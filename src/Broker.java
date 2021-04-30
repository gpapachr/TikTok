import java.io.*;
import java.net.*;
import java.util.*;

public class Broker extends Thread implements BrokerInterface, Node{
    //fields
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream input   = null;
    private DataOutputStream output = null;

    private int port;
    //-------------------------

    Broker(int port){
        init(port);
        connect();
        disconnect();
    }

    //  broker implementation
    public void calculateKeys() {

    }

    public Publisher acceptConnection(Publisher p) {
        return null;
    }

    public Consumer acceptConnection(Consumer c) {
        return null;
    }

    public void notifyPublisher(String s) {

    }

    public void notifyBrokersOnChanges() {

    }

    public void pull(String s) {

    }

    public void filterConsumers(String s) {

    }


    //node methods

    public void init(int port) {
        try{
            this.port = port;
            server = new ServerSocket(port);

            System.out.println("Server started");

            System.out.println("Waiting for a client ...");
        }
        catch(Exception e){
            System.out.println(e);
        }

    }

    public List<Broker> getBrokers() {
        return null;
    }

    public void connect() {
        try{
            socket = server.accept();
            System.out.println("Client accepted");

            // takes input from the client socket
            input = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));

            output    = new DataOutputStream(socket.getOutputStream());

            String line = "";

            // reads message from client until "Over" is sent
            while (!line.equalsIgnoreCase("over"))
            {
                try
                {
                    line = input.readUTF();
                    System.out.println(line);
                    output.writeUTF("Got it!");

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
        try{
            socket.close();
            input.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public void updateNodes() {

    }

    public static void main(String args[])
    {
        Broker broker = new Broker(Integer.parseInt(args[0]));
    }
}
