import java.io.*;
import java.net.*;
import java.util.*;
import java.security.*;
import java.math.*;

public class Broker implements BrokerInterface, Node, Serializable{
    //fields
    private transient Socket clientSocket = null;
    private transient ServerSocket serverSocket = null;
    private int port;
    private Boolean isOnline = false;
    private List<Consumer> registeredUsers;
    private List<Publisher> registeredPublishers;
    public int id = 0;

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

                Thread t = new ClientHandler(clientSocket, oos, brokers);
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
    final List<Broker> temp_brokers = new ArrayList<Broker>();

    public ClientHandler(Socket s, ObjectOutputStream oos, List<Broker> b){
        this.clientSocket = s;
        this.oos = oos;

        for (int i=0; i< b.size(); i++){
            this.temp_brokers.add(b.get(i));
        }
    }

    @Override
    public void run(){
        
        try{
            for (int i=0; i< temp_brokers.size(); i++){
                oos.writeObject(temp_brokers.get(i));
            }
            Broker end = null;
            oos.writeObject(end);
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
