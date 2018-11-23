package reader;

import medias.Media;
import medias.MediaTypes;
import medias.Movie;
import medias.Serie;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * class reasponsible for reading all medias
 */
public class MediaHandler{

    public static void main(String[]args){
        MediaHandler.getInstance();
    }

    // Make it a singleton since there is no reason to read all media multiple times
    private static MediaHandler instance = new MediaHandler();
    public static MediaHandler getInstance(){
        return instance;
    }

    // Container of all media present in the project
    private List<Media> medias;

    private MediaHandler(){
        this.medias = new ArrayList<Media>();
        // Get list of media to be loaded
        String[] loadedMedias = MediaTypes.getLoadedMediaTypes();
        for ( String media: loadedMedias ){
            // Get path to media csv file
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
            while (ite.hasNext()){
                // Create Media of MediaType using MediaFactory
                Media media = Media.getMediaByMediaType(mediaType, ite.next());
                if ( media != null ){
                    medias.add(media);
                }
            }
        } catch ( FileNotFoundException e){
            // This is reached if there is a typo in the csv file path for the media, or if the file does not exists
            System.out.println("Failed to find media at: " + path);
        }
    }

}
