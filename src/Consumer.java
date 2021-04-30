import java.io.*;
import java.net.*;
import java.util.*;

public class Consumer extends Thread implements ConsumerInterface, Node{

    //fields
    private Socket socket = null;
    private DataInputStream  input   = null;
    private DataInputStream  in   = null;
    private DataOutputStream output = null;
    private Broker broker = null;
    private List<Broker> brokersList;
    private String address;
    private int port;

    //methods

    public Consumer(int port){
        init(port);
        connect();
        disconnect();
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
            socket = new Socket(Inet4Address.getLocalHost().getHostAddress(), port);

            // takes input from terminal
            input  = new DataInputStream(System.in);

            in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));

            // sends output to the socket
            output    = new DataOutputStream(socket.getOutputStream());

            // string to read message from input
            String line = "";
            String response = "";

            // keep reading until "Over" is input
            while (!line.equalsIgnoreCase("over"))
            {
                try
                {
                    line = input.readLine();
                    output.writeUTF(line);
                    response = in.readUTF();
                    System.out.println(response);
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void disconnect() {
        try
        {
            input.close();
            output.close();
            socket.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public void updateNodes() {

    }

    public static void main(String args[]){
        Consumer consumer = new Consumer(Integer.parseInt(args[0]));
    }
}
