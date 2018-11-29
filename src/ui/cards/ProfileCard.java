package ui.cards;

import ui.components.ImageButton;
import user.PictureHandler;
import user.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ProfileCard extends JButton {

    private int SIZE = 128;
    private BufferedImage image;

    public ProfileCard(Profile profile){
        super();
        setPicture(PictureHandler.getInstance().getPicture(profile.getProfilePicture()), SIZE);
    }

    public ProfileCard(){
        super();
        setPicture(PictureHandler.getInstance().getPicture("create"), SIZE);
    }

    public ProfileCard(String ImageName, int size){
        super();
        image = PictureHandler.getInstance().getPicture(ImageName);
        setPicture(ImageName, size);
    }

    public void setPicture(String imageName, int size){
        setPicture(PictureHandler.getInstance().getPicture(imageName), size);
    }

    public void setPicture(BufferedImage image, int size){
        this.image = image;
        SIZE = size;
        setMaximumSize(new Dimension(SIZE, SIZE));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null){
            return;
        }
        Image img = image.getScaledInstance(SIZE, SIZE, BufferedImage.SCALE_SMOOTH);
        g.drawImage(img, 0, 0, SIZE, SIZE, this); // see javadoc for more info on the parameters
    }
}
