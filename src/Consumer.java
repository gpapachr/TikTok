import java.io.*;
import java.net.*;
import java.util.*;

public class Consumer implements ConsumerInterface, Node{

    //fields
    private Socket socket = null;
    private DataInputStream  dis   = null;
    private DataOutputStream dos = null;
    private String address;
    private int port;
    private Scanner sc;
    private String received;
    private String tosend;

    private Broker broker = null;
    private List<Broker> brokersList;


    //methods

    public Consumer(int port){
        this.port = port;
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
            System.out.println(socket);

            dis = new DataInputStream((socket.getInputStream()));
            dos = new DataOutputStream(socket.getOutputStream());


            System.out.println(dis.readUTF());
            tosend = sc.nextLine();
            dos.writeUTF(tosend);
            received = dis.readUTF();
            System.out.println("Closing this connection : " + socket);
            socket.close();
            System.out.println("Connection closed");
            socket.close();
            System.out.println(received);

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
            dis.close();
            dos.close();
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
