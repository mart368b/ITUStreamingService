package user;

import debugging.Logger;
import maincomponents.ImageHandler;
import medias.Media;
import medias.types.MediaTypes;
import reader.MediaHandler;

import java.awt.image.BufferedImage;
import java.util.*;

public class Profile {

    private String name;
    private String picture;
    private int age;
    private List<Media> favorites;
    private Map<Integer, Integer> watchedMovies;
    private Map<Integer, Map<Integer, Map<Integer, Integer>>> watchedSeries;

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
        this.watchedSeries = new HashMap<>();
        this.watchedMovies = new HashMap<>();
    }

    public Profile(String name, int age, String picture){
        this.name = name;
        this.age = age;
        this.picture = picture;
        this.favorites = new ArrayList<Media>();
        watchedMovies = new HashMap<>();
        watchedSeries = new HashMap<>();
    }

    /**
     * Create profile with known age and profile picture
     * @param name The name of the user
     * @param age The age of the profile
     * @param picture The name of the profilepicture
     * @param favorites The stringarray which contains all the names of the favorite media
     */
    public Profile(String name, int age, String picture, String[] favorites, Map<Integer, Map<Integer, Map<Integer, Integer>>> watchedSeries, Map<Integer, Integer> watchedMovies){
        this.name = name;
        this.age = age;
        this.picture = picture;
        this.favorites = new ArrayList<Media>();
        for(String s : favorites){
            if (s.length() == 0){
                continue;
            }
            int mediaID = Integer.parseInt(s);
            boolean isSeries = (mediaID & 1) == 1;
            Media media = MediaHandler.getMediaByID(mediaID >> 1, isSeries);
            if(media != null) this.favorites.add(media);
            else Logger.log("Found title in favoriteslist of username: " + name + ", that does not exist in Media!");
        }
        this.watchedSeries = watchedSeries;
        this.watchedMovies = watchedMovies;
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

    public Map<Integer, Map<Integer, Map<Integer, Integer>>> getWatchedSeries(){
        return watchedSeries;
    }

    public Map<Integer, Integer> getWatchedMovies(){
        return watchedMovies;
    }

    public void addWatchedMovie(int movieID, int duration){
        if (duration == 0){
            return;
        }
        watchedMovies.put(movieID, duration);
    }

    public int getMovieTimeStamp(int movieID){
        int timeStamp = 0;
        if(watchedMovies.containsKey(movieID)){
            timeStamp = watchedMovies.get(movieID);
        }
        return timeStamp;
    }

    public void addWatchedSeriesEpisode(int seriesID, int seasonID, int episodeID, int duration){
        Map<Integer, Map<Integer, Integer>> series= null;
        if (!watchedSeries.containsKey(seriesID)){
            if (duration == 0){
                return;
            }
            series = new HashMap<>();
            watchedSeries.put(seriesID, series);
        }else {
            series = watchedSeries.get(seriesID);
        }
        Map<Integer, Integer> season = null;
        if (!series.containsKey(seasonID)){
            if (duration == 0){
                return;
            }
            season = new HashMap<>();
            series.put(seasonID, season);
        }else{
            season = series.get(seasonID);
        }
        season.put(episodeID, duration);
    }

    public int getSeriesTimeStamp(int seriesID, int seasonID, int episodeID){
        Map<Integer, Map<Integer, Integer>> series= null;
        if (!watchedSeries.containsKey(seriesID)){
            return 0;
        }else {
            series = watchedSeries.get(seriesID);
        }
        Map<Integer, Integer> season = null;
        if (!series.containsKey(seasonID)){
            return 0;
        }else{
            season = series.get(seasonID);
        }
        if (!season.containsKey(episodeID)){
            return 0;
        }
        return season.get(episodeID);
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
        return ImageHandler.getInstance().getImage(picture);
    }

    /**
     * @return return the profile picture name
     */
    public String getProfilePictureName(){
        return picture;
    }

    /**
     * @param name The new username for this profile
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * @param age The new age for this profile
     */
    public void setAge(int age){
        this.age = age;
    }

    public void removeFavorite(Media media) {
        favorites.remove(media);
    }
}
