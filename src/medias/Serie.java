package medias;

import java.util.HashMap;
import java.util.ArrayList;

public class Serie extends Media {

    //private HashMap<Integer, ArrayList<Integer>> episodes; //Første input i hashmappet er sæsoner, andet er en array af episoder

    private HashMap<Integer, ArrayList<SeriesEpisode>> episodes;

    Serie(String title, String year, String genre, String rating, String ageResriction, String seasons) {

        this.title = title;
        this.rating = Double.parseDouble(rating.replace(",", "."));
        this.year = year;
        this.genre = genre.split(",");
        this.ageResctriction = Integer.parseInt(ageResriction);

        this.episodes = new HashMap<>();
        String[] seasonArr = seasons.split(",");
        for ( String season: seasonArr){
            // season = "seasonID-(Episode)(Episode)"
            String[] info = season.split("-");
            int seasonNumber = Integer.parseInt(info[0]);
            if ( !this.episodes.containsKey(seasonNumber) ){
                this.episodes.put(seasonNumber, new ArrayList<SeriesEpisode>());
            }
            ArrayList<SeriesEpisode> episodeContainer = this.episodes.get(seasonNumber);
            String episodeArr = info[1];
            if ( episodeArr.length() > 2){
                String[] episodes = episodeArr.substring(1, episodeArr.length() - 1).split("\\)\\(");
                for( String episode: episodes ){
                    String[] episodeInfo = episode.split(":");
                    SeriesEpisode newEpisode = new SeriesEpisode(info[1], episodeInfo[0], episodeInfo[1], episodeInfo[2]);
                    episodeContainer.add(newEpisode);
                }
            }
        }
    }

    @Override
    public StringBuilder getMediaInfo(){
        StringBuilder builder = super.getMediaInfo();
        builder.insert(0, "Serie: ");
        return builder;
    }

}