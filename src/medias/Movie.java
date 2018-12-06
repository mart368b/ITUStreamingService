package medias;

import medias.types.AgeTypes;
import medias.types.Genre;

public class Movie extends Media{

    private int duration;

    Movie(String id, String title, String year, String genre, String rating, String ageResriction, String duration){
        this.id = Integer.parseInt(id);
        this.title = title;
        this.rating = Double.parseDouble(rating.replace(",", "."));
        this.year = year;
        this.genres = Genre.getGenresByNames(genre.split(","));
        this.ageResctriction = AgeTypes.getAgeTypeFromName(ageResriction);
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

    public int getDuration() {
        return duration;
    }
}
