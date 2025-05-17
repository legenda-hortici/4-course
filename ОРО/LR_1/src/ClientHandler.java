import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

            System.out.println("Клиент подключился: " + clientSocket.getInetAddress());

            String line;
            while ((line = reader.readLine()) != null) {
                if ("exit".equalsIgnoreCase(line)) {
                    System.out.println("Клиент отключился: " + clientSocket.getInetAddress());
                    break;
                }

                String[] parts = line.split(" ");
                if (parts.length != 4) {
                    writer.println("Ошибка: введите 4 числа");
                    continue;
                }

                try {
                    double x1 = Double.parseDouble(parts[0]);
                    double y1 = Double.parseDouble(parts[1]);
                    double x2 = Double.parseDouble(parts[2]);
                    double y2 = Double.parseDouble(parts[3]);

                    double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

//                    Thread.sleep(5000);

                    writer.println(distance == 0 ? "Ошибка: точки совпадают!" : "Расстояние: " + distance);
                } catch (NumberFormatException e) {
                    writer.println("Ошибка: некорректный ввод!");
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка клиента: " + e.getMessage());
        }
    }
}