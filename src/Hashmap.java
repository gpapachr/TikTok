import java.util.ArrayList;
import java.util.HashMap;

public class Hashmap{
    private HashMap<String, ArrayList<VideoFile>> videos = new HashMap<String, ArrayList<VideoFile>>();

    Hashmap(){

    }

    public boolean contains(String key){
        for (String i: videos.keySet()){
            if(i.equals(key)){
                return true;
            }
        }
        return false;
    }
    public void addNew(String key, VideoFile vf){
        if(!contains(key)){
            videos.put(key, new ArrayList<VideoFile>());
        }
        videos.get(key).add(vf);
    }

    public VideoFile getVideo(String key, int i){
        return videos.get(key).get(i);
    }

    public int getMapSize(){
        return videos.size();
    }

    public int getListSize(String key){
        return videos.get(key).size();
    }

    public void printMap(){
        for (String key: videos.keySet()){
            System.out.print(key);
            for(int i = 0; i<videos.get(key).size(); i++){
                System.out.print("-> " + videos.get(key).get(i));
            }
            System.out.print("\n");
        }
    }

    public void deleteValue(String videoName, String channel){
        for (String key : videos.keySet()){
            for(int i = 0; i < videos.get(key).size(); i++){
                if(videos.get(key).get(i).getVideoName().equals(videoName)){
                    if(videos.get(key).get(i).getChannelName() == channel){
                        videos.get(key).remove(i);
                        if(videos.get(key)==null){
                            videos.remove(key);
                        }
                    }
                }
            }
        }
    }
}