import java.io.*;
import java.net.*;
import java.util.*;

public class Consumer implements ConsumerInterface, Node, Serializable{

    //fields
    private transient Socket socket = null;
    private ObjectInputStream  ois = null;
    private String address;
    private int port;
    private Scanner sc;

    private Broker broker = null;
    
    //methods

    public Consumer(int port){
        init(port);
        connect();
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

    public static void main(String args[]) throws Exception{
        Consumer consumer = new Consumer(Integer.parseInt(args[0]));
    }
}
