import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Consumer implements ConsumerInterface, Node, Serializable{

    //fields
    private String channelName;
    private transient Socket socket = null;
    private transient ObjectInputStream ois = null;
    private transient ObjectOutputStream oos = null;
    private transient DataOutputStream dos = null;
    private transient DataInputStream dis = null;
    private String address;
    private int port;
    private Scanner sc;
    private static int mode = -1;

    private Broker broker = null;
    
    //methods

    public Consumer(int port, String channelName){
        this.channelName = channelName;
        init(port);
    }


    //Consumer Methods Implementation

    public void register(Broker b, String s) {

    }

    public void disconnect(Broker b, String s) {

    }

    public void playData(String s, Value v) {

    }

    //Node Methods Implementation

    public void init(int port) {
        this.port = port;
    }

    public List<Broker> getBrokers() {
        return null;
    }

    public void connect() {
        try{
            //sc = new Scanner(System.in);
            address = "localhost";
            socket = new Socket(address, port);
            System.out.println("Socket= " + socket + "\n");

            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(mode);

            dis = new DataInputStream(socket.getInputStream());
            //ois = new ObjectInputStream(socket.getInputStream());
            //oos = new ObjectOutputStream(socket.getOutputStream());


           /* 

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
            System.out.println("Connection closed");*/
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            dos.close();
            dis.close();
            ois.close();
            oos.close();
            socket.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();  
        }
    }

    public void updateNodes() {

    }

    public void searchVideo(String key) {
        try {
            mode = 1;
            connect();
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(key);
            //dos.writeUTF(key);
            //int loop = dis.readInt();
            ois = new ObjectInputStream(socket.getInputStream());
            int loop = (int) ois.readObject();
            System.out.println(loop);
            VideoList returnedVideos = new VideoList();
            for (int i=0; i<loop; i++){               
                VideoFile temp = (VideoFile) ois.readObject();
                returnedVideos.addVideo(temp);               
            }
            dos.writeUTF("done");
            returnedVideos.print();

            System.out.println("Choose video by its name: ");
            Scanner temp = new Scanner(System.in);

            String response = temp.next();

            for (int i=0; i<returnedVideos.size(); i++){

                System.out.println(returnedVideos.getVideo(i).getVideoName() + " == " + response + "?");
                if(returnedVideos.getVideo(i).getVideoName().equalsIgnoreCase(response)){
                    String videoFile;
                    OutputStream os;

                    System.out.println("Chunks to be returned: " + returnedVideos.getVideo(i).chunksNumber());

                    String videoFileName = returnedVideos.getVideo(i).getPath().substring(0, returnedVideos.getVideo(i).getPath().lastIndexOf(".")); // Name of the videoFile without extension
                    File splitFile = new File("./chunks/"+ videoFileName);//Destination folder to save.
                    if (!splitFile.exists()) {
                        splitFile.mkdirs();
                        System.out.println("Directory Created -> "+ splitFile.getAbsolutePath());
                    }

                    
                    for (int j=0; j<returnedVideos.getVideo(i).chunksNumber(); j++){
                        videoFile = splitFile.getAbsolutePath() + "-" + String.format("%02d", j) +"_"+ "test";
                        System.out.println("File created: " + videoFile);
                        os = new FileOutputStream(videoFile);
                        os.write(returnedVideos.getVideo(i).getVideoFileChunk(j));
                        os.close();
                    }
                    break;                   
                }
            }
            returnedVideos.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws Exception{
        Consumer consumer = new Consumer(Integer.parseInt(args[0]), args[1]);
        boolean logout = false;
        Scanner sc = new Scanner(System.in);
        
        while(!logout){
            System.out.println("---------Menu---------");
            System.out.println("1 - Search Video");
            System.out.println("0 - Exit");

            int response = sc.nextInt();

            while(response != 0 && response != 1){
                System.out.println("Invalid Input!");
                response = sc.nextInt();
            }

            try{
                switch(response){
                    case 0:
                        logout = true;
                        break;
                    case 1:
                        System.out.println("Give hashtag or channel name to search");
                        String key = sc.next();
                        consumer.searchVideo(key);                        
                        break;
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }    
    }
}
