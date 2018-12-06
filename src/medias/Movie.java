package medias;

import medias.types.AgeTypes;
import medias.types.GenreTypes;

import java.awt.image.BufferedImage;

public class Movie extends Media{

    private int duration;

    public Movie(String id, String title, String year, String genre, String rating, String ageResriction, String duration){
        this.id = Integer.parseInt(id);
        this.title = title;
        this.rating = Double.parseDouble(rating.replace(",", "."));
        this.year = year;
        this.genres = GenreTypes.getGenreTypeByNames(genre.split(","));
        this.ageResctriction = AgeTypes.getAgeTypeFromName(ageResriction);
        this.duration = getTimeInMinutes(duration);

        loadImage();
        createPreviewCard();
    }

    public Movie(int id, String title, String year, String[] genres, double rating, String age, String time, BufferedImage image){
        this.id = id;
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.genres = GenreTypes.getGenreTypeByNames(genres);
        this.ageResctriction = AgeTypes.getAgeTypeFromName(age);
        this.duration = getTimeInMinutes(time);
        this.img = image;

        createPreviewCard();
    }

    @Override
    public StringBuilder getMediaInfo(){
        StringBuilder builder = super.getMediaInfo();
        builder.insert(0, "Movie: ");
        return builder;
    }

    public int getDuration() {
        return duration;
    }
}
