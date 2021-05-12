import java.util.ArrayList;

public class VideoList{

    ArrayList<VideoFile> videos = new ArrayList<VideoFile>();
    VideoList(){

    }

    public void addVideo(VideoFile video){
        videos.add(video);
    }
    
    public VideoFile getVideo(int i){
        return videos.get(i);
    }

    public boolean isEmpty(){
        return videos.isEmpty();
    }

    public int size(){
        return videos.size();
    }

    public void print(){
        System.out.println("\n-----------Videos Found---------\n");
        if(isEmpty()){
            System.out.println("null");
        }
        for(int i=0; i<videos.size(); i++){
            System.out.println(videos.get(i).getVideoName() + " by: " + videos.get(i).getChannelName());
        }
        System.out.println();
    }
}