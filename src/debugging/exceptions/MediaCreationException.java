package debugging.exceptions;

public class MediaCreationException extends Exception {
    public MediaCreationException( ExceptionInInitializerError e){
        super("Error while creating media", e.getCause());
    }
}
