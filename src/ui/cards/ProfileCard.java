package ui.cards;

import maincomponents.PictureHandler;
import ui.components.ImageButton;
import user.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ProfileCard extends ImageButton {

    private int SIZE = 128;

    public ProfileCard(Profile profile){
        super();
        BufferedImage image = PictureHandler.getInstance().getPicture(profile.getProfilePicture());
        setPicture(image, SIZE);
    }

    public ProfileCard(){
        super();
        BufferedImage image = PictureHandler.getInstance().getPicture("create");
        setPicture(image, SIZE);
    }

    public ProfileCard(String ImageName, int size){
        super();
        BufferedImage image = PictureHandler.getInstance().getPicture(ImageName);
        setPicture(ImageName, size);
    }

    public void setPicture(String imageName, int size){
        setPicture(PictureHandler.getInstance().getPicture(imageName), size);
    }

    public void setPicture(BufferedImage image, int size){
        SIZE = size;
        setMaximumSize(new Dimension(SIZE, SIZE));
        initalizeButton(image);
        repaint();
    }

}
