import pandas as pd
import matplotlib.pyplot as plt

# Загрузка данных из CSV файла
def load_csv_without_repeated_headers(filename):
    with open(filename, 'r') as file:
        lines = file.readlines()
    
    # Удаляем повторяющиеся строки заголовков
    cleaned_lines = [line for line in lines if not line.startswith('Size,')]
    
    # Записываем очищенные данные во временный файл
    with open('cleaned_results.csv', 'w') as cleaned_file:
        cleaned_file.writelines(cleaned_lines)

    # Читаем очищенные данные без заголовков
    data = pd.read_csv('cleaned_results.csv', header=None)
    
    # Добавляем заголовки вручную
    data.columns = ['Size', 'PThreads Time', 'OpenMP Time', 'Sequential Time']
    
    return data

# g++-14 -fopenmp -pthread -o main main.cpp -L/opt/homebrew/opt/libomp/lib -I/opt/homebrew/opt/libomp/include


# Загрузка очищенных данных
data = load_csv_without_repeated_headers('results.csv')

# Проверка заголовков столбцов
print(data.columns)

# Создание графиков на одном рисунке
plt.figure(figsize=(15, 8))

# График для PThreads
plt.plot(data['Size'], data['PThreads Time'], marker='o', color='b', label='PThreads Time')

# График для OpenMP
plt.plot(data['Size'], data['OpenMP Time'], marker='o', color='g', label='OpenMP Time')

# График для последовательного метода
plt.plot(data['Size'], data['Sequential Time'], marker='o', color='r', label='Sequential Time')

# Настройки графика
plt.title('Время умножения матриц для различных реализаций')
plt.xlabel('Размерность матриц')
plt.ylabel('Время (секунды)')
plt.grid()
plt.legend()

# Отображение графиков
plt.tight_layout()
plt.show()
