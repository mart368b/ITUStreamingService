package maincomponents;

import debugging.LogTypes;
import debugging.Logger;
import ui.Display;
import user.Profile;
import user.User;
import user.UserHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AvMinArm {

    public static User user;
    public static Profile profile;

    public AvMinArm(){}

    public static void main( String[] args){
        PictureHandler.getInstance().init();
        UserHandler.getInstance().init();
        user = UserHandler.getInstance().getUser("Kaare", "1234");
        Display display = Display.getInstance();;
        display.setPage(2);
    }
}
