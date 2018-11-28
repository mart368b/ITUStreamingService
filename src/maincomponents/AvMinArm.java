package maincomponents;

import ui.Display;
import user.PictureHandler;
import user.Profile;
import user.User;
import user.UserHandler;

public class AvMinArm {

    public static User user;
    public static Profile profile;

    public AvMinArm(){}

    public static void main( String[] args){
        PictureHandler.getInstance().init();
        UserHandler.getInstance().init();
        user = UserHandler.getInstance().getUser("Kaare", "1234");
        Display.getDisplay();
    }
}
