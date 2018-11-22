package medias;

public class Movie extends Media{

    public Movie(String title, String year, String genre, String rating){

        this.title = title;
        this.rating = Double.parseDouble(rating.replace(",", "."));
        this.year = Integer.parseInt(year);
        this.genre = genre.split(",");
    }



}
