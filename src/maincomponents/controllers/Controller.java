package maincomponents.controllers;

public abstract class Controller {

    public static boolean isNumber(String s){
        try {
            Integer.parseInt(s);
            return true;
        }catch (Exception e){return false;}
    }

    public static boolean isDouble(String s){
        try {
            Double.parseDouble(s);
            return true;
        }catch (Exception e){return false;}
    }
}
