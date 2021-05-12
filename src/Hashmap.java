import java.util.ArrayList;
import java.util.HashMap;

public class Hashmap{
    private HashMap<String, ArrayList<VideoFile>> videos = new HashMap<String, ArrayList<VideoFile>>();
    private VideoList videosToReturn = new VideoList();

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
        printMap();
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
        System.out.println("\n-------------Videos--------------\n");
        if(videos.isEmpty()){
            System.out.println("------------Empty-----------------");
        }
        for (String key: videos.keySet()){
            if(!videos.get(key).isEmpty()){
                System.out.print(key);
                for(int i = 0; i<videos.get(key).size(); i++){
                    System.out.print("-> " + videos.get(key).get(i).getVideoName());
                }
                System.out.print("\n");
            }        
        }
        System.out.print("\n");
    }

    public void deleteValue(String videoName, String channel){
        for (String key : videos.keySet()){                                                        //gia ola ta kleidia tou map
            for(int i = 0; i < videos.get(key).size(); i++){                                       //gia ola ta soixia stis listes tous
                if(videos.get(key).get(i).getVideoName().equals(videoName)){                       //an to onoma tou i video sthn lista einai videoname
                    if(videos.get(key).get(i).getChannelName().equals(channel)){                   //an to onoma tou channel tou i video sthn lista einai channel
                        videos.get(key).remove(i);                                          
                    }
                }
            }
        }
        printMap();
    }

    public VideoList search(String key){
        if(contains(key)){
            for(int i = 0; i < videos.get(key).size(); i++){
                videosToReturn.addVideo(videos.get(key).get(i));
            }
            return videosToReturn;
        }
        else{
            return null;
        }
    }
}