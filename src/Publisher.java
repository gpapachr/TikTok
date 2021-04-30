import java.util.List;

public class Publisher extends Thread implements PublisherInterface, Node{
    private ChannelName channelName;

    public ChannelName getChannelName() {
        return channelName;
    }


    //implementation
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

    public void addHashTag(String s) {

    }

    public void removeHashTag(String s) {

    }

    public void getBrokerList() {

    }

    public Broker HashTopic(String s) {
        return null;
    }

    public void push(String s, Value v) {

    }

    public void notifyFailure(Broker b) {

    }

    public void notifyBrokersForHashTags(String s) {

    }

    public void generateChunks(String s) {

    }
}
