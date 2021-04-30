import java.util.*;

public class VideoFile {
    private String videoName;
    private String channelName;
    private String dateCreated;
    private String length;
    private String framerate;
    private String frameWidth;
    private String frameHeight;
    private ArrayList<String> associatedHashtags;
    private byte videoFileChunk;

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
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

    public void setAssociatedHashtags(ArrayList<String> associatedHashtags) {
        this.associatedHashtags = associatedHashtags;
    }

    public byte getVideoFileChunk() {
        return videoFileChunk;
    }

    public void setVideoFileChunk(byte videoFileChunk) {
        this.videoFileChunk = videoFileChunk;
    }
}
