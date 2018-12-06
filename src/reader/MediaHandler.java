package reader;

import debugging.Exceptions.MissingFileException;
import debugging.Exceptions.ResourceLoadingException;
import debugging.LogTypes;
import debugging.Logger;
import medias.Media;
import medias.types.MediaTypes;
import ui.cards.MediaPreviewCard;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * class reasponsible for reading all medias
 */
public class MediaHandler {

    public static void main(String[]args){
        MediaHandler.getInstance();
    }

    // Make it a singleton since there is no reason to read all media multiple times
    private static MediaHandler instance = new MediaHandler();
    public static MediaHandler getInstance(){ return instance;}

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

    public void updateMediaCards(List<Media> favorites) {
        for (Media media: medias){
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
            reader = new CSVReader(path, mediaType.getColumnCount());
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

    public void getAllMedia(ArrayList<Media> mediaList){
        mediaList.clear();
        for (Media media: medias){
            mediaList.add(media);
        }
    }

    public void getAllMedia(ArrayList<Media> mediaList, MediaTypes mediaTypes){
        mediaList.clear();
        for (Media media: medias){
            if (mediaTypes.equals(media)){
                mediaList.add(media);
            }
        }
    }

    public Media getMediaByID(int id){
        for (Media media: medias){
            if (media.getId() == id){
                return media;
            }
        }
        return null;
    }
}
