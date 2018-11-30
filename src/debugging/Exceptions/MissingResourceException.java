package debugging.Exceptions;

import debugging.LogTypes;
import debugging.Logger;
import sun.rmi.runtime.Log;

import java.io.File;

public abstract class MissingResourceException extends Exception {

    private StringBuilder errorMessage = new StringBuilder();
    public MissingResourceException(File f, String fileType){
        super("Failed to find file: " + f.getAbsolutePath());
        errorMessage.append("Failed to find ");
        errorMessage.append(fileType);
        errorMessage.append(" at ");
        errorMessage.append(f.getAbsolutePath());
    }

    public void logError(LogTypes logType){
        Logger.log(errorMessage.toString(), logType);
    }
}
