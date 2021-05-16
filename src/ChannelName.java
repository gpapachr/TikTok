import java.util.*;

public class ChannelName {
    public String channelName;
    public ArrayList<String> hashtagsPublished;
    public HashMap<String, ArrayList<VideoFile>> userVideoFilesMap;

    ChannelName(String channelName){
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public ArrayList<String> getHashtagsPublished() {
        return hashtagsPublished;
    }

    public void setHashtagsPublished(ArrayList<String> hashtagsPublished) {
        this.hashtagsPublished = hashtagsPublished;
    }

    public HashMap<String, ArrayList<VideoFile>> getUserVideoFilesMap() {
        return userVideoFilesMap;
    }

    public void setUserVideoFilesMap(HashMap<String, ArrayList<VideoFile>> userVideoFilesMap) {
        this.userVideoFilesMap = userVideoFilesMap;
    }
}
