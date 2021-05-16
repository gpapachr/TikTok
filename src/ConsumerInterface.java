public interface ConsumerInterface {
    public void register(Broker b,String s);
    public void disconnect(Broker b,String s);
    public void playData(String s,VideoFile v);
}
