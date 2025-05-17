import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Worker_1 {
    public static void main(String[] args) {
        final String SERVER_ADDRESS = "localhost";
        final int SERVER_PORT = 8080;

        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Подключено к серверу. Введите координаты (x1 y1 x2 y2) или 'stop' для выхода:");

            while (true) {
                System.out.print("-> ");
                String input = scanner.nextLine();

                if ("stop".equalsIgnoreCase(input)) {
                    writer.println("stop");
                    break;
                }

                writer.println(input);
                String response = reader.readLine();
                System.out.println("Ответ сервера: " + response);
            }
        } catch (IOException e) {
            System.err.println("Ошибка клиента: " + e.getMessage());
        }
    }
}
