import java.io.*;
import java.net.*;
import java.util.*;
import java.security.*;
import java.math.*;

public class Broker implements BrokerInterface, Node, Serializable{
    //fields
    private transient Socket clientSocket = null;
    private transient ServerSocket serverSocket = null;
    private transient Socket brokerSocket = null;

    private int port;
    public int id = 0;
    String address;

    private Boolean isOnline = false;
    private List<Consumer> registeredUsers;
    private List<Publisher> registeredPublishers;

    //-------------------------
    
    Broker(int port, int id){
        this.port = port;
        this.id= id;
        init(port);
    }
    public static String encryptThisString(String input)
    {
        try {
            // getInstance() method is called with algorithm SHA-1
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            // return the HashText
            return hashtext;
        }

        // For specifying wrong message digest algorithms
            catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
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
        switch(port){
            case 5000:
                try{
                    address = "localhost";
                    brokerSocket = new Socket(address, port);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            case 6000:
            case 7000:
        }
    }

    public void pull(String s) {

    }

    public void filterConsumers(String s) {

    }


    //node methods

    public void init(int port) {
        try{
            serverSocket = new ServerSocket(port);
            System.out.println(serverSocket);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public List<Broker> getBrokers() {
        return null;
    }

    public void connect() {
        while (true)
        {
            try{
                clientSocket = serverSocket.accept();
                System.out.println(clientSocket);

                System.out.println("A new client is connected : " + clientSocket);
                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                DataInputStream dio = new DataInputStream(clientSocket.getInputStream());
                
                Thread t = new ClientHandler(clientSocket, oos, dio, this);
                System.out.println("Assigning new thread for this client: " + t.getId());
                t.start();
            }
            catch (Exception e){
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {
        try{
            clientSocket.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void updateNodes() {
        if(isOnline==false){
            brokers.add(this);
            isOnline = true;
        }
        else{
            brokers.remove(this);
        }
    }

    public static void main(String args[]) throws Exception{
        Broker broker = new Broker(Integer.parseInt(args[0]), 1);
        broker.updateNodes();
        for(int i=0; i<brokers.size(); i++){
            System.out.println("Broker = " + brokers.get(i) + " "+ brokers.get(i).id);
        }
        broker.connect();
        broker.updateNodes();
    }
}

class ClientHandler extends Thread{
    
    final ObjectOutputStream oos;
    final Socket clientSocket;
    final Broker b;

    public ClientHandler(Socket s, ObjectOutputStream oos, Broker b){
        this.clientSocket = s;
        this.oos = oos;
        this.b = b;
    }

    @Override
    public void run(){
        
        try{
            oos.writeObject(b);
            this.clientSocket.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }

        try{
            // closing resources
            this.oos.close();
        }
        catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
