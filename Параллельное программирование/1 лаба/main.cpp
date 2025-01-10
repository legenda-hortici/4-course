#include <iostream>
#include <vector>
#include <chrono>
#include <pthread.h>
#include <fstream>


void multiply_matrices_openmp(std::vector<std::vector<double> > C_openmp,
    std::vector<std::vector<double> > A,
    std::vector<std::vector<double> > B,
    int MATRIX_SIZE
) {
    #pragma omp parallel for
    for (int m = 0; m < MATRIX_SIZE * MATRIX_SIZE; ++m) {
        int i = m / MATRIX_SIZE;
        int j = m % MATRIX_SIZE;
        C_openmp[i][j] = 0;
        for (int k = 0; k < MATRIX_SIZE; ++k) {
            C_openmp[i][j] += A[i][k] * B[k][j];
        }
    }
}

//  g++-14 -fopenmp -pthread -o main main.cpp -L/opt/homebrew/opt/libomp/lib -I/opt/homebrew/opt/libomp/include


void multiply_matrices_seq(std::vector<std::vector<double> > C_openmp,
    std::vector<std::vector<double> > A,
    std::vector<std::vector<double> > B,
    int MATRIX_SIZE
) {
    for (int m = 0; m < MATRIX_SIZE * MATRIX_SIZE; ++m) {
        int i = m / MATRIX_SIZE;
        int j = m % MATRIX_SIZE;
        C_openmp[i][j] = 0;
        for (int k = 0; k < MATRIX_SIZE; ++k) {
            C_openmp[i][j] += A[i][k] * B[k][j];
        }
    }
}


struct ThreadData {
    int start_index;
    int end_index;
    int size;
    std::vector<std::vector<double> >* C; 
    std::vector<std::vector<double> >* A; 
    std::vector<std::vector<double> >* B; 
};


void* multiply_matrices_pthreads(void* arg) {
    ThreadData* data = static_cast<ThreadData*>(arg);
    int size = data->size;
    int start_index = data->start_index;
    int end_index = data->end_index;

    for (int m = start_index; m < end_index; ++m) {
        int i = m / size;
        int j = m % size;

        (*data->C)[i][j] = 0;
        for (int k = 0; k < size; ++k) {
            (*data->C)[i][j] += (*data->A)[i][k] * (*data->B)[k][j];
        }
    }
    return 0;
}


void  multiply_matrices(int num_threads,
    int MATRIX_SIZE,
    std::vector<std::vector<double> >& C_windows,
    std::vector<std::vector<double> >& A,
    std::vector<std::vector<double> >& B
) {
    pthread_t threads[num_threads];
    ThreadData thread_data[num_threads];

    int elements_per_thread = MATRIX_SIZE * MATRIX_SIZE / num_threads;

    for (int i = 0; i < num_threads; ++i) {
        thread_data[i].start_index = i * elements_per_thread;
        thread_data[i].end_index = (i + 1) * elements_per_thread;
        thread_data[i].size = MATRIX_SIZE;
        thread_data[i].C = &C_windows;
        thread_data[i].A = &A;
        thread_data[i].B = &B;

        if (i == num_threads - 1) {
            thread_data[i].end_index = A.size();
        }

        pthread_create(&threads[i], nullptr, multiply_matrices_pthreads, (void*)&thread_data[i]);
    }

    for (int i = 0; i < num_threads; ++i) {
        pthread_join(threads[i], nullptr);
    }
}

void initialize_matrices(int size, std::vector<std::vector<double> >& A, std::vector<std::vector<double> >& B){
    A.resize(size, std::vector<double>(size));
    B.resize(size, std::vector<double>(size));
    for (int i = 0; i < size; ++i) {
        for (int j = 0; j < size; ++j) {
            A[i][j] = i + j + 1;
            B[i][j] = (i + j + 1) % 10 + 1;
        }
    }
}


int main() {
    setlocale(LC_ALL, "ru");

    std::vector<int> sizes;
    sizes.push_back(600);
    sizes.push_back(840);
    sizes.push_back(984);
    sizes.push_back(1128);
    sizes.push_back(1488);
    sizes.push_back(1992);
    // sizes.push_back(3000); 

    int const num_threads = 8;
    std::ofstream output("results.csv");
    std::ofstream output2("results2.csv");

    output.flush();
    output2.flush();
   

    if (!output.is_open() || !output2.is_open()) {
       std::cerr << "Ошибка открытия файлов!" << std::endl;
       return 1; // или другой код ошибки
   }
   
    std::cout << "Начало выполнения программы..." << std::endl;
   

    for (int size : sizes) {
        std::vector<std::vector<double> > A, B, C_windows(size, std::vector<double>(size, 0)), C_openmp(size, std::vector<double>(size, 0)), C_seq(size, std::vector<double>(size, 0));
        initialize_matrices(size, A, B);
        
        auto start_windows = std::chrono::high_resolution_clock::now();
        multiply_matrices(num_threads, size, C_windows, A, B);
        auto end_windows = std::chrono::high_resolution_clock::now();
        // double time_windows = ;
        std::cout << "Умножение с помощью PThreads для размера " << size << ": "
            << std::chrono::duration<double>(end_windows - start_windows).count() << " seconds\n";

        auto start_openmp = std::chrono::high_resolution_clock::now();
        multiply_matrices_openmp(C_openmp, A, B, size);
        auto end_openmp = std::chrono::high_resolution_clock::now();
        // double time_openmp = ;
        std::cout << "Умножение с помощью OpenMP для размера " << size << ": "
            << std::chrono::duration<double>(end_openmp - start_openmp).count() << " seconds\n";

        auto start_seq = std::chrono::high_resolution_clock::now();
        multiply_matrices_seq(C_seq, A, B, size);
        auto end_seq = std::chrono::high_resolution_clock::now();
        // double time_seq = e;
        std::cout << "Умножение последовательно для размера " << size << ": "
            << std::chrono::duration<double>(end_seq - start_seq).count() << " seconds\n";

        double boost_windows = static_cast<double>((end_seq - start_seq).count()) / static_cast<double>((end_windows - start_windows).count());
        double boost_openmp = static_cast<double>((end_seq - start_seq).count()) / static_cast<double>((end_openmp - start_openmp).count());

        std::cout << "Ускорение OpenMP: " << boost_openmp << "\n";
        std::cout << "Ускорение  Windows Threads: " << boost_windows << "\n";
        
        
        output << "Size,Pthreads Time,OpenMP Time,Sequential Time\n";
        output << size << "," << std::chrono::duration<double>(end_windows - start_windows).count() << "," << std::chrono::duration<double>(end_openmp - start_openmp).count() << "," << std::chrono::duration<double>(end_seq - start_seq).count() << "\n";

        
        output2 << "Size,Boost PThreads Time,Boost OpenMP Time\n";
        output2 << size << "," << boost_windows << "," << boost_openmp << "\n";

        std::cout << "--------------------------------------------------------\n\n";
    }
    output.close();
    output2.close();
    std::cout << "Запись завершена." << std::endl;
    return 0;
}
