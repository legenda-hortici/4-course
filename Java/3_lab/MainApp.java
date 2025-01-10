import java.io.*;
import java.util.*;

public class MainApp {
    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        items.add(new Product(new int[]{100, 300}, "Ноутбук", 1));
        items.add(new Service(new int[]{100, 200, 300}, "Уборка", 1));

        // Запись и чтение из байтового потока
        System.out.println("\n=== Запись и чтение из байтового потока ===");
        try (FileOutputStream fos = new FileOutputStream("items.bin");
             FileInputStream fis = new FileInputStream("items.bin")) {
            for (Item item : items) {
                StreamUtil.output(item, fos);
            }
            for (int i = 0; i < items.size(); i++) {
                System.out.println("Прочитано из байтового потока: " + StreamUtil.input(fis));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Запись и чтение из символьного потока
        System.out.println("\n=== Запись и чтение из символьного потока ===");
        try (FileWriter fw = new FileWriter("symbol_tr.txt");
            FileReader fr = new FileReader("symbol_tr.txt")) {

            // Запись объектов
            for (Item item : items) {
                StreamUtil.write(item, fw);
            }

            // Чтение объектов
            List<Item> readItems = StreamUtil.read(fr);
            for (Item readItem : readItems) {
                System.out.println("Прочитан объект: " + readItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Сериализация и десериализация
        System.out.println("\n=== Сериализация и десериализация ===");
        try (FileOutputStream fos = new FileOutputStream("items.ser");
             FileInputStream fis = new FileInputStream("items.ser")) {
            for (Item item : items) {
                StreamUtil.serialize(item, fos);
            }
            for (int i = 0; i < items.size(); i++) {
                System.out.println("Прочитано из сериализованного файла: " + StreamUtil.deserialize(fis));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Запись и чтение с отформатированным выводом
        System.out.println("\n=== Запись и чтение с отформатированным выводом ===");
        try (FileWriter fw = new FileWriter("items_1.txt");
            FileReader fr = new FileReader("items_1.txt");
            Scanner scanner = new Scanner(fr)) {
            for (Item item : items) {
                StreamUtil.writeFormat(item, fw);
            }
            for (int i = 0; i < items.size(); i++) {
                if (scanner.hasNextLine()) {
                    System.out.println("Прочитано из символьного потока: " + StreamUtil.readFormat(scanner));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
