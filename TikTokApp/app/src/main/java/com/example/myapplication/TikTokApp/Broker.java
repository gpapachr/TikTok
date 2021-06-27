package com.example.myapplication.TikTokApp;


import java.io.*;
import java.net.*;
import java.util.*;

public class Broker implements BrokerInterface, Node, Serializable{
    //fields
    private transient Socket socket = null;
    private transient ServerSocket serverSocket = null;
    private transient Socket brokerSocket = null;

    private int port;
    public int id = 0;
    String address;

    private Boolean isOnline = false;

    private transient ObjectInputStream ois = null;
    private transient ObjectOutputStream oos = null;
    private transient DataInputStream dis = null;
    private transient DataOutputStream dos = null;

    Hashmap videos = new Hashmap();    

    //-------------------------
    
    Broker(int port, int id){
        this.port = port;
        this.id= id;
        init(port);
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

    public void notifyBrokersOnChanges() {//trying to add the broker-to-broker communication in a greedy way

    }

    public void pull(String s) {

    }

    public void filterConsumers(String s) {

    }

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
                socket = serverSocket.accept();
                System.out.println(socket);
                Thread t;
                
                DataInputStream typeInput = new DataInputStream(socket.getInputStream());
                int type = typeInput.readInt();
                switch(type){
                    case 1: // for consumer: search video mode
                        System.out.println("A new client is connected : " + socket);
                        ois = new ObjectInputStream(socket.getInputStream());                                    
                        oos = new ObjectOutputStream(socket.getOutputStream());                        
                        dis = new DataInputStream(socket.getInputStream());                      
                        dos = new DataOutputStream(socket.getOutputStream());
                        
                        t = new ClientHandler2(socket, ois, oos, dis, dos, this);
                        System.out.println("Assigning new thread for this client: " + t.getId());
                        t.start();
                        while(t.isAlive()){
                            //wait
                        }
                        socket.close();                        
                        break;
                    case 2: // for publisher: upload new video mode
                        System.out.println("A new publisher is connected : " + socket);
                        ois = new ObjectInputStream(socket.getInputStream());
                        oos = new ObjectOutputStream(socket.getOutputStream());
                        dis = new DataInputStream(socket.getInputStream());
                        dos = new DataOutputStream(socket.getOutputStream());

                        t = new PublisherHandler1(socket, ois, oos, dis, dos, this);
                        System.out.println("Assigning new thread for this publisher: " + t.getId());
                        t.start();
                        while(t.isAlive()){
                            //wait
                        }
                        socket.close();
                        break;
                    case 3: // for publisher: remove video mode
                        System.out.println("A new publisher is connected : " + socket);
                        ois = new ObjectInputStream(socket.getInputStream());
                        oos = new ObjectOutputStream(socket.getOutputStream());
                        dis = new DataInputStream(socket.getInputStream());
                        dos = new DataOutputStream(socket.getOutputStream());

                        t = new PublisherHandler2(socket, ois, oos, dis, dos, this);
                        System.out.println("Assigning new thread for this publisher: " + t.getId());
                        t.start();
                        while(t.isAlive()){
                            //wait
                        }
                        socket.close();
                        break;
                }                
            }
            catch (Exception e){
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {
        try{
            socket.close();
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
        broker.connect();
    }
}

class ClientHandler1 extends Thread{ // handler for broker sharing to consumer
    
    final ObjectInputStream ois;
    final ObjectOutputStream oos;
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket clientSocket;
    final List<Broker> temp_brokers = new ArrayList<Broker>();

    public ClientHandler1(Socket s, ObjectInputStream ois, ObjectOutputStream oos, DataInputStream dis, DataOutputStream dos, List<Broker> b){
        
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
class ClientHandler2 extends Thread{ // handler for video sharing to consumer
    
    final ObjectInputStream ois;
    final ObjectOutputStream oos;
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket clientSocket;
    final Broker broker;

    public ClientHandler2(Socket s, ObjectInputStream ois, ObjectOutputStream oos, DataInputStream dis, DataOutputStream dos, Broker broker){
            
        this.clientSocket = s;
        this.ois = ois;
        this.oos = oos;
        this.dis = dis;
        this.dos = dos;
        this.broker = broker;
    }

    @Override
    public void run(){        
        try{
            String key = (String) ois.readObject();
            String name = (String) ois.readObject();
            VideoList temp = broker.videos.search(key, name);

            int length = temp.size();
            oos.writeObject(length);
            for(int i = 0; i<length; i++){
                oos.writeObject(temp.getVideo(i));
            }
            try{
                dis.readUTF();
            }catch(Exception e){}
            broker.videos.overNout();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}


class PublisherHandler1 extends Thread{ // handler for publisher sharing a new video
    final ObjectInputStream ois;
    final ObjectOutputStream oos;
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket publisherSocket;
    final Broker broker;

    public PublisherHandler1(Socket s, ObjectInputStream ois, ObjectOutputStream oos, DataInputStream dis, DataOutputStream dos, Broker broker){
        
        this.publisherSocket = s;
        this.ois = ois;
        this.oos = oos;
        this.dis = dis;
        this.dos = dos;
        this.broker = broker;
    }
    @Override
    public void run(){
        try{
            VideoFile temp = (VideoFile) ois.readObject();

            broker.videos.addNew(temp.getChannelName(), temp);           
            
            for(int i=0; i<temp.getHashtagsSize(); i++){
                broker.videos.addNew(temp.getHashtag(i), temp);          
            }
            dos.writeUTF("done");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

class PublisherHandler2 extends Thread{// handler for publisher removing a video
    final ObjectInputStream ois;
    final ObjectOutputStream oos;
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket publisherSocket;
    final Broker broker;

    public PublisherHandler2(Socket s, ObjectInputStream ois, ObjectOutputStream oos, DataInputStream dis, DataOutputStream dos, Broker broker){
        
        this.publisherSocket = s;
        this.ois = ois;
        this.oos = oos;
        this.dis = dis;
        this.dos = dos;
        this.broker = broker;
    }
    @Override
    public void run(){
        try{           
            String video = (String) ois.readObject();
            String channel = (String) ois.readObject();

            System.out.println("Deleting.....");
            broker.videos.deleteValue(video, channel);            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
