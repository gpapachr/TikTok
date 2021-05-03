import java.io.*;
import java.net.*;
import java.util.*;
import java.security.*;
import java.math.*;

public class Broker implements BrokerInterface, Node{
    //fields
    private Socket clientSocket = null;
    private ServerSocket serverSocket = null;
    private int port;

    private List<Broker> brokersList;

    private List<Consumer> registeredUsers;
    private List<Publisher> registeredPublishers;


    //-------------------------

    Broker(int port){
        this.port = port;
        init(port);
        connect();
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
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

                Thread t = new ClientHandler(clientSocket, dis, dos);
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

    }

    public static void main(String args[]) throws Exception{
        Broker broker = new Broker(Integer.parseInt(args[0]));
    }
}

class ClientHandler extends Thread{
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket clientSocket;

    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos){
        this.clientSocket = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run(){
        String received;
        String response;


        try{
            dos.writeUTF("What do u want?");

            received = dis.readUTF();

            System.out.println(received);
            response = "Here is your answer: *********";
            dos.writeUTF(response);
            this.clientSocket.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }


        try{
            // closing resources
            this.dis.close();
            this.dos.close();
        }
        catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
