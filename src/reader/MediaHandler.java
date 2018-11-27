package reader;

import debugging.LogTypes;
import debugging.Logger;
import debugging.exceptions.MediaCreationException;
import medias.Media;
import medias.MediaTypes;

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

    /**
     * This functions reade a given media csv file and convert it to a list of elements of the specified MediaType
     * @param path : csv file path
     * @param mediaType : MediaType to convert rows in csv file to
     */
    private void loadMedia( String path, MediaTypes mediaType ){
        try {
            // Read file
            CSVReader reader = new CSVReader(path, mediaType.getColumnCount());
            Iterator<String[]> ite = reader.getIterator();
            // Iterate over all rows in the csv file
            ArrayList<MediaCreationException> errors = new ArrayList<>();
            int mediaCount = 0;
            while (ite.hasNext()){
                // Create Media of MediaType using MediaFactory
                Media media = null;
                try {
                    media = Media.getMediaByMediaType(mediaType, ite.next());
                    mediaCount++;
                } catch (MediaCreationException e) {
                    errors.add(e);
                }
                if ( media != null ){
                    medias.add(media);
                }
            }
            if (errors.size() > 0){
                StringBuilder builder = new StringBuilder();
                builder.append("Errors during creation of " + mediaType.getName());
                for (MediaCreationException e: errors){
                    builder.append("\n");
                    builder.append(e.getMessage().toString());
                }
                Logger.log(builder.toString(), LogTypes.SOFTERROR);
            }
            Logger.log("Created " + mediaCount + " " + mediaType.getName());
        } catch ( FileNotFoundException e){
            // This is reached if there is a typo in the csv file path for the media, or if the file does not exists
            Logger.log("Failed to find media at: " + path, LogTypes.SOFTERROR);
        }
    }

}
