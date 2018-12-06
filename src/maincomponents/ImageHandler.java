package maincomponents;

import debugging.Exceptions.MissingImageException;
import debugging.Exceptions.ResourceLoadingException;
import debugging.LogTypes;
import debugging.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ImageHandler {
    private static ImageHandler instance = new ImageHandler();
    public static ImageHandler getInstance() {
        return instance;
    }

    private HashMap<String, BufferedImage> images;
    public static String[] types = new String[]{"default", "pig", "space", "mummy"};
    public static String[] colors = new String[]{"green", "orange", "cyan", "red", "grey"};
    public static String[] agetypes = new String[]{"13", "15", "16", "18", "G", "M", "R", "PG", "NONE"};
    public static String[] buttons = new String[]{"create", "star", "logo", "add", "remove"};

    private ImageHandler(){}

    public void init(){
        // creates fresh hashmap
        images = new HashMap<String, BufferedImage>();
        loadStockPhoto();
        // double for-loop to initialze every profilepicture
        for(String type : types){
            for(String name : colors){
                String result = type + "-" + name;
                loadResource("user-images", result, "jpg");
            }
        }
        for (String ageTypeName: agetypes){
            loadResource("rating-images", ageTypeName, "png");
        }
        for (String buttonNames: buttons){
            loadResource("button-images", buttonNames, "png");
        }
        loadResource("user-images","canvas", "png");
    }

    private void loadStockPhoto() {
        BufferedImage img = new BufferedImage(255,255,BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < 255; x++){
            for (int y = 0; y < 255; y++){
                img.setRGB(x, y, (x<<16) | ((255 - (x/2) - (y/2))<<8) | y);
            }
        }
        images.put("stock", img);
    }

    public void loadResource(String folderName, String imageName, String extention){
        try {
            loadImage(folderName, imageName, extention);
        } catch (MissingImageException e) {
            e.logError(LogTypes.SOFTERROR);
            images.put(imageName, getImage("stock"));
        } catch (ResourceLoadingException e) {
            e.logError(LogTypes.SOFTERROR);
            images.put(imageName, getImage("stock"));
        } catch (Exception e){
            Logger.log(e.getMessage(), LogTypes.SOFTERROR);
            images.put(imageName, getImage("stock"));
        }
    }

    private void loadImage(String folderName, String imageName, String extention) throws MissingImageException, ResourceLoadingException {
        File f = new File("res/" + folderName + "/" + imageName + "." + extention);
        if (!f.exists()){
            throw new MissingImageException(f);
        }
        BufferedImage img;
        try {
            img = ImageIO.read(f);
        } catch (IOException e) {
            throw new ResourceLoadingException(e);
        }
        images.put(imageName, img);
    }

    public BufferedImage getImage(String name){
        if(images.containsKey(name)){
            return images.get(name);
        }else{
            Logger.log("Could not find image named: " + name, LogTypes.SOFTERROR);
            return images.get("stock");
        }
    }

    /**
     * @param array The ArrayList which will be filled with all images
     */
    public void getImages(ArrayList<BufferedImage> array){
        for(BufferedImage image : images.values()){
            array.add(image);
        }
    }
}
