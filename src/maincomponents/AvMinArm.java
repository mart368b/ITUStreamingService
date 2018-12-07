package maincomponents;

import ui.Display;
import ui.panels.Page;
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
        Display.setPage(Page.LOGINPAGE);
    }
}
