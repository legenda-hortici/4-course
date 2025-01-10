import java.io.*;

public interface Item extends Serializable {
    int calculateTotal() throws BusinessException;
    
    String getName();
    void setName(String name);

    int getId();
    void setId(int id);

    void output(OutputStream out) throws IOException;
    void write(Writer out) throws IOException;

    default String toFileFormat() {
        return String.format("%s %d %s %s",
            this.getClass().getSimpleName(),
            getId(),
            getName(),
            serializeFields());
    }

    String serializeFields();
}
