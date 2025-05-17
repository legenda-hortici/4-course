import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RemoteDatabaseService extends Remote {
    List<String> getAllTopicsWithSections() throws RemoteException;
    void addSection(String name, String description) throws RemoteException;
    void addTopic(String name, int sectionId) throws RemoteException;
    void deleteSection(int sectionId) throws RemoteException;
    boolean deleteTopic(int topicId) throws RemoteException;

    boolean hasTopicsInSection(int sectionId) throws RemoteException;
    void deleteAllTopicsInSection(int sectionId) throws RemoteException;
}
