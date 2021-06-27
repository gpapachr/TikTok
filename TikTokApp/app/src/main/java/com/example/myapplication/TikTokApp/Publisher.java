package com.example.myapplication.TikTokApp;

import android.util.Log;

import java.net.*;
import java.util.*;
import java.io.*;

public class Publisher extends Thread implements PublisherInterface, Node, Serializable{
    
    
    private ChannelName channelName;
    private String address = "10.0.2.2";
    private int port;
    private transient Socket socket = null;

    private static int mode = -1;
    private static String videoToDelete;

    private Hashmap data = new Hashmap();
    
    private transient ObjectOutputStream oos = null;
    private transient DataOutputStream dos;
    private transient DataInputStream dis;
    
    private ArrayList<byte[]> chunks = new ArrayList<byte[]>();
    private transient VideoFile vf = null;
    
    Publisher(int port, ChannelName channelName){
        this.channelName = channelName;
        init(port);
        Log.e("DebugInfo: Constructor ok", "null");
    }

    public void init(int port) {
        this.port = port;
    }

    public List<Broker> getBrokers() {
        return null;
    }

    public void connect() { // mode: 1-consumer, 2-publisher upload, 3-publisher remove video
        try{
            socket = new Socket(address, port);
            System.out.println("Socket= " + socket + "\n");

            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(mode);

            dis = new DataInputStream(socket.getInputStream());
        }
        catch(Exception e){
            e.printStackTrace();
            Log.e("DebugInfo_in_connect_exception", e.toString());

        }
    }

    public void disconnect() {
        try{
            dos.close();
            oos.close();
            socket.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }       
    }

    public void updateNodes() {
        try{
            mode = 3;
            connect();
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(videoToDelete);
            oos.writeObject(channelName.getChannelName());
            try{
                dis.readUTF();
            }
            catch(Exception e){}
            disconnect();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void removeVideo(String Videoname){
        data.deleteValue(Videoname, channelName.getChannelName());
        videoToDelete = Videoname;
        updateNodes();
    }

    public void addHashTag(String s) {

    }

    public void removeHashTag(String s) {
        
    }

    public void getBrokerList() {
        
    }

    public Broker HashTopic(String s) {
        return null;
    }

    public void push(String path, String name, String hashtag) {
        try{
            generateChunks(path, name, hashtag);
    
            for(int i = 0; i<vf.getHashtagsSize(); i++){
                data.addNew(vf.getHashtag(i), vf);
            }
            mode = 2;
            connect();
            oos = new ObjectOutputStream((socket.getOutputStream()));
            oos.writeObject(vf);            
            try{
                dis.readUTF();
            }
            catch(Exception e){
                Log.e("DebugInfo_in_push_readUTF_exception", e.toString());
            }
            disconnect();
        }
        catch(Exception e){
            e.printStackTrace();
            Log.e("DebugInfo_in_push_exception", e.toString());
        }
        finally {
            Log.e("DebugInfo: finally OK", "null");
        }
    }

    public void notifyFailure(Broker b) {
        
    }

    public void notifyBrokersForHashTags(String s) {

    }
    
    public void generateChunks(String path, String name, String hashtag) {


        vf = new VideoFile(path, name);
        vf.setChannelName(channelName.getChannelName());
        vf.setAssociatedHashtags(hashtag);
        Mp4Parse mp = new Mp4Parse(path);
        
        mp.parse();
        for(int i=0; i<mp.getChunksNumber(); i++){
            chunks.add(mp.getChunk(i));
        }
       
        vf.setVideoFileChunk(chunks);
        mp.delete();
        chunks.clear();
        Log.e("DebugInfo_generateChunksOk", "null");

    }
//    public static void main(String args[]) throws Exception{
//        ChannelName channel = new ChannelName(args[1]);
//        Publisher publisher = new Publisher(Integer.parseInt(args[0]), channel);
//        boolean logout = false;
//        Scanner sc = new Scanner(System.in);
//
//        while(!logout){
//            System.out.println("---------Menu---------");
//            System.out.println("1 - Upload Video");
//            System.out.println("2 - Delete Video");
//            System.out.println("0 - Exit");
//
//            int response = sc.nextInt();
//
//            while(response != 0 && response != 1 && response != 2){
//                System.out.println("Invalid Input!");
//                response = sc.nextInt();
//            }
//
//            try{
//                switch(response){
//                    case 0:
//                        logout = true;
//                        break;
//                    case 1:
//                        System.out.println("Set video path");
//                        String file = sc.next();
//                        String name = sc.next();
//                        String hashtag = sc.next();
//                        publisher.push(file, name, hashtag);
//                        System.out.println("Video Upload completed!");
//                        break;
//                    case 2:
//                        try{
//                            System.out.println("Give Video name to remove");
//                            name = sc.next();
//                            publisher.removeVideo(name);
//                            System.out.println("Video deleted");
//                        }
//                        catch(Exception e){
//                            System.out.println("Video not found");
//                        }
//                        break;
//                }
//            }
//            catch(Exception e){
//                e.printStackTrace();
//            }
//        }
//        sc.close();
//    }
}
