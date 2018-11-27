package maincomponents;

import ui.Display;
import user.PictureHandler;
import user.User;
import user.UserHandler;

public class AvMinArm {

    private static User user;

    public AvMinArm(){}

    public static void main( String[] args){
        PictureHandler.getInstance().init();
        UserHandler.getInstance().init();
        Display.getDisplay();
    }

    public static void setUser(User u){
        user = u;
    }
}
