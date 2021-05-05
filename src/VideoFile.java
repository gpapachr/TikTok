import java.util.*;
import java.io.File;
import org.mp4parser.IsoFile;
import org.mp4parser.boxes.iso14496.part12.MovieHeaderBox;

public class VideoFile {
    private String videoName;
    private String channelName;
    private String dateCreated;
    private long length;
    private String framerate;
    private String frameWidth;
    private String frameHeight;
    private ArrayList<String> associatedHashtags = new ArrayList<String>();
    private byte[] videoFileChunk;
    private String path;


    VideoFile(String s) {
        this.path = s;
        File video = new File(path);
        this.length = getVideoLength(video);
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setAssociatedHashtags(ArrayList<String> ah) {
        for (int i = 0; i < ah.size(); i++) {
            associatedHashtags.add(ah.get(i));
        }
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public byte[] getVideoFileChunk() {
        return null;
    }

    public void setVideoFileChunk(byte videoFileChunk) {
        this.videoFileChunk = videoFileChunk;
    }

    
























    public String getVideoName() {
        return videoName;
    }

    

    

   

    public String getDateCreated() {
        return dateCreated;
    }

    

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getFramerate() {
        return framerate;
    }

    public void setFramerate(String framerate) {
        this.framerate = framerate;
    }

    public String getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(String frameWidth) {
        this.frameWidth = frameWidth;
    }

    public String getFrameHeight() {
        return frameHeight;
    }

    public void setFrameHeight(String frameHeight) {
        this.frameHeight = frameHeight;
    }

    public ArrayList<String> getAssociatedHashtags() {
        return associatedHashtags;
    }

   

    
}
