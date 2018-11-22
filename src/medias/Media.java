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

}