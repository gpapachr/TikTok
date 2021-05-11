import java.io.*;
import java.net.*;
import java.util.*;

public class Consumer implements ConsumerInterface, Node, Serializable{

    //fields
    private ChannelName channelName;
    private transient Socket socket = null;
    private ObjectInputStream  ois = null;
    private ObjectOutputStream  oos = null;
    private DataOutputStream dos;
    private DataInputStream dis;
    private String address;
    private int port;
    private Scanner sc;

    private Broker broker = null;
    
    //methods

    public Consumer(int port, ChannelName channelName){
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

           /* ois = new ObjectInputStream((socket.getInputStream()));

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
        //disconnect();
    }

    public void disconnect() {
        try {
            dos.close();
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

    }

    public static void main(String args[]) throws Exception{
        Consumer consumer = new Consumer(Integer.parseInt(args[0]));
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
                        System.out.println("Press 1 for Channel Name, 2 for Hashtag");
                        int response = sc.nextInt();

                        while(response != 1 && response != 2){
                            System.out.println("Invalid Input!");
                            response = sc.nextInt();
                        }
                        if(response == 1){
                            System.out.println("Give Channel Name");
                            String response = sc.next();
                            consumer.searchVideo(response);
                        } 
                        else{
                            System.out.println("Give Hashtag");
                            String response = sc.next();
                            consumer.searchVideo(response);
                        }
                        break;
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }    
    }
}
