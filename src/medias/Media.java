package medias;
import java.util.ArrayList;

public abstract class Media {
    protected String title;
    protected String year;
    protected double rating;
    protected int ageResctriction;
    protected String[] genre;


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
    public String getGenre(int i){
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
        builder.append(String.join(", ", genre));
        return builder;
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
    public static Media getMediaByMediaType( MediaTypes mediaType, String[] information ){
        switch (mediaType){
            case MOVIE:
                return new Movie( information[0], information[1], information[2], information[3], information[4]);
            case SERIES:
                return  new Serie( information[0], information[1], information[2], information[3], information[4], information[5]);
            default:
                return null;
        }
    }
}