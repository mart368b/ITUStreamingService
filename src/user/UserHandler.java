package user;

import debugging.LogTypes;
import debugging.Logger;
import medias.Media;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
                this.save();
            }
        }));
    }

    public void init(){
        // Creates new user list
        users = new ArrayList<User>();
        try {
            // finds file
            File f = new File(PATH);
            // creates a reader to read file
            BufferedReader reader = new BufferedReader(new FileReader(f));
            // gets the first line
            String line = reader.readLine();
            // while loop, which will read lines of file until there are no more lines
            while(line != null){
                // gets the arguments of the line! Example of line [Bob;1234;true;Mor,48,default,Bob The Builder~Game Of Thrones%Far,52,default,;]
                String[] args = line.split(";");
                String username = args[0];
                String password = args[1];
                boolean admin = args[2].equals("true");

                if(!(args.length > 3)){
                    users.add(new User(username, password, admin));
                    line = reader.readLine();
                    continue;
                }

                String[] profilesdata = args[3].split("%");
                ArrayList<Profile> profiles = new ArrayList<Profile>();
                // goes through the data of each profile! Example of data [Mor,48,default,Bob The Builder~Game Of Thrones%Far,52,default,;]
                for(String p : profilesdata){
                    String[] info = p.split(",");
                    String name = info[0];
                    int age = Integer.parseInt(info[1]);
                    String profilepic = info[2];
                    String[] favs = info[3].split("~");
                    profiles.add(new Profile(name, age, profilepic, favs));
                }

                users.add(new User(username, password, admin, profiles));
                line = reader.readLine();
            }
        }catch (Exception e){
            Logger.log("Could not find the users.txt file!", LogTypes.FATALERROR);
            e.printStackTrace();
        }
    }

    public void save(){
        // save file in correct format
        try{
            File f = new File(PATH);
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
                    profiledata.append(profile.getProfilePicture());
                    profiledata.append(",");
                    Iterator<Media> favIter =  profile.getFavorites().iterator();
                    while(favIter.hasNext()){
                        Media fav = favIter.next();
                        StringBuilder favoritesdata = new StringBuilder();
                        favoritesdata.append(fav.getTitle());
                        // makes sure no ~ at the end
                        if(favIter.hasNext()) favoritesdata.append("~");
                        profiledata.append(favoritesdata);
                    }
                    sb.append(profiledata);
                    // makes sure no % at the end
                    if(profileIter.hasNext()) sb.append("%");
                }
                sb.append(";\n");
                writer.write(sb.toString());
            }
            writer.close();
        }catch (Exception e){
            Logger.log("Could not find the users.txt file!", LogTypes.FATALERROR);
            e.printStackTrace();
        }
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
