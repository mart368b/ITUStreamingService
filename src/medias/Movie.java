package medias;

import medias.types.AgeTypes;
import medias.types.GenreTypes;

public class Movie extends Media{

    private int duration;

    Movie(String title, String year, String genre, String rating, String ageResriction, String duration){
        this.title = title;
        this.rating = Double.parseDouble(rating.replace(",", "."));
        this.year = year;
        this.genres = GenreTypes.getGenreTypeByNames(genre.split(","));
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

}
