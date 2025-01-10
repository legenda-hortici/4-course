public class Main {
    public static void main(String[] args) {
        Vector v1 = new Vector(3);
        Vector v2 = new Vector(3);

        v1.setElement(0, 1.0);
        v1.setElement(1, 2.0);
        v1.setElement(2, 3.0);

        v2.setElement(0, 4.0);
        v2.setElement(1, 5.0);
        v2.setElement(2, 6.0);

        System.out.println("Вектор 1: " + v1);
        System.out.println("Вектор 2: " + v2);

        Vector sum = Vector.add(v1, v2);
        System.out.println("Сумма векторов: " + sum);

        double dotProduct = Vector.dotProduct(v1, v2);
        System.out.println("Скалярное произведение: " + dotProduct);

        double norm = v1.getEuclideanNorm();
        System.out.println("Евклидова норма вектора 1: " + norm);

        v1.multiplyByScalar(2);
        System.out.println("Вектор 1 после умножения на 2: " + v1);

        double min = v1.getMin();
        double max = v1.getMax();
        System.out.println("Минимум вектора 1: " + min);
        System.out.println("Максимум вектора 1: " + max);

        v1.sortArray();
        System.out.println("Вектор 1 после сортировки: " + v1);
    }
}
