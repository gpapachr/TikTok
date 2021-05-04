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


    public void extractDuplicates(List<Broker> b){
        for (int i = 0; i<b.size(); i++){
            for(int j = i+1; j<b.size(); j++){
                if (b.get(i) == b.get(j)){
                    b.remove(b.get(j));
                }
            }
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
                    brokerSocket = new Socket(address, 6000);
                    DataOutputStream dos = new DataOutputStream(brokerSocket.getOutputStream());
                    dos.writeUTF("update_nodes");
                    DataInputStream dis = new DataInputStream(brokerSocket.getInputStream());
                    int length = dis.readInt();
                    ObjectInputStream ois = new ObjectInputStream(brokerSocket.getInputStream());
                    Broker temp = null;
                    for (int i = 0; i<length; i++){
                        temp = (Broker) ois.readObject();
                        brokers.add(temp);
                    }
                    brokerSocket.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                try{
                    address = "localhost";
                    brokerSocket = new Socket(address, 7000);
                    DataOutputStream dos = new DataOutputStream(brokerSocket.getOutputStream());
                    dos.writeUTF("update_nodes");
                    DataInputStream dis = new DataInputStream(brokerSocket.getInputStream());
                    int length = dis.readInt();
                    ObjectInputStream ois = new ObjectInputStream(brokerSocket.getInputStream());
                    Broker temp = null;
                    for (int i = 0; i<length; i++){
                        temp = (Broker) ois.readObject();
                        brokers.add(temp);
                    }
                    brokerSocket.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            case 6000:
                try{
                    address = "localhost";
                    brokerSocket = new Socket(address, 5000);
                    DataOutputStream dos = new DataOutputStream(brokerSocket.getOutputStream());
                    dos.writeUTF("update_nodes");
                    DataInputStream dis = new DataInputStream(brokerSocket.getInputStream());
                    int length = dis.readInt();
                    ObjectInputStream ois = new ObjectInputStream(brokerSocket.getInputStream());
                    Broker temp = null;
                    for (int i = 0; i<length; i++){
                        temp = (Broker) ois.readObject();
                        brokers.add(temp);
                    }
                    brokerSocket.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                try{
                    address = "localhost";
                    brokerSocket = new Socket(address, 7000);
                    DataOutputStream dos = new DataOutputStream(brokerSocket.getOutputStream());
                    dos.writeUTF("update_nodes");
                    DataInputStream dis = new DataInputStream(brokerSocket.getInputStream());
                    int length = dis.readInt();
                    ObjectInputStream ois = new ObjectInputStream(brokerSocket.getInputStream());
                    Broker temp = null;
                    for (int i = 0; i<length; i++){
                        temp = (Broker) ois.readObject();
                        brokers.add(temp);
                    }
                    brokerSocket.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            case 7000:
                try{
                    address = "localhost";
                    brokerSocket = new Socket(address, 5000);
                    DataOutputStream dos = new DataOutputStream(brokerSocket.getOutputStream());
                    dos.writeUTF("update_nodes");
                    DataInputStream dis = new DataInputStream(brokerSocket.getInputStream());
                    int length = dis.readInt();
                    ObjectInputStream ois = new ObjectInputStream(brokerSocket.getInputStream());
                    Broker temp = null;
                    for (int i = 0; i<length; i++){
                        temp = (Broker) ois.readObject();
                        brokers.add(temp);
                    }
                    brokerSocket.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                try{
                    address = "localhost";
                    brokerSocket = new Socket(address, 6000);
                    DataOutputStream dos = new DataOutputStream(brokerSocket.getOutputStream());
                    dos.writeUTF("update_nodes");
                    DataInputStream dis = new DataInputStream(brokerSocket.getInputStream());
                    int length = dis.readInt();
                    ObjectInputStream ois = new ObjectInputStream(brokerSocket.getInputStream());
                    Broker temp = null;
                    for (int i = 0; i<length; i++){
                        temp = (Broker) ois.readObject();
                        brokers.add(temp);
                    }
                    brokerSocket.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
        }
        extractDuplicates(brokers);
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
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                
                Thread t = new Handler(clientSocket, ois, oos, dis, dos, brokers);
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
        broker.notifyBrokersOnChanges();
        broker.connect();
        broker.updateNodes();
    }
}

class Handler extends Thread{
    
    final ObjectInputStream ois;
    final ObjectOutputStream oos;
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket clientSocket;
    final List<Broker> temp_brokers = new ArrayList<Broker>();

    public Handler(Socket s, ObjectInputStream ois, ObjectOutputStream oos, DataInputStream dis, DataOutputStream dos, List<Broker> b){
        
        this.clientSocket = s;
        this.ois = ois;
        this.oos = oos;
        this.dis = dis;
        this.dos = dos;

        for(int i=0; i<b.size(); i++){
            this.temp_brokers.add(b.get(i));
        }
    }

    @Override
    public void run(){
        
        try{
            dos.writeInt(temp_brokers.size());
            for(int i=0; i<temp_brokers.size(); i++){
                oos.writeObject(temp_brokers.get(i));
            }            
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
