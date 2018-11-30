package ui.cards;

import maincomponents.ImageHandler;
import ui.components.ImageButton;
import user.Profile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ProfileCard extends ImageButton {

    private int SIZE = 128;

    public ProfileCard(Profile profile){
        super();
        BufferedImage image = ImageHandler.getInstance().getImage(profile.getProfilePictureName());
        setPicture(image, SIZE);
    }

    public ProfileCard(){
        super();
        BufferedImage image = ImageHandler.getInstance().getImage("create");
        setPicture(image, SIZE);
    }

    public ProfileCard(String ImageName, int size){
        super();
        BufferedImage image = ImageHandler.getInstance().getImage(ImageName);
        setPicture(ImageName, size);
    }

    public void setPicture(String imageName, int size){
        setPicture(ImageHandler.getInstance().getImage(imageName), size);
    }

    public void setPicture(BufferedImage image, int size){
        SIZE = size;
        setMaximumSize(new Dimension(SIZE, SIZE));
        initalizeButton(image);
        repaint();
    }

}
