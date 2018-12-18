package reader;

import debugging.Exceptions.MissingFileException;
import debugging.Exceptions.ResourceLoadingException;
import debugging.LogTypes;
import debugging.Logger;
import medias.Media;
import medias.Movie;
import medias.Serie;
import medias.types.MediaTypes;
import ui.cards.MediaPreviewCard;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * class reasponsible for reading all medias
 */
public class MediaHandler {

    // Make it a singleton since there is no reason to read all media multiple times
    private static MediaHandler instance;
    public static MediaHandler getInstance(){
        if (instance == null){
            instance = new MediaHandler();
        }
        return instance;
    }

    // Container of all media present in the project
    private List<Media> medias;

    private MediaHandler(){
        this.medias = new ArrayList<>();
        // Get list of media to be loaded
        String[] loadedMedias = MediaTypes.getLoadedMediaTypes();
        for ( String media: loadedMedias ){
            // Get path to media csv file
            Logger.log("Initializing " + media);
            String mediaPath = "res/" + media + ".csv";
            loadMedia( mediaPath, MediaTypes.getTypeFromString(media) );
        }
    }

    public static void updateMediaCards(List<Media> favorites) {
        for (Media media: instance.medias){
            MediaPreviewCard card = media.getPreviewCard();
            card.setActive(favorites.contains(media));
        }
    }

    /**
     * This functions reade a given media csv file and convert it to a list of elements of the specified MediaType
     * @param path : csv file path
     * @param mediaType : MediaType to convert rows in csv file to
     */
    private void loadMedia( String path, MediaTypes mediaType ){

        CSVReader reader = null;
        try {
            reader = new CSVReader(path, new int[]{mediaType.getColumnCount()});
        } catch (MissingFileException e) {
            e.logError(LogTypes.SOFTERROR);
            return;
        } catch (ResourceLoadingException e){
            e.logError(LogTypes.SOFTERROR);
            return;
        }

        Iterator<String[]> ite = reader.getIterator();
        // Iterate over all rows in the csv file
        ArrayList<Exception> errors = new ArrayList<>();
        int mediaCount = 0;
        while (ite.hasNext()){
            // Create Media of MediaType using MediaFactory
            Media media = null;
            try {
                media = Media.getMediaByMediaType(mediaType, ite.next());
                mediaCount++;
            } catch (ExceptionInInitializerError e) {
                errors.add((Exception) e.getException());
            }
            if ( media != null ){
                medias.add(media);
            }
        }
        Logger.logErrors(errors, "Errors during creation of " + mediaType.getName(), LogTypes.SOFTERROR);
        Logger.log("Created " + mediaCount + " " + mediaType.getName());

    }

    /**
     * Get a Media by its title
     * @param title The title of the Media
     * @return return either the Media or Null
     */
    public Media getMedia(String title){
        for(Media m : medias){
            if(m.getTitle().toLowerCase().equals(title.toLowerCase())){
                return m;
            }
        }
        return null;
    }

    public static List<Media> getAllMedia(){
        MediaHandler ins = getInstance();
        List<Media> newMedia = new ArrayList<>(ins.medias);
        return newMedia;
    }

    public static void addMovie(String title, String year, double rating, String age, String time, Object[] categories, BufferedImage image) {
        MediaHandler ins = getInstance();
        int id = 0;
        for (Media med : ins.medias) {
            if (med instanceof Movie) {
                id++;
            }
        }
        StringBuilder builder = new StringBuilder();
        String[] cats = new String[categories.length];
        int index = 0;
        for (Object o : categories) {
            cats[index] = o.toString();
            builder.append(o.toString());
            if (categories.length != index - 1) builder.append(",");
            index++;
        }
        Movie movie = new Movie(id, title, year, cats, rating, age, time, image);
        ins.medias.add(movie);

        try {
            File file = new File("res/movies.csv");
            FileWriter writer = new FileWriter(file, true);
            PrintWriter out = new PrintWriter(writer);
            out.write("\n" + id + ";" + title + ";" + year + ";"
                    + builder.toString() + ";" + String.valueOf(rating).replace(".",",") + ";"
                    + age + ";" + time + ";");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addSeries(String title, double rating, String age, Object[] categories,
                                 BufferedImage image, String yearstart, String yearend,
                                 HashMap<Integer, ArrayList<String[]>> seasons){
        MediaHandler ins = getInstance();
        int id = 0;
        for (Media med : ins.medias) {
            if (med instanceof Serie) {
                id++;
            }
        }
        StringBuilder builder = new StringBuilder();
        String[] cats = new String[categories.length];
        int index = 0;
        for (Object o : categories) {
            cats[index] = o.toString();
            builder.append(o.toString());
            if (categories.length != index - 1) builder.append(",");
            index++;
        }

        StringBuilder seasonbuilder = new StringBuilder();
        for(int i : seasons.keySet()){
            seasonbuilder.append(i);
            seasonbuilder.append("-");
            for(String[] info : seasons.get(i)){
                String name = info[0];
                String time = info[1];
                seasonbuilder.append("(");
                seasonbuilder.append(name);
                seasonbuilder.append("%");
                seasonbuilder.append(time);
                seasonbuilder.append(")");
            }
        }

        Serie serie = new Serie(id, title, rating, age, cats,
                image, yearstart + "-" + yearend, seasonbuilder.toString());
        ins.medias.add(serie);

        try {
            File file = new File("res/series.csv");
            FileWriter writer = new FileWriter(file, true);
            PrintWriter out = new PrintWriter(writer);
            out.write("\n" + id + ";" + title + ";" + yearstart + "-" + yearend + ";" +
                    builder.toString() + ";" + rating + ";" + age + ";" + seasonbuilder.toString() + ";");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Media getMediaByID(int id){
        MediaHandler ins = getInstance();
        for (Media media: ins.medias){
            if (media.getId() == id){
                return media;
            }
        }
        return null;
    }

    public static Media getMediaByID(int id, boolean isSeries) {
        MediaHandler ins = getInstance();
        MediaTypes mediaTypes = isSeries ? MediaTypes.SERIES: MediaTypes.MOVIE;
        System.out.println(isSeries);
        for (Media media: ins.medias){
            if (mediaTypes.equals(media) && media.getId() == id){
                return media;
            }
        }
        return null;
    }
}
