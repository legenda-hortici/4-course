import java.util.Arrays;

public class Vector {
    private double[] elements;

    // Конструктор с параметром
    public Vector(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Размер вектора должен быть положительным");
        }
        this.elements = new double[size];
    }

    // метод для проверки индекса
    private void checkIndex(int index) {
        if (index < 0 || index >= elements.length) {
            throw new IndexOutOfBoundsException("Индекс вне диапазона: " + index);
        }
    }

    // Метод для получения значения элемента по индексу
    public double getElement(int index) {
        checkIndex(index);
        return elements[index];
    }

    // Метод для изменения значения элемента по индексу
    public void setElement(int index, double value) {
        checkIndex(index);
        elements[index] = value;
    }

    // Метод для получения длины вектора
    public int getSize() {
        return elements.length;
    }

    // Метод для поиска минимального значения
    public double getMin() {
        return Arrays.stream(elements).min().getAsDouble();
    }

    // Метод для поиска максимального значения
    public double getMax() {
        return Arrays.stream(elements).max().getAsDouble();
    }

    // Метод для сортировки вектора по возрастанию
    public void sortArray() {
        Arrays.sort(elements);
    }

    // Метод для нахождения евклидовой нормы
    public double getEuclideanNorm() {
        return Math.sqrt(Arrays.stream(elements).map(x -> x * x).sum());
    }

    // Метод для умножения вектора на число
    public void multiplyByScalar(double scalar) {
        for (int i = 0; i < elements.length; i++) {
            elements[i] *= scalar;
        }
    }

    // Статический метод для сложения двух векторов
    public static Vector add(Vector v1, Vector v2) {
        checkSameSize(v1, v2);
        Vector result = new Vector(v1.getSize());
        for (int i = 0; i < v1.getSize(); i++) {
            result.setElement(i, v1.getElement(i) + v2.getElement(i));
        }
        return result;
    }

    // Статический метод для нахождения скалярного произведения двух векторов
    public static double dotProduct(Vector v1, Vector v2) {
        checkSameSize(v1, v2);
        double result = 0;
        for (int i = 0; i < v1.getSize(); i++) {
            result += v1.getElement(i) * v2.getElement(i);
        }
        return result;
    }


    // Приватный метод для проверки, что два вектора одинаковой длины
    private static void checkSameSize(Vector v1, Vector v2) {
        if (v1.getSize() != v2.getSize()) {
            throw new IllegalArgumentException("Векторы должны быть одинаковой длины");
        }
    }

    // Переопределение метода toString для удобного вывода
    @Override
    public String toString() {
        return Arrays.toString(elements);
    }
}
