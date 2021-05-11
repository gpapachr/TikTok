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
        return videos.isEmpty;
    }
}