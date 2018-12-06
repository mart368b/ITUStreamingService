package user;

import debugging.Exceptions.MissingFileException;
import debugging.Exceptions.ResourceLoadingException;
import debugging.LogTypes;
import debugging.Logger;
import maincomponents.AvMinArm;
import medias.Media;
import reader.CSVReader;

import java.io.*;
import java.util.*;

public class UserHandler {
    private final String PATH = "res/users.txt";
    private static UserHandler instance = new UserHandler();
    public static UserHandler getInstance() {
        return instance;
    }

    private List<User> users;

    private UserHandler() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (instance != null){
                Logger.log("Saving user data");
                this.save();
            }
        }));
    }

    public void init(){
        // Creates new user list
        users = new ArrayList<User>();
        try {
            CSVReader reader = new CSVReader(PATH, 4);
            Iterator<String[]> ite = reader.getIterator();
            while(ite.hasNext()){
                // gets the arguments of the line! Example of line [Bob;1234;true;Mor,48,default,Bob The Builder~Game Of Thrones%Far,52,default,;]
                String[] row = ite.next();
                String username = row[0];
                String password = row[1];
                boolean admin = row[2].equals("true");

                if(!(row.length > 3)){
                    users.add(new User(username, password, admin));
                    continue;
                }

                String[] profilesdata = row[3].split("%");
                ArrayList<Profile> profiles = new ArrayList<Profile>();
                // goes through the data of each profile! Example of data [Mor,48,default,Bob The Builder~Game Of Thrones%Far,52,default,;]
                for(String p : profilesdata){
                    String[] info = p.split(",");
                    String name = info[0];
                    int age = Integer.parseInt(info[1]);
                    String profilepic = info[2];
                    if(!(info.length > 3)){
                        profiles.add(new Profile(name, age, profilepic));
                        continue;
                    }
                    String[] favs = info[3].split("~");
                    Map<Integer, Map<Integer, Map<Integer, Integer>>> watchedSeries;
                    if (info.length < 5 || info[4].length() == 0){
                        watchedSeries = new HashMap<>();
                    }else {
                        watchedSeries = loadWatchedSeries(info[4]);
                    }
                    Map<Integer, Integer> watchedMovies;
                    if (info.length < 6 || info[5].length() == 0){
                        watchedMovies = new HashMap<>();
                    }else {
                        watchedMovies = loadWatchedMovies(info[5]);
                    }
                    profiles.add(new Profile(name, age, profilepic, favs, watchedSeries, watchedMovies));
                }

                users.add(new User(username, password, admin, profiles));
            }
        }catch (MissingFileException e){
            e.logError(LogTypes.SOFTERROR);
            File f = new File(PATH);
            try {
                f.createNewFile();
            } catch (IOException e1) {
                Logger.log("Failed to create file", LogTypes.FATALERROR);
                e1.printStackTrace();
            }
        } catch (ResourceLoadingException e){
            e.logError(LogTypes.FATALERROR);
        }
    }

    public void save(){
        // save file in correct format
        try{
            File f = new File(PATH);
            if (!f.exists()){
                f.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            Iterator<User> userIter = users.iterator();
            while(userIter.hasNext()){
                User user = userIter.next();
                StringBuilder sb = new StringBuilder();
                sb.append(user.getUsername());
                sb.append(";");
                sb.append(user.getPassword());
                sb.append(";");
                sb.append(user.isAdmin());
                sb.append(";");
                Iterator<Profile> profileIter = user.getProfiles().iterator();
                while(profileIter.hasNext()){
                    Profile profile = profileIter.next();
                    StringBuilder profiledata = new StringBuilder();
                    profiledata.append(profile.getName());
                    profiledata.append(",");
                    profiledata.append(profile.getAge());
                    profiledata.append(",");
                    profiledata.append(profile.getProfilePictureName());
                    profiledata.append(",");
                    Iterator<Media> favIter =  profile.getFavorites().iterator();
                    while(favIter.hasNext()){
                        Media fav = favIter.next();
                        StringBuilder favoritesdata = new StringBuilder();
                        favoritesdata.append(fav.getId());
                        // makes sure no ~ at the end
                        if(favIter.hasNext()) favoritesdata.append("~");
                        profiledata.append(favoritesdata);
                    }
                    profiledata.append(",");
                    saveWatchedSeries(profiledata, profile);
                    profiledata.append(",");
                    saveWatchedMovies(profiledata, profile);
                    sb.append(profiledata);
                    // makes sure no % at the end
                    if(profileIter.hasNext()) sb.append("%");
                }
                sb.append(";\n");
                writer.write(sb.toString());
            }
            writer.close();
        }catch (Exception e){
            Logger.log("Could not save users", LogTypes.FATALERROR);
            e.printStackTrace();
        }
    }

    private void saveWatchedMovies(StringBuilder profiledata, Profile profile) {
        Map<Integer, Integer> watchedMovies = profile.getWatchedMovies();
        Iterator<Integer> ite = watchedMovies.keySet().iterator();
        while (ite.hasNext()){
            int movieID = ite.next();
            profiledata.append(movieID);
            profiledata.append(":");
            profiledata.append(watchedMovies.get(movieID));
            if (ite.hasNext()){
                profiledata.append("&");
            }
        }
    }

    private void saveWatchedSeries(StringBuilder profileData, Profile profile){
        //Series
        Map<Integer, Map<Integer, Map<Integer, Integer>>> wathedSeries = profile.getWatchedSeries();
        for (int i0: wathedSeries.keySet()) {
            Map<Integer, Map<Integer, Integer>> wathedSeasons = wathedSeries.get(i0);
            profileData.append(i0);
            profileData.append("&");
            // Seasons
            for (int i1: wathedSeasons.keySet()) {
                Map<Integer, Integer> watchedEpisodes = wathedSeasons.get(i1);
                profileData.append(i1);
                // Episodes
                for (int i2: watchedEpisodes.keySet()){
                    profileData.append("_");
                    int duration = watchedEpisodes.get(i2);
                    profileData.append(i2);
                    profileData.append(":");
                    // Duration
                    profileData.append(duration);
                }
            }
        }
    }

    private Map<Integer, Map<Integer, Map<Integer, Integer>>> loadWatchedSeries(String content){
        Map<Integer, Map<Integer, Map<Integer, Integer>>> wathedSeries = new HashMap<>();
        String[] m0 = content.split("$");
        for (int i0 = 0; i0 < m0.length; i0++) {
            String s0 = m0[i0];
            String[] m1 = s0.split("&");
            int id0 = Integer.parseInt(m1[0]);
            Map<Integer, Map<Integer, Integer>> hm1 = new HashMap<>();
            // Seasons
            for (int i1 = 1; i1 < m1.length; i1++) {
                String s1 = m1[i1];
                String[] m2 = s1.split("_");
                int id1 = Integer.parseInt(m2[0]);
                Map<Integer, Integer> hm2 = new HashMap<>();
                // Episodes
                for (int i2 = 1; i2 < m2.length; i2++) {
                    String s2 = m2[i2];
                    String[] m3 = s2.split(":");
                    int id3 = Integer.parseInt(m3[0]);
                    // Duration
                    int duration = Integer.parseInt(m3[1]);
                    hm2.put(id3, duration);
                }
                hm1.put(id1, hm2);
            }
            wathedSeries.put(id0, hm1);
        }
        return wathedSeries;
    }

    private HashMap<Integer, Integer> loadWatchedMovies(String content){
        HashMap<Integer, Integer> watchedMovies = new HashMap<>();
        String[] m0 = content.split("&");
        for(String s: m0){
            String[] m1 = s.split(":");
            int movieID = Integer.parseInt(m1[0]);
            int timeStamp = Integer.parseInt(m1[1]);
            watchedMovies.put(movieID, timeStamp);
        }
        return watchedMovies;
    }

    /**
     * Sign up a new user
     * @param name The name of the new user
     * @param password The password of the new user
     */
    public void signUpUser(String name, String password){
        users.add(new User(name, password));
    }

    /**
     * Sign up a new user
     * @param user The already known user
     */
    public void signUpUser(User user){
        users.add(user);
    }

    /**
     * Remove user
     * @param user The user which is to be removed
     */
    public void removeUser(User user){
        users.remove(user);
    }

    /**
     * Checks if a user with this username already exists
     * @param username The username to be checked
     * @return returns true if user exists, else returns false
     */
    public boolean hasUser(String username){
        for(User user : users){
            if(user.getUsername().toLowerCase().equals(username.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there is a user with known username and password
     * @param username The username of the user
     * @param password The password of the user
     * @return returns a user if found otherwise null
     */
    public User getUser(String username, String password){
        for(User user : users){
            if(user.getUsername().toLowerCase().equals(username.toLowerCase())
                && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }
}
