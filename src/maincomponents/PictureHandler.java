package maincomponents;

import debugging.LogTypes;
import debugging.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class PictureHandler {
    private static PictureHandler instance = new PictureHandler();
    public static PictureHandler getInstance() {
        return instance;
    }

    private HashMap<String, BufferedImage> images;
    public static String[] types = new String[]{"default", "mummy", "pig", "space"};
    public static String[] colors = new String[]{"cyan", "green", "grey", "orange", "red"};

    private PictureHandler(){}

    public void init(){
        // creates fresh hashmap
        images = new HashMap<String, BufferedImage>();
        try {
            // double for-loop to initialze every profilepicture
            for(String type : types){
                for(String name : colors){
                    String result = type + "-" + name;
                    File f = new File("res/user-images/" + result + ".jpg");
                    images.put(result, ImageIO.read(f));
                }
            }
            File f = new File("res/button-images/create.jpg");
            images.put("create", ImageIO.read(f));

            f = new File("res/button-images/star.png");
            images.put("star", ImageIO.read(f));

            f = new File("res/button-images/logo.png");
            images.put("logo", ImageIO.read(f));

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

    /**
     * @param array The ArrayList which will be filled with all images
     */
    public void getPictures(ArrayList<BufferedImage> array){
        for(BufferedImage image : images.values()){
            array.add(image);
        }
    }
}
