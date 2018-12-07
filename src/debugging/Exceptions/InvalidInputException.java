package debugging.Exceptions;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String msg){
        super(msg);
    }

    @Override
    public String toString(){
        return getMessage();
    }
}
