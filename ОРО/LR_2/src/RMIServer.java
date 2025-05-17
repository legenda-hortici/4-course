import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args) {
        try {
            RemoteDatabaseServiceImpl service = new RemoteDatabaseServiceImpl();
            Registry registry = LocateRegistry.createRegistry(8080);
            registry.rebind("DatabaseService", service);
            System.out.println("RMI сервер запущен!");
        } catch (Exception e) {
            System.out.println("Ошибка сервера: " + e.getMessage());
        }
    }
}
