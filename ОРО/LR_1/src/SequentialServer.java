import java.io.*;
import java.net.*;

public class SequentialServer {
    public static void main(String[] args) {
        final int PORT = 8080;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен. Ожидание клиентов...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    System.out.println("Клиент подключился.");

                    String line;
                    while ((line = reader.readLine()) != null) {
                        if ("exit".equalsIgnoreCase(line)) {
                            System.out.println("Клиент отключился.");
                            break;
                        }

                        String[] parts = line.split(" ");
                        if (parts.length != 4) {
                            writer.println("Ошибка: введите 4 числа!");
                            continue;
                        }

                        try {
                            double x1 = Double.parseDouble(parts[0]);
                            double y1 = Double.parseDouble(parts[1]);
                            double x2 = Double.parseDouble(parts[2]);
                            double y2 = Double.parseDouble(parts[3]);

                            double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
                            writer.println(distance == 0 ? "Ошибка: точки совпадают!" : "Расстояние: " + distance);
                        } catch (NumberFormatException e) {
                            writer.println("Ошибка: некорректный ввод!");
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка сервера: " + e.getMessage());
        }
    }
}
