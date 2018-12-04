package medias;

import medias.types.AgeTypes;
import medias.types.GenreTypes;

import java.util.HashMap;
import java.util.ArrayList;

public class Serie extends Media {

    //private HashMap<Integer, ArrayList<Integer>> seasons; //Første input i hashmappet er sæsoner, andet er en array af episoder
    
    private HashMap<Integer, ArrayList<SeriesEpisode>> seasons;

    Serie(String id,String title, String year, String genre, String rating, String ageResriction, String seasons) {
        this.id = Integer.parseInt(id);
        this.title = title;
        this.genres = GenreTypes.getGenreTypeByNames(genre.split(","));
        this.rating = Double.parseDouble(rating.replace(",", "."));
        this.year = year;
        this.ageResctriction = AgeTypes.getAgeTypeFromName(ageResriction);;

        this.seasons = new HashMap<>();
        String[] seasonArr = seasons.split("_"); // 4-13
        for ( String season: seasonArr){
            addSeason(season);
        }

        loadImage();
        createPreviewCard();
    }

    private void addSeason(String season) {
        // season = "seasonID-(Episode)(Episode)"
        int splitter = season.indexOf("-");
        int seasonNumber = Integer.parseInt(season.substring(0, splitter));
        if ( !this.seasons.containsKey(seasonNumber) ){
            this.seasons.put(seasonNumber, new ArrayList<SeriesEpisode>());
        }
        String episodesInfo = season.substring(splitter, season.length() - 1);
        ArrayList<SeriesEpisode> episodeContainer = this.seasons.get(seasonNumber);
        if ( episodesInfo.length() <= 2){
            return;
        }
        String[] episodes = episodesInfo.split("\\)\\(");
        for (int i = 0; i < episodes.length; i++){
            String episode = episodes[i];
            addEpisode( seasonNumber, i, episodes.length, episode);
        }
    }

    private void addEpisode(int seasonNumber, int episodeNumber, int numberOfEpisodes, String episode) {
        if (episodeNumber == 0){
            episode = episode.substring(2);
        }else if (episodeNumber == numberOfEpisodes){
            episode = episode.substring(0, episode.length());
        }
        String[] episodeInfo = episode.split("%");

        int duration = getTimeInMinutes(episodeInfo[1]); // episode duration in seconds
        SeriesEpisode episodeClass = new SeriesEpisode(seasonNumber, episodeNumber, episodeInfo[0], duration);
        this.seasons.get(seasonNumber).add(episodeClass);
    }

    @Override
    public StringBuilder getMediaInfo(){
        StringBuilder builder = super.getMediaInfo();
        builder.insert(0, "Serie: ");
        return builder;
    }

    public HashMap<Integer, ArrayList<SeriesEpisode>> getSeasons(){
        return seasons;
    }

}