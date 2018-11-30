package debugging.Exceptions;

import debugging.LogTypes;
import debugging.Logger;

import java.io.IOException;

public class ResourceLoadingException extends Exception {

    private StringBuilder errorMessage = new StringBuilder();
    public ResourceLoadingException(IOException e){
        super(e.getMessage(), e.getCause());
        errorMessage.append(e.getMessage());
        errorMessage.append("\n");
        errorMessage.append(e.getCause());
    }

    public void logError(LogTypes logType){
        Logger.log(errorMessage.toString(), logType);
    }
}
