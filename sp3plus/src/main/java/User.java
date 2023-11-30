import java.util.ArrayList;
import java.util.List;

public class User {
private String name;

//  recentlyViewed laves senere
    ArrayList <Media> watchList;
    ArrayList <Media> saveMedia;
    ArrayList <Media> allMedia;

    public void addMedia(Media media){
    watchList.add(media);
    }

    public void saveMedia(Media media){
        saveMedia.add(media);

    }

    public void  removeMedia(Media seriesToRemove, Media movieToRemove){
        if(saveMedia.contains(seriesToRemove)){
            saveMedia.remove(seriesToRemove);
            System.out.println("Removed " + seriesToRemove.getTitle()+ " from your list");
        } else if(saveMedia.contains(movieToRemove)) {
            saveMedia.remove(movieToRemove);
            System.out.println("Removed " + movieToRemove.getTitle()+ " from your list");
        } else{
            System.out.println("Media not found in your list");
        }
    }




    public User(String name) {
        this.name = name;
        this.watchList = new ArrayList<>();
        this.saveMedia = new ArrayList<>();
        this.allMedia = new ArrayList<>();

    }

    public String getName(){
        return name;

    }
    public ArrayList<Media> getWatchList() {
        return watchList;
    }
    public ArrayList<Media> getSaveMedia() {
        return saveMedia;
    }

    public ArrayList<Media> getAllMedia() {
        return allMedia;
    }

    public void addMediaToWatchList(Media media) {
        watchList.add(media);
    }

    public ArrayList<Media> searchBySpecfikNameInPersonalList(String s){
        ArrayList<Media> searchResult = new ArrayList<>();
        for (Media media: saveMedia){
            if(media.getTitle().equalsIgnoreCase(s)){
                searchResult.add(media);
                }
        }
        return searchResult;
    }


    private List<Media> saveMediaList;

    public List<Media> getSaveMediaList() {
        return saveMediaList;
    }
}
