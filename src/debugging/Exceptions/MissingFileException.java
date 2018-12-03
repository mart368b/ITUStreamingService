package debugging.Exceptions;

import java.io.File;

public class MissingFileException extends MissingResourceException{

    public MissingFileException(File f, String fileType) {
        super(f, fileType + " file");
    }
}
