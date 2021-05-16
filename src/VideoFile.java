import java.util.*;
import java.io.*;
import java.net.*;

public class VideoFile implements Serializable{
    private String videoName;
    private String channelName;
    private String dateCreated;
    private long length;
    private String framerate;
    private String frameWidth;
    private String frameHeight;
    private ArrayList<String> associatedHashtags = new ArrayList<String>();
    private ArrayList<byte[]> videoFileChunk;
    private String path;


    VideoFile(String s,String name) {
        this.path = s;
        this.videoName = name;
        File video = new File(path);
        //this.length = getVideoLength(video);
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setAssociatedHashtags(String ah) {
        associatedHashtags.add(ah);        
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public byte[] getVideoFileChunk(int i) {
        return videoFileChunk.get(i);
    }

    public void setVideoFileChunk(ArrayList<byte[]> chunks) {
        videoFileChunk = new ArrayList<byte[]>();
        for(int i=0; i < chunks.size(); i++){
            videoFileChunk.add(chunks.get(i));
        }
    }

    public int chunksNumber(){
        return videoFileChunk.size();
    }

    public String getChannelName() {
        return channelName;
    }

    public int getHashtagsSize() {
        return associatedHashtags.size();
    }

    public String getHashtag(int i) {
        return associatedHashtags.get(i);
    }

    public String getVideoName() {
        return videoName;
    }

    public String getPath() {
        return path;
    }
}