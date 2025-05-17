import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RemoteDatabaseServiceImpl extends UnicastRemoteObject implements RemoteDatabaseService {
    private static final String URL = "jdbc:sqlite:topics.db";

    protected RemoteDatabaseServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public List<String> getAllTopicsWithSections() throws RemoteException {
        List<String> result = new ArrayList<>();
        String query = """
                SELECT Темы.название, Разделы.название
                FROM Темы
                LEFT JOIN Разделы ON Темы.раздел_id = Разделы.id
                """;

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String topic = rs.getString(1);
                String section = rs.getString(2);
                result.add("Тема: " + topic + " | Раздел: " + (section != null ? section : "Нет"));
            }

        } catch (SQLException e) {
            throw new RemoteException("Ошибка при получении данных: " + e.getMessage());
        }
        return result;
    }

    @Override
    public void addSection(String name, String description) {
        String query = "INSERT INTO Разделы (название, описание) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.executeUpdate();
            System.out.println("Раздел добавлен!");

        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении раздела: " + e.getMessage());
        }
    }

    @Override
    public void addTopic(String name, int sectionId) throws RemoteException {
        String query = "INSERT INTO Темы (название, раздел_id) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, sectionId);
            pstmt.executeUpdate();
            System.out.println("Удалённо: Тема добавлена!");

        } catch (SQLException e) {
            throw new RemoteException("Ошибка при добавлении темы: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteTopic(int topicId) throws RemoteException {
        String query = "DELETE FROM Темы WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, topicId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new RemoteException("Ошибка при удалении темы: " + e.getMessage());
        }
    }


    @Override
    public boolean hasTopicsInSection(int sectionId) throws RemoteException {
        String checkQuery = "SELECT COUNT(*) FROM Темы WHERE раздел_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            checkStmt.setInt(1, sectionId);
            ResultSet rs = checkStmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;

        } catch (SQLException e) {
            System.out.println("Ошибка при проверке тем: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void deleteAllTopicsInSection(int sectionId) throws RemoteException {
        String deleteTopicsQuery = "DELETE FROM Темы WHERE раздел_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement deleteTopicsStmt = conn.prepareStatement(deleteTopicsQuery)) {

            deleteTopicsStmt.setInt(1, sectionId);
            deleteTopicsStmt.executeUpdate();
            System.out.println("Связанные темы удалены.");

        } catch (SQLException e) {
            System.out.println("Ошибка при удалении тем: " + e.getMessage());
        }
    }

    @Override
    public void deleteSection(int sectionId) throws RemoteException {
        String deleteSectionQuery = "DELETE FROM Разделы WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement deleteSectionStmt = conn.prepareStatement(deleteSectionQuery)) {

            deleteSectionStmt.setInt(1, sectionId);
            int affectedRows = deleteSectionStmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Раздел удален!");
            } else {
                System.out.println("Раздел с таким ID не найден.");
            }

        } catch (SQLException e) {
            System.out.println("Ошибка при удалении раздела: " + e.getMessage());
        }
    }

}

