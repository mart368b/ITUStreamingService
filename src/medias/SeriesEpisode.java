package medias;

public class SeriesEpisode {
    private int episodeNumber;
    private int seasonNumber;
    private String title;
    private int duration;

    public SeriesEpisode( String seasonNumber, String episodeNumber, String title, String duration){
        this.episodeNumber = Integer.parseInt(seasonNumber);
        this.seasonNumber = Integer.parseInt(seasonNumber);
        this.title = title;
        this.duration = Integer.parseInt(duration);
    }
}
