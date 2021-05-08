import java.net.*;
import java.util.*;
import java.io.*;


public class Publisher extends Thread implements PublisherInterface, Node, Serializable{
    private ChannelName channelName;
    public ArrayList<VideoFile> videos;
    public ArrayList<String> hashtags = new ArrayList<String>();
    public Boolean message = false;
    public ArrayList<byte[]> chunks = new ArrayList<byte[]>();
    private transient Socket socket = null;
    private ObjectInputStream  ois = null;
    private String address;
    private int port;
    private Scanner sc;
    private Broker broker = null;
    private ObjectOutputStream  oos = null;
    
    Publisher(int port, ChannelName channelName){
        this.channelName = channelName;
        init(port);
    }
    


    public ChannelName getChannelName() {
        return channelName;
    }


    //implementation
    public void init(int port) {
        videos = new ArrayList<VideoFile>();
        this.port = port;

    }

    public List<Broker> getBrokers() {
        return null;
    }

    public void connect() {
        sc = new Scanner(System.in);
        address = "localhost";
        socket = new Socket(address, port);
        System.out.println("Socket= " + socket);

        oos = new ObjectOutputStream((socket.getOutputStream()));
        
        for(int i = 0; i < videoFileChunk.size(); i++){
            for(int j = 0; j <videoFileChunk.get(i).length; j++){
            oos.writeObject(videoFileChunk.get(i).[j]);
          }  
        }
        
        socket.close();
    }

    public void disconnect() {
       
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
        
    }

    public Broker HashTopic(String s) {
        return null;
    }

    public void push(String path) {
        generateChunks(path);
        connect();
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
        Mp4Parse mp = new Mp4Parse(path);
        
        chunks = mp.parse();
        vf.setVideoFileChunk(chunks);

    }

    public static void main(String args[]) throws Exception{
        ChannelName channel = new ChannelName("fotis_panos");
        Publisher publisher = new Publisher(5000, channel.getChannelName());
        
    }
}
