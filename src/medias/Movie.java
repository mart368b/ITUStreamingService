package medias;

public class Movie extends Media{

    Movie(String title, String year, String genre, String rating){
        this.title = title;
        this.rating = Double.parseDouble(rating.replace(",", "."));
        this.year = year;
        this.genre = Categories.getCategoriesByNames(genre.split(","));
        //this.ageResctriction = Integer.parseInt(ageResriction);
    }

    @Override
    public StringBuilder getMediaInfo(){
        StringBuilder builder = super.getMediaInfo();
        builder.insert(0, "Movie: ");
        return builder;
    }
}
