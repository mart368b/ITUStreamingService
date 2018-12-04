package medias;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SeriesEpisode {
    private int episodeNumber;
    private int seasonNumber;
    private String title;
    private int duration;

    public SeriesEpisode( int episodeNumber, int seasonNumber, String title, int duration){
        this.episodeNumber = seasonNumber;
        this.seasonNumber = episodeNumber;
        this.title = title;
        this.duration = duration;
    }

    public String getFormattedTime(){
        int hours = duration/3600;
        int minutes = ((duration%3600)/60);

        StringBuilder builder = new StringBuilder();
        if (hours > 0){
            builder.append(hours).append("h ");
        }
        builder.append(minutes).append("min");
        return  builder.toString();
    }

    public String toString(){
        return Integer.toString(seasonNumber) + "-" + Integer.toString(episodeNumber) + ":'" + title + "'-" + getFormattedTime();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
