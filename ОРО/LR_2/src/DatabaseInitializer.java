import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    private static final String URL = "jdbc:sqlite:topics.db";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            // Таблица с разделами тем по программированию на Go
            String createSectionsTable = """
                CREATE TABLE IF NOT EXISTS Разделы (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    название TEXT NOT NULL,
                    описание TEXT
                );
                """;

            // Таблица с темами, связанными с разделами Go
            String createTopicsTable = """
                CREATE TABLE IF NOT EXISTS Темы (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    название TEXT NOT NULL,
                    раздел_id INTEGER,
                    FOREIGN KEY (раздел_id) REFERENCES Разделы(id) ON DELETE CASCADE
                );
                """;

            stmt.execute(createSectionsTable);
            stmt.execute(createTopicsTable);

            System.out.println("База данных для тем по Go успешно инициализирована!");

        } catch (SQLException e) {
            System.out.println("Ошибка при инициализации БД для Go: " + e.getMessage());
        }
    }
}
