package user;

import debugging.Logger;
import medias.Media;
import reader.MediaHandler;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Profile {

    private String name;
    private String picture;
    private int age;
    private List<Media> favorites;

    /**
     * Create profile with known age
     * @param name The name of the user
     * @param age The age of the profile
     */
    public Profile(String name, int age){
        this.name = name;
        this.age = age;
        this.picture = "default-orange";
        this.favorites = new ArrayList<Media>();
    }

    /**
     * Create profile with known age and profile picture
     * @param name The name of the user
     * @param age The age of the profile
     * @param picture The name of the profilepicture
     * @param favorites The stringarray which contains all the names of the favorite media
     */
    public Profile(String name, int age, String picture, String[] favorites){
        this.name = name;
        this.age = age;
        this.picture = picture;
        this.favorites = new ArrayList<Media>();
        for(String s : favorites){
            Media media = MediaHandler.getInstance().getMedia(s);
            if(media != null) this.favorites.add(media);
            else Logger.log("Found title in favoriteslist of username: " + name + ", that does not exist in Media!");
        }
    }

    /**
     * @return returns the name of the profile
     */
    public String getName(){
        return name;
    }

    /**
     * @return returns the age of the profile
     */
    public int getAge(){
        return age;
    }

    /**
     * @param image The image which is wished as profile picture
     */
    public void setPicture(String image){
        picture = image;
    }

    /**
     * Set profile picture back to default
     */
    public void resetPicture(){
        picture = "default-orange";
    }

    /**
     * @return returns the list of favorites
     */
    public List<Media> getFavorites(){
        return favorites;
    }

    /**
     * @param media The media which is wished to check
     * @return true if media is in favorites
     */
    public boolean isFavorite(Media media){
        return favorites.contains(media);
    }

    /**
     * Add media to the profiles favorites
     * @param media The media which whishes to be added
     */
    public void addFavorite(Media media){
        favorites.add(media);
    }

    /**
     * @return returns a BufferedImage (The profile picture)
     */
    public BufferedImage getImage(){
        return PictureHandler.getInstance().getPicture(picture);
    }

    /**
     * @return return the profile picture name
     */
    public String getProfilePicture(){
        return picture;
    }
}
