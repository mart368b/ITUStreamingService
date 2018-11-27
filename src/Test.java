import medias.Media;
import reader.MediaHandler;
import user.PictureHandler;
import user.Profile;
import user.User;
import user.UserHandler;

public class Test {
    public static void main( String[] args){
        PictureHandler.getInstance();
        User user = new User("DeSeje","1234");
        Media media = MediaHandler.getInstance().getMedia("The Godfather");
        Media media2 = MediaHandler.getInstance().getMedia("Schindler's List");
        Profile profile1 = new Profile("Mor", 48);
        Profile profile2 = new Profile("Far", 52);

        user.signUpProfile(profile1);
        user.signUpProfile(profile2);
        profile1.addFavorite(media);
        profile1.addFavorite(media2);
        profile2.addFavorite(media);

        UserHandler.getInstance().signUpUser(user);
        System.exit(0);
    }
}
