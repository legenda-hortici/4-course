import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class RMIClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 8080);
            RemoteDatabaseService service = (RemoteDatabaseService) registry.lookup("DatabaseService");

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n1. Показать все темы по Go\n2. Добавить раздел Go\n3. Добавить тему Go\n4. Удалить тему Go\n5. Удалить раздел Go\n0. Выход");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> {
                        List<String> topics = service.getAllTopicsWithSections();
                        topics.forEach(System.out::println);
                    }
                    case 2 -> {
                        System.out.print("Введите название раздела по Go: ");
                        String section = scanner.nextLine();
                        System.out.print("Введите описание раздела: ");
                        String description = scanner.nextLine();
                        service.addSection(section, description);
                    }
                    case 3 -> {
                        System.out.print("Введите название темы по Go: ");
                        String topic = scanner.nextLine();
                        System.out.print("Введите ID раздела: ");
                        int sectionId = scanner.nextInt();
                        service.addTopic(topic, sectionId);
                    }
                    case 4 -> {
                        System.out.print("Введите ID темы по Go для удаления: ");
                        int topicId = scanner.nextInt();

                        boolean deleted = service.deleteTopic(topicId);
                        if (deleted) {
                            System.out.println("Тема по Go удалена.");
                        } else {
                            System.out.println("Тема с таким ID не найдена.");
                        }
                    }
                    case 5 -> {
                        System.out.print("Введите ID раздела по Go для удаления: ");
                        int sectionId = scanner.nextInt();
                        scanner.nextLine();

                        if (service.hasTopicsInSection(sectionId)) {
                            System.out.print("В этом разделе Go есть темы. Удалить их тоже? (да/нет): ");
                            String response = scanner.nextLine();

                            if (response.equalsIgnoreCase("да")) {
                                service.deleteAllTopicsInSection(sectionId);
                                service.deleteSection(sectionId);
                                System.out.println("Раздел по Go удалён.");
                            } else {
                                System.out.println("Удаление отменено.");
                            }
                        } else {
                            service.deleteSection(sectionId);
                            System.out.println("Раздел по Go удалён.");
                        }
                    }
                    case 0 -> {
                        System.out.println("Выход...");
                        return;
                    }
                    default -> System.out.println("Неверный ввод! Выберите пункт из меню.");
                }
            }

        } catch (Exception e) {
            System.out.println("Ошибка клиента: " + e.getMessage());
        }
    }
}
