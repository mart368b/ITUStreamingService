package maincomponents;

import maincomponents.ImageHandler;
import reader.MediaHandler;
import ui.Display;
import ui.pages.PageHandler;
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
        MediaHandler.getInstance();
        Display.setPage(PageHandler.LOGINPAGE);
    }
}
