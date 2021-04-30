import java.util.List;

public class Broker extends Thread implements BrokerInterface, Node{



    //  implementation
    public void calculateKeys() {

    }

    public Publisher acceptConnection(Publisher p) {
        return null;
    }

    public Consumer acceptConnection(Consumer c) {
        return null;
    }

    public void notifyPublisher(String s) {

    }

    public void notifyBrokersOnChanges() {

    }

    public void pull(String s) {

    }

    public void filterConsumers(String s) {

    }

    public void init(int i) {

    }

    public List<Broker> getBrokers() {
        return null;
    }



    public void connect() {

    }

    public void disconnect() {

    }

    public void updateNodes() {

    }
}
