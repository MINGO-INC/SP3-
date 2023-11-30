import java.util.ArrayList;

public class Media {
    private String title;
    private ArrayList<String> genres=new ArrayList<>();
    private String releasDate;
    private String rating;
    Media(String title, String releasDate,ArrayList<String> genres,String rating){
        this.title=title;
        this.releasDate=releasDate;
        this.genres=genres;
        this.rating=rating;
    }


    public String getTitle() {
        return title;
    }
    public String toString() {
        return this.title + ", " + this.releasDate + ", " + "categories: " + showCategories() + rating+"\n";
    }


    public ArrayList<String> getGenres() {
        return genres;
    }

    public String getReleasDate() {
        return releasDate;
    }

    public String getRating() {
        return rating;
    }

    public String showCategories(){
        String categories="";
        for (String c: genres){
            categories+=c+",";
        }
        return categories;

    }
}
