package medias;

public class Movie extends Media{

    private int runtime;

    public Movie(String title, String year, String genre, String rating, String age, String runtime){

        this.title = title;
        this.age_restriction = age;
        this.runtime = Integer.parseInt(runtime);
        this.rating = Double.parseDouble(rating.replace(",", "."));
        this.year = year;
        this.genre = genre.split(",");
    }

    @Override
    public StringBuilder getMediaInfo(){
        StringBuilder builder = super.getMediaInfo();
        builder.insert(0, "Movie: ");
        return builder;
    }
}
