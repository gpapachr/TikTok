import java.util.*;

public interface Node {
    public List<Broker> brokers = new ArrayList<Broker>();

    public void init(int i);
    public List<Broker> getBrokers();
    public void connect();
    public void disconnect();
    public void updateNodes();
}
