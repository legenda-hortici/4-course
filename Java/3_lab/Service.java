import java.io.*;
import java.util.Arrays;

public class Service implements Item {
    private int[] costs;
    private String serviceName;
    private int serviceId;

    public Service(int[] costs, String serviceName, int serviceId) {
        if (costs.length <= 0 || serviceName.isEmpty() || serviceId <= 0) {
            throw new InvalidInputException("Неверные входные данные.");
        }
        this.costs = costs;
        this.serviceName = serviceName;
        this.serviceId = serviceId;
    }   

    public int[] getCosts() {
        return costs;
    }

    public void setCosts(int[] costs) {
        this.costs = costs;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public int calculateTotal() throws BusinessException {
        if (costs.length == 0) throw new BusinessException("Цены отсутствуют.");
        return Arrays.stream(costs).sum();
    }

    @Override
    public void output(OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeInt(serviceId);
        dos.writeUTF(serviceName);
        dos.writeInt(costs.length);
        for (int cost : costs) dos.writeInt(cost);
    }

    @Override
    public void write(Writer out) throws IOException {
        out.write(serviceId + " " + serviceName + " " + Arrays.toString(costs)); // Для Service
    }


    @Override
    public String getName() {
        return serviceName;
    }

    @Override
    public void setName(String name) {
        this.serviceName = name;
    }

    @Override
    public int getId() {
        return serviceId;
    }

    @Override
    public void setId(int id) {
        this.serviceId = id;
    }

    @Override
    public String toString() {
        return "Service {название услуги = '" + serviceName + "', id = " + serviceId + ", затраты = " + Arrays.toString(costs) + "}";
    }
    @Override
    public String serializeFields() {
        return Arrays.toString(costs);
    }
}
