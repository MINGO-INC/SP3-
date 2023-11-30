import java.util.ArrayList;
public class Series extends Media {

    private String totalSeasons;
    private String totalEpisodes;

    public Series(String title, String releaseDate, ArrayList<String> genres, String rating, String totalSeasons) {
        super(title, releaseDate, genres, rating);
        this.totalSeasons = totalSeasons;
        this.totalEpisodes = totalEpisodes;
    }

    public String getTotalEpisodes() {
        return totalEpisodes;
    }
    private String getTotalSeasons() {
        return totalSeasons;
    }

    @Override
    public String toString() {
        return String.format("Title: %s\n" +
                        "Release Date: %s\n" +
                        "Genres: %s\n" +
                        "Rating: %s\n" +
                        "Total Seasons: %s\n",
                getTitle(), getReleasDate(), showCategories(), getRating(), getTotalSeasons());
    }

}



