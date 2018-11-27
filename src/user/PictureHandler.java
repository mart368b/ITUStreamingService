package user;

import debugging.LogTypes;
import debugging.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

public class PictureHandler {
    private static PictureHandler instance = new PictureHandler();
    public static PictureHandler getInstance() {
        return instance;
    }

    private HashMap<String, BufferedImage> images;
    private String[] types = new String[]{"default", "mummy", "pig", "space"};
    private String[] names = new String[]{"cyan", "green", "grey", "orange", "red"};

    private PictureHandler(){}

    public void init(){
        // creates fresh hashmap
        images = new HashMap<String, BufferedImage>();
        try {
            // double for-loop to initialze every profilepicture
            for(String type : types){
                for(String name : names){
                    String result = type + "-" + name;
                    File f = new File("res/user-images/" + result + ".jpg");
                    images.put(result, ImageIO.read(f));
                }
            }
        }catch(Exception e){
            Logger.log("No file was found! ", LogTypes.FATALERROR);
            e.printStackTrace();
        }
    }

    /**
     * @param name The name of the profile picture
     * @return returns either the profilepicture in form of BufferedImage or Null
     */
    public BufferedImage getPicture(String name){
        if(images.containsKey(name)){
            return images.get(name);
        }else{
            Logger.log("Could not find picture named: " + name, LogTypes.SOFTERROR);
            return null;
        }
    }
}
