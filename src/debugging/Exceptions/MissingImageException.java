package debugging.Exceptions;

import java.io.File;

public class MissingImageException extends MissingResourceException {

    public MissingImageException(File f) {
        super(f, "image");
    }
}
