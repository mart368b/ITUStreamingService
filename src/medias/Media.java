package medias;


import maincomponents.ImageHandler;
import medias.types.AgeTypes;
import medias.types.Genre;
import medias.types.MediaTypes;
import ui.cards.MediaPreviewCard;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class Media {
    protected int id;
    protected String title;
    protected String year;
    protected double rating;
    protected AgeTypes ageResctriction;
    protected Genre[] genres;
    protected BufferedImage img;
    protected MediaPreviewCard previewCard;

    protected void loadImage() throws ExceptionInInitializerError{
        ImageHandler handler = ImageHandler.getInstance();
        handler.loadResource(MediaTypes.getMediaType(this).getName() + "-images", title, "jpg");
        img = handler.getImage(title);
    }

    protected void createPreviewCard(){
        previewCard = new MediaPreviewCard(this);
    }

    public BufferedImage getImage(){
        return img;
    }

    public MediaPreviewCard getPreviewCard(){
        return previewCard;
    }

    public String getTitle() { //Returnerer mediets titel
        return title;
    }

    public double getRating() { //Returnerer mediets bedømmelse
        return rating;
    }

    public int getId(){
        return id;
    }

    public AgeTypes getAgeRestriction(){return ageResctriction;}

    public String getYear() { //Returnerer årstallet, som mediet blev udgivet. (Har vi selv tilføjet til den vedhæftede data)
        return year;
    }
    public Genre getGenre(int i){
        return genres[i];
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
        for (int i = 0; i < genres.length; i++){
            if ( i != 0){
                builder.append(" ,");
            }
            builder.append(genres[i].getName());
        }
        return builder;
    }

    public static int getTimeInMinutes(String time){ //113h 12m    11m
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

    public static Media getMediaByMediaType( MediaTypes mediaType, String[] information ) throws ExceptionInInitializerError {
        switch (mediaType) {
            case MOVIE:
                return new Movie(information[0], information[1], information[2], information[3], information[4], information[5], information[6] );
            case SERIES:
                return new Serie(information[0], information[1], information[2], information[3], information[4], information[5], information[6]);
            default:
                return null;
        }
    }

    public boolean hasGenre(Genre keyGenre){
        for(Genre genre: genres){
            if (genre.equals(keyGenre)){
                return true;
            }
        }
        return false;
    }

    public Genre[] getGenres(){
        return genres;
    }
}