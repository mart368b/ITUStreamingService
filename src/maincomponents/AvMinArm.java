package maincomponents;

import ui.Display;
import user.Profile;
import user.User;
import user.UserHandler;

public class AvMinArm {

    public static User user;
    public static Profile profile;

    public AvMinArm(){}

    public static void main( String[] args){
        ImageHandler.getInstance().init();
        UserHandler.getInstance().init();
        Display display = Display.getInstance();;
        display.setPage(2);
    }
}
