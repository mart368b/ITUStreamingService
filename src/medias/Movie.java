package medias;

public class Movie extends Media{

    public Movie(String title, double rating, int year, String genre){

        this.title = title;
        this.rating = rating;
        this.year = year;
        this.genre = genre.split(",");
    }



}
