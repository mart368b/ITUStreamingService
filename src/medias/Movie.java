package medias;

import java.awt.image.BufferedImage;
import java.time.Year;

public class Movie extends Media{

    private int duration;

    Movie(String title, String year, String genre, String rating, String ageResctriction, String duration){
        this.title = title;
        this.rating = Double.parseDouble(rating.replace(",", "."));
        this.year = year;
        this.genre = Categories.getCategoriesByNames(genre.split(","));
        this.ageResctriction = ageResctriction;
        this.duration = getTimeInMinutes(duration);

        loadImage();
        createPreviewCard();
    }

    @Override
    public StringBuilder getMediaInfo(){
        StringBuilder builder = super.getMediaInfo();
        builder.insert(0, "Movie: ");
        return builder;
    }

}
