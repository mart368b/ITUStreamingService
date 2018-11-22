package medias;
import java.util.ArrayList;

public abstract class Media {

    protected String title;
    protected double rating;
    protected int year;
    protected String[] genre;

    //GETTERS

    public String getTitle() { //Returnerer mediets titel
        return title;
    }

    public double getRating() { //Returnerer mediets bedømmelse
        return rating;
    }

    public int getYear() { //Returnerer årstallet, som mediet blev udgivet. (Har vi selv tilføjet til den vedhæftede data)
        return year;
    }
    public String getGenre(int i){
        return genre[i];
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
                return new Movie( information[0], information[1], information[2], information[3]);
            case SERIES:
                return  new Serie( information[0], information[1], information[2], information[2], information[3]);
            default:
                return null;
        }
    }
}