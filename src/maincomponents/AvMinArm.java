package maincomponents;

import debugging.LogTypes;
import debugging.Logger;
import ui.Display;
import user.PictureHandler;
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
    public static BufferedImage logoImage;

    public AvMinArm(){}

    public static void main( String[] args){
        PictureHandler.getInstance().init();
        UserHandler.getInstance().init();
        user = UserHandler.getInstance().getUser("Kaare", "1234");
        AvMinArm.loadLogo();
        Display.getDisplay();
    }

    public static void loadLogo(){
        File f = new File("res/button-images/logo.png");
        try {
            logoImage = ImageIO.read(f);
        } catch (IOException e) {
            Logger.log("No file was found! ", LogTypes.FATALERROR);
            e.printStackTrace();
        }
    }
}
