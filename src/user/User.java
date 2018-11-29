package user;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String username;
    private String password;
    private ArrayList<Profile> profiles;
    private boolean admin;

    /**
     * Create user with known username and password
     * @param username The User's username
     * @param password The User's password
     */
    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.profiles = new ArrayList<Profile>();
        this.admin = false;
    }

    /**
     * Create a user with known username, password and access
     * @param username The User's username
     * @param password The User's password
     * @param admin The User's permission
     */
    public User(String username, String password, boolean admin){
        this.username = username;
        this.password = password;
        this.profiles = new ArrayList<Profile>();
        this.admin = admin;
    }

    /**
     * Create a user with known username, password and access
     * @param username The User's username
     * @param password The User's password
     * @param admin The User's permission
     * @param profiles The User's profiles
     */
    public User(String username, String password, boolean admin, ArrayList<Profile> profiles){
        this.username = username;
        this.password = password;
        this.profiles = profiles;
        this.admin = admin;
    }

    /**
     * @return return the profiles to this User
     */
    public ArrayList<Profile> getProfiles(){
        return profiles;
    }

    /**
     * @param name The name of the profile
     * @return return a profile or Null
     */
    public Profile getProfile(String name){
        for(Profile p : profiles){
            if(p.getName().equals(name)) return p;
        }
        return null;
    }

    /**
     * @param name The name of the profile
     * @param age The age of the profile
     */
    public void signUpProfile(String name, int age){
        profiles.add(new Profile(name, age));
    }

    /**
     * @param profile The already known profile
     */
    public void signUpProfile(Profile profile){
        profiles.add(profile);
    }

    /**
     * @return returns true if user is admin
     */
    public boolean isAdmin(){
        return admin;
    }

    /**
     * Make a user Admin
     */
    public void makeAdmin(){
        this.admin = true;
    }

    /**
     * @return get username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return get password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Checks if there are any profiles existant with this username
     * @param username Username to be tested
     * @return returns true if profile with username already exists, else false.
     */
    public boolean hasProfile(String username){
        for(Profile profile : profiles){
            if(profile.getName().toLowerCase().equals(username.toLowerCase())){
                return true;
            }
        }
        return false;
    }
}
