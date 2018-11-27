package medias;
import debugging.exceptions.MediaCreationException;

import java.util.ArrayList;

public abstract class Media {
    protected String title;
    protected String year;
    protected double rating;
    protected String ageResctriction;
    protected Categories[] genre;

    //GETTERS

    public String getTitle() { //Returnerer mediets titel
        return title;
    }

    public double getRating() { //Returnerer mediets bedømmelse
        return rating;
    }

    public String getYear() { //Returnerer årstallet, som mediet blev udgivet. (Har vi selv tilføjet til den vedhæftede data)
        return year;
    }
    public Categories getGenre(int i){
        return genre[i];
    }

    public StringBuilder getMediaInfo(){
        StringBuilder builder = new StringBuilder();
        builder.append("Title = ");
        builder.append(title);
        builder.append(", Rating = ");
        builder.append(rating);
        builder.append(", Year = ");
        builder.append(year);
        builder.append("Genre(s) = ");
        for (int i = 0; i < genre.length; i++){
            if ( i != 0){
                builder.append(" ,");
            }
            builder.append(genre[i].getName());
        }
        return builder;
    }

    public static int getTimeInMinutes(String time){
        String timeString = time.replace("h", "h-").replace("min","m");
        String[] timeArr = timeString.split("-");
        int duration = 0;
        for( String timePart: timeArr){
            if (timePart.length() == 0){
                continue;
            }
            switch (timePart.charAt(timePart.length() - 1)){
                case 'h':
                    duration += Integer.parseInt(timePart.substring(0, timePart.length() - 1)) * 3600;
                    break;
                case 'm':
                    duration += Integer.parseInt(timePart.substring(0, timePart.length() - 1)) * 60;
            }
        }
        return duration;
    }

    public String toString(){
        return getMediaInfo().toString();
    }

    /**
     * Get a Media based on its assosiated MediaType
     * @param mediaType
     * @param information
     * @return
     */
    public static Media getMediaByMediaType( MediaTypes mediaType, String[] information ) throws MediaCreationException {
        try {
            switch (mediaType) {
                case MOVIE:
                    return new Movie(information[0], information[1], information[2], information[3], information[4], information[5]);
                case SERIES:
                    return new Serie(information[0], information[1], information[2], information[3], information[4], information[5]);
                default:
                    return null;
            }
        } catch (ExceptionInInitializerError e){
            throw new MediaCreationException(e);
        }
    }
}