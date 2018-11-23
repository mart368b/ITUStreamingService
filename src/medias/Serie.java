package medias;

import java.util.HashMap;
import java.util.ArrayList;

public class Serie extends Media {

    private HashMap<Integer, ArrayList<Integer>> episodes; //Første input i hashmappet er sæsoner, andet er en array af episoder

    Serie(String title, String year, String genre, String episodes, String rating) {

        this.title = title;
        this.rating = Double.parseDouble(rating.replace(",", "."));
        this.year = year;
        this.genre = genre.split(",");

        this.episodes = new HashMap<>();
        initializeSeasons(episodes);
    }

    @Override
    public StringBuilder getMediaInfo(){
        StringBuilder builder = super.getMediaInfo();
        builder.insert(0, "Serie: ");
        return builder;
    }

    public void initializeSeasons(String episodes){
        String[] episodeArr = episodes.split(","); //Laver en array, der sepererer således: 1-13 2-13 osv.
    }



    /**
     * Funktionen har til formål at returnere en episode for en given sæson.
     * @param season Dette er sæsonnummer
     * @param episode Dette er episodenummer
     * @return
     */

    /*
    public int getEpisode(int season, int episode){
        return episodes.get(season)[episode];
    }
    */

}