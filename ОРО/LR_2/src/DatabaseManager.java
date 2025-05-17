import java.sql.*;
import java.util.Scanner;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:topics.db";

    public void getAllTopicsWithSections() {
        String query = """
            SELECT Темы.id, Темы.название, Разделы.название, Разделы.описание
            FROM Темы
            LEFT JOIN Разделы ON Темы.раздел_id = Разделы.id
            ORDER BY Темы.id;
            """;

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String topicName = rs.getString(2);
                String sectionName = rs.getString(3);

                System.out.println("Тема по Go: " + topicName + " - раздел: " + sectionName);
            }

        } catch (SQLException e) {
            System.out.println("Ошибка при получении данных о темах Go: " + e.getMessage());
        }
    }

    public void addSection(String name, String description) {
        String query = "INSERT INTO Разделы (название, описание) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.executeUpdate();
            System.out.println("Раздел по Go добавлен!");
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении раздела по Go: " + e.getMessage());
        }
    }

    public void addTopic(String name, int sectionId) {
        String checkQuery = "SELECT id FROM Разделы WHERE id = ?";
        String insertQuery = "INSERT INTO Темы (название, раздел_id) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            checkStmt.setInt(1, sectionId);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Ошибка: раздел по Go с ID " + sectionId + " не существует.");
                return;
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                insertStmt.setString(1, name);
                insertStmt.setInt(2, sectionId);
                insertStmt.executeUpdate();
                System.out.println("Тема по Go добавлена!");
            }

        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении темы по Go: " + e.getMessage());
        }
    }

    public void deleteTopic(int topicId) {
        String query = "DELETE FROM Темы WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, topicId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Тема по Go удалена!");
            } else {
                System.out.println("Тема по Go с таким ID не найдена.");
            }

        } catch (SQLException e) {
            System.out.println("Ошибка при удалении темы по Go: " + e.getMessage());
        }
    }

    public void deleteSection(int sectionId) {
        String checkQuery = "SELECT COUNT(*) FROM Темы WHERE раздел_id = ?";
        String deleteTopicsQuery = "DELETE FROM Темы WHERE раздел_id = ?";
        String deleteSectionQuery = "DELETE FROM Разделы WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            checkStmt.setInt(1, sectionId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("В этом разделе Go есть темы. Удалить их тоже? (да/нет)");
                Scanner scanner = new Scanner(System.in);
                String response = scanner.nextLine();

                if (!response.equalsIgnoreCase("да")) {
                    System.out.println("Удаление отменено.");
                    return;
                }

                try (PreparedStatement deleteTopicsStmt = conn.prepareStatement(deleteTopicsQuery)) {
                    deleteTopicsStmt.setInt(1, sectionId);
                    deleteTopicsStmt.executeUpdate();
                    System.out.println("Связанные темы по Go удалены.");
                }
            }

            try (PreparedStatement deleteSectionStmt = conn.prepareStatement(deleteSectionQuery)) {
                deleteSectionStmt.setInt(1, sectionId);
                int affectedRows = deleteSectionStmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Раздел по Go удалён!");
                } else {
                    System.out.println("Раздел по Go с таким ID не найден.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Ошибка при удалении раздела по Go: " + e.getMessage());
        }
    }
}
