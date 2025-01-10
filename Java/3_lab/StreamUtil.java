import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.List;

public class StreamUtil {
    // Запись объекта Item в байтовый поток
    public static void output(Item item, OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        
        if (item instanceof Product) {
            dos.writeUTF("Product"); 
            Product product = (Product) item;
            dos.writeInt(product.getId());
            dos.writeUTF(product.getName());
            dos.writeInt(product.getPrices().length);
            for (int price : product.getPrices()) {
                dos.writeInt(price);
            }
        } else if (item instanceof Service) {
            dos.writeUTF("Service"); 
            Service service = (Service) item;
            dos.writeInt(service.getId());
            dos.writeUTF(service.getName());
            dos.writeInt(service.getCosts().length);
            for (int cost : service.getCosts()) {
                dos.writeInt(cost);
            }
        }
        dos.flush();
    }
    
    // Чтение объекта Item из байтового потока
    public static Item input(InputStream in) throws IOException {
        DataInputStream dis = new DataInputStream(in);
        
        String type = dis.readUTF();  // Читаем тип объекта
        int id = dis.readInt();
        String name = dis.readUTF();
        int length = dis.readInt();
        int[] values = new int[length];
        for (int i = 0; i < length; i++) {
            values[i] = dis.readInt();
        }
        
        if ("Product".equals(type)) {
            return new Product(values, name, id);
        } else if ("Service".equals(type)) {
            return new Service(values, name, id);
        } else {
            throw new IOException("Неизвестный тип: " + type);
        }
    }



    // Запись объекта Item в символьный поток
    public static void write(Item item, Writer out) throws IOException {
        out.write(item.toFileFormat() + System.lineSeparator());
        out.flush(); // Сбрасываем данные
        System.out.println("Запись завершена для: " + item);
    }

    public static List<Item> read(Reader in) throws IOException {
        List<Item> items = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(in)) {
            String line;
            while ((line = in.readLine()) != null) {
                String[] parts = line.split(" ", 4);
                if (parts.length < 4) {
                    throw new IOException("Неверный формат файла: " + line);
                }

                // String type = parts[0];
                int id = Integer.parseInt(parts[1]);
                String name = parts[2];
                String[] valuesStr = parts[3].replace("[", "").replace("]", "").split(", ");
                int[] values = Arrays.stream(valuesStr).mapToInt(Integer::parseInt).toArray();

                items.add(new (values, name, id));
            }
        }
        return items;
    }



    
    
    // Сериализация объекта Item
    public static void serialize(Item item, OutputStream out) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(item);
        oos.flush();
    }

    // Десериализация объекта Item
    public static Item deserialize(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(in);
        return (Item) ois.readObject();
    }

    // Запись объекта Item с отформатированным выводом
    public static void writeFormat(Item item, Writer out) throws IOException {
        if (item instanceof Product) {
            Product product = (Product) item;
            out.write(String.format("Product %d %s %s%n", product.getId(), product.getName(), Arrays.toString(product.getPrices())));
        } else if (item instanceof Service) {
            Service service = (Service) item;
            out.write(String.format("Service %d %s %s%n", service.getId(), service.getName(), Arrays.toString(service.getCosts())));
        }
        out.flush();
    }
    

    // Чтение объекта Item с отформатированного вывода
    public static Item readFormat(Scanner in) {
        if (!in.hasNextLine()) {
            return null; // при отсутствии данных
        }
        String line = in.nextLine();
        // System.out.println("Читается строка: " + line);
        String[] parts = line.split(" ", 4);
        
        if (parts.length < 4) {
            throw new InputMismatchException("Неверный формат строки: " + line);
        }
        
        try {
            String type = parts[0];
            int id = Integer.parseInt(parts[1]);
            String name = parts[2];
            String[] valuesStr = parts[3].replace("[", "").replace("]", "").split(", ");
            int[] values = Arrays.stream(valuesStr).mapToInt(Integer::parseInt).toArray();
            
            if (type.equals("Product")) {
                return new Product(values, name, id);
            } else if (type.equals("Service")) {
                return new Service(values, name, id);
            } else {
                throw new InputMismatchException("Неизвестный тип: " + type);
            }
        } catch (NumberFormatException e) {
            throw new InputMismatchException("Ошибка разбора числовых значений: " + e.getMessage());
        }
    }
}
