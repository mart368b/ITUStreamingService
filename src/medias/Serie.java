package medias;

import java.util.HashMap;
import java.util.ArrayList;

public class Serie extends Media {
    protected HashMap<Integer, ArrayList<Integer>> episodes; //Første input i hashmappet er sæsoner, andet er en array af episoder

    public Serie(String title, double rating, int year, String genre, String episodes) {

        this.title = title;
        this.rating = rating;
        this.year = year;
        this.genre = genre.split(",");
        episodes = new HashMap<>();

        initializeSeasons(episodes);
    }


    public void initializeSeasons(String episodes){
        String[] episodeArr = episodes.split(","); //Laver en array, der sepererer således: 1-13 2-13 osv.
        for (int i = 1; i < episodeArr.Length; i++){
            String[] identifiers = data[i].split("-"); //Denne array skulle altid være på to elementer, episode og sæson således: 1 13 2 13
            if (episodes.containsValue(int.PassedString(identifiers))){

            } else {
                episodes.put();
            }

        }


    }







    /**
     * Funktionen har til formål at returnere en episode for en given sæson.
     * @param season Dette er sæsonnummer
     * @param episode Dette er episodenummer
     * @return
     */

    public int getEpisode(int season, int episode){
        return episodes.get(season)[episode];
    }

}