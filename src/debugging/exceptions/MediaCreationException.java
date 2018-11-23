package debugging.exceptions;

import medias.Media;

public class MediaCreationException extends Exception {
    public MediaCreationException (){
        super("Failed on creation of media");
    }
}
