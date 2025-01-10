import java.io.*;
import java.util.Arrays;

public class Product implements Item {
    private int[] prices;
    private String name;
    private int id;

    public Product(int[] prices, String name, int id) {
        if (prices.length <= 0 || name.isEmpty() || id <= 0) {
            throw new InvalidInputException("Неверные входные данные.");
        }
        this.prices = prices;
        this.name = name;
        this.id = id;
    }

    public int[] getPrices() {
        return prices;
    }

    public void setPrices(int[] prices) {
        this.prices = prices;
    }

    @Override
    public int calculateTotal() throws BusinessException {
        if (prices.length == 0) throw new BusinessException("Нет доступных цен.");
        return Arrays.stream(prices).sum();
    }

    @Override
    public void output(OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeInt(id);
        dos.writeUTF(name);
        dos.writeInt(prices.length);
        for (int price : prices) dos.writeInt(price);
    }

    @Override
    public void write(Writer out) throws IOException {
        out.write(id + " " + name + " " + Arrays.toString(prices)); // Для Product
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Product {название товара = '" + name + "', id = " + id + ", цены = " + Arrays.toString(prices) + "}";
    }

    @Override
    public String serializeFields() {
        return Arrays.toString(prices);
    }
}
