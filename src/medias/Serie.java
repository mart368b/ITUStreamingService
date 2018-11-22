package medias;

import java.util.HashMap;
import java.util.ArrayList;

public class Serie extends Media {

    protected HashMap<Integer, ArrayList<Integer>> episodes; //Første input i hashmappet er sæsoner, andet er en array af episoder

    public Serie(String title, String rating, String year, String genre, String episodes) {

        this.title = title;
        this.rating = Double.parseDouble(rating);
        this.year = Integer.parseInt(year);
        this.genre = genre.split(",");

        System.out.println(this);
        this.episodes = new HashMap<>();
        initializeSeasons(episodes);
    }

    public String toString(){
        return title + " : " + rating + " : " + year + " : " + genre;
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