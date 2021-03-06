package com.example.myapplication.TikTokApp;

import android.util.Log;

import java.io.*;
import java.net.*;
import java.util.*;

public class Consumer implements ConsumerInterface, Node, Serializable {

    //fields
    private String channelName;
    private transient Socket socket = null;
    private transient ObjectInputStream ois = null;
    private transient ObjectOutputStream oos = null;
    private transient DataOutputStream dos = null;
    private transient DataInputStream dis = null;
    private String address;
    private int port;
    private static int mode = -1;

    //methods

    public Consumer(int port, String channelName) {
        this.channelName = channelName;
        init(port);
    }


    //Consumer Methods Implementation

    public void register(Broker b, String s) {

    }

    public void disconnect(Broker b, String s) {

    }

    public void playData(String s, VideoFile v) {
        try {
            File splitFiles = new File(s);// get all files to join
            if (splitFiles.exists()) {
                File[] files = splitFiles.getAbsoluteFile().listFiles();
                Arrays.sort(files);
                if (files.length != 0) {
                    System.out.println("Total files to be join: " + files.length);

                    String joinFileName = v.getVideoName() + ".mp4";
                    System.out.println("Join file created with name -> " + joinFileName);
                    File fileJoinPath = new File(s);// merge video files saved in this location

                    OutputStream outputStream = new FileOutputStream(s + "/" + v.getVideoName() + ".mp4");

                    for (File file : files) {
                        System.out.println("Reading the file -> " + file.getName());
                        InputStream inputStream = new FileInputStream(file);

                        int readByte = 0;
                        while ((readByte = inputStream.read()) != -1) {
                            outputStream.write(readByte);
                        }
                        inputStream.close();
                    }

                    System.out.println("Join file saved at -> " + fileJoinPath.getAbsolutePath() + "/" + joinFileName);
                    outputStream.close();
                } else {
                    System.err.println("No Files exist in path -> " + splitFiles.getAbsolutePath());
                }
            } else {
                System.err.println("This path doesn't exist -> " + splitFiles.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init(int port) {
        this.port = port;
        address = "10.0.2.2";
    }

    public List<Broker> getBrokers() {
        return null;
    }

    public void connect() {
        try {
            socket = new Socket(address, port);
            System.out.println("Socket= " + socket + "\n");

            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(mode);

            dis = new DataInputStream(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("aaaaa", e.toString());
        }
    }

    public void disconnect() {
        try {
            dos.close();
            dis.close();
            ois.close();
            oos.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateNodes() {

    }

    public void searchVideo(String key) {
        try {
            mode = 1;
            Log.e("aaaaa", "connect");
            connect();
            Log.e("aaaaa", "connect indeed");
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(key);
            oos.writeObject(this.channelName);
            ois = new ObjectInputStream(socket.getInputStream());
            int loop = (int) ois.readObject();
            System.out.println("Videos found: " + loop);
            VideoList returnedVideos = new VideoList();
            for (int i = 0; i < loop; i++) {
                VideoFile temp = (VideoFile) ois.readObject();
                returnedVideos.addVideo(temp);
            }
            dos.writeUTF("done");
            returnedVideos.print();

            System.out.println("Choose video by its name: ");
            Scanner temp = new Scanner(System.in);

            String response = temp.next();

            for (int i = 0; i < returnedVideos.size(); i++) {

                if (returnedVideos.getVideo(i).getVideoName().equalsIgnoreCase(response)) {
                    String videoFile;
                    OutputStream os;

                    System.out.println("Chunks to be returned: " + returnedVideos.getVideo(i).chunksNumber());

                    String videoFileName = returnedVideos.getVideo(i).getVideoName(); // Name of the videoFile without extension
                    File splitFile = new File("./chunks/" + videoFileName);//Destination folder to save.
                    if (!splitFile.exists()) {
                        splitFile.mkdirs();
                        System.out.println("Directory Created -> " + splitFile.getAbsolutePath());
                    }


                    for (int j = 0; j < returnedVideos.getVideo(i).chunksNumber(); j++) {
                        videoFile = "./chunks/" + videoFileName + "/" + String.format("%02d", j) + "_" + videoFileName + ".mp4";
                        System.out.println("File created: " + videoFile);
                        os = new FileOutputStream(videoFile);
                        os.write(returnedVideos.getVideo(i).getVideoFileChunk(j));
                        os.close();
                    }

                    playData("./chunks/" + videoFileName, returnedVideos.getVideo(i));
                    break;
                }
            }
            returnedVideos.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws Exception {
        Consumer consumer = new Consumer(Integer.parseInt(args[0]), args[1]);
        boolean logout = false;
        Scanner sc = new Scanner(System.in);

        while (!logout) {
            System.out.println("---------Menu---------");
            System.out.println("1 - Search Video");
            System.out.println("0 - Exit");

            int response = sc.nextInt();

            while (response != 0 && response != 1) {
                System.out.println("Invalid Input!");
                response = sc.nextInt();
            }

            try {
                switch (response) {
                    case 0:
                        logout = true;
                        break;
                    case 1:
                        System.out.println("Give hashtag or channel name to search");
                        String key = sc.next();
                        consumer.searchVideo(key);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
