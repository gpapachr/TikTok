import java.util.*;

public class ChannelName {
    public String channelName;
    public ArrayList<String> hashtagsPublished;
    public HashMap<String, ArrayList<Value>> userVideoFilesMap;

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

    public HashMap<String, ArrayList<Value>> getUserVideoFilesMap() {
        return userVideoFilesMap;
    }

    public void setUserVideoFilesMap(HashMap<String, ArrayList<Value>> userVideoFilesMap) {
        this.userVideoFilesMap = userVideoFilesMap;
    }
}
