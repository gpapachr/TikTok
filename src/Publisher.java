import java.util.List;
import java.util.*;
import java.io.*;


public class Publisher extends Thread implements PublisherInterface, Node{
    private ChannelName channelName;

    public ArrayList<VideoFile> videos;
    public ArrayList<String> hashtags = new ArrayList<String>();
    public Boolean message = false;


    public ChannelName getChannelName() {
        return channelName;
    }


    //implementation
    public void init(int i) {
        videos = new ArrayList<myObj>();

    }

    public List<Broker> getBrokers() {
        return null;
    }

    public void connect() {
        try{
            sc = new Scanner(System.in);
            address = "localhost";
            socket = new Socket(address, port);
            System.out.println("Socket= " + socket);

            ois = new ObjectInputStream((socket.getInputStream()));

            System.out.println("Waiting for server's response");
            Broker temp = (Broker) ois.readObject();
            while(temp!=null){
                brokers.add(temp);
                temp = (Broker) ois.readObject();
            }
            System.out.println("List acquired");

            for(int i=0; i<brokers.size(); i++){
                System.out.println(brokers.get(i).id);
            }

            socket.close();
            System.out.println("Connection closed");
        }
        catch(Exception e){
            System.out.println("connect(): ");
            e.printStackTrace();
        }
        disconnect();
    }

    public void disconnect() {
        try {
            sc.close();
        }
        catch(Exception e)
        {
            System.out.println("disconnect(): " + e);
        }
    }

    public void updateNodes() {

    }

    public void addHashTag(String s) {
        hashtags.add(s);

    }

    public void removeHashTag(String s) {
        hashtags.remove(s);

    }

    public void getBrokerList() {
        return brokers;
    }

    public Broker HashTopic(String s) {
        return null;
    }

    public void push(String s, Value v) {

    }

    public void notifyFailure(Broker b) {
        if(message==false){
            System.out.println("Not found");
            //prowthish ston consumer
        }

    }

    public void notifyBrokersForHashTags(String s) {

    }

    public void generateChunks(String path) {
        VideoFile vf = new VideoFile(path);
        Mp4Parse parser = new Mp4Parse();
    }

    public static void main(String args[]) throws Exception{
        Publisher publisher = new Publisher(Integer.parseInt(args[0]));
    }
}
