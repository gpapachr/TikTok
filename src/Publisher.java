import java.net.*;
import java.util.*;
import java.io.*;

public class Publisher extends Thread implements PublisherInterface, Node, Serializable{
    private ChannelName channelName;
    private String address;
    private int port;
    private transient Socket socket = null;
    private static int mode = -1;
    private static String videoName;

    private ArrayList<VideoFile> videos = new ArrayList<VideoFile>();
    private ArrayList<String> hashtags = new ArrayList<String>();
    private HashMap<String, VideoFile> dict = new HashMap<String, VideoFile>();
    
    private ObjectOutputStream  oos = null;
    private DataOutputStream dos;
    
    private ArrayList<byte[]> chunks = new ArrayList<byte[]>();
    private transient VideoFile vf = null;
    
    Publisher(int port, ChannelName channelName){
        this.channelName = channelName;
        init(port);
    }

    public void init(int port) {
        videos = new ArrayList<VideoFile>();
        this.port = port;
    }

    public List<Broker> getBrokers() {
        return null;
    }

    public void connect() { // mode: 1-consumer, 2-publisher upload, 3-publisher remove video
        try{
            address = "localhost";
            socket = new Socket(address, port);
            System.out.println("Socket= " + socket);

            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(mode);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try{
            dos.close();
            oos.close();
            socket.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }       
    }

    public void updateNodes() {
        try{
            mode = 3;
            connect();
            dos.writeUTF(videoName);
            disconnect();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void removeVideo(String name){
        for (int i =0; i<videos.size(); i++){
            if(videos.get(i).getVideoName() == name){
                videos.remove(i);
                hashtags.clear();
                break;                
            }
        }
        for(int i = 0; i<videos.size(); i++){
            for(int j = 0; j<videos.get(i).getHashtagsSize(); j++){
                addHashTag(videos.get(i).getHashtag(j));
            }
        }
        videoName = name;
        updateNodes();
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

    public void push(String path, String name, String hashtag) {
        try{
            generateChunks(path, name, hashtag);
    
            for(int i = 0; i<vf.getHashtagsSize(); i++){
                addHashTag(vf.getHashtag(i));
            }
            videos.add(vf);
            mode = 2;
            connect();
            oos = new ObjectOutputStream((socket.getOutputStream()));
            oos.writeObject(vf);
            disconnect();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void notifyFailure(Broker b) {
        
    }

    public void notifyBrokersForHashTags(String s) {

    }
    
    public void generateChunks(String path, String name, String hashtag) {
        vf = new VideoFile(path, name);
        vf.setAssociatedHashtags(hashtag);
        Mp4Parse mp = new Mp4Parse(path);
        
        chunks = mp.parse();
        vf.setVideoFileChunk(chunks);
    }

    public static void main(String args[]) throws Exception{
        ChannelName channel = new ChannelName(args[1]);
        Publisher publisher = new Publisher(Integer.parseInt(args[0]), channel);
        boolean logout = false;
        Scanner sc = new Scanner(System.in);
        
        while(!logout){
            System.out.println("---------Menu---------");
            System.out.println("1 - Upload Video");
            System.out.println("2 - Delete Video");
            System.out.println("0 - Exit");

            int response = sc.nextInt();

            while(response != 0 && response != 1 && response != 2){
                System.out.println("Invalid Input!");
                response = sc.nextInt();
            }

            try{
                switch(response){
                    case 0:
                        logout = true;
                        break;
                    case 1:
                        System.out.println("Set video path");
                        String file = sc.next();
                        String name = sc.next();
                        String hashtag = sc.next();
                        publisher.push(file, name, hashtag);
                        System.out.println("Video Upload completed!");
                        break;
                    case 2:
                        System.out.println("Give Video name to remove");
                        name = sc.next();
                        publisher.removeVideo(name);
                        System.out.println("Video deleted");
                        break;                    
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
