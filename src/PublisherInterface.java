public interface PublisherInterface {
    public void addHashTag(String s);
    public void removeHashTag(String s);
    public void getBrokerList();
    public Broker HashTopic(String s);
    public void push(String s);
    public void notifyFailure(Broker b);
    public void notifyBrokersForHashTags(String s);
    public void generateChunks(String s);
}
