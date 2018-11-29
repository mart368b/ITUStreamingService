package ui.cards;

import user.PictureHandler;
import user.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ProfileCard extends JButton {

    private BufferedImage image;
    private final int SIZE = 128;

    public ProfileCard(Profile profile){
        super();

        this.image = profile.getImage();
        setMaximumSize(new Dimension(SIZE, SIZE));
    }

    public ProfileCard(){
        super();
        this.image = PictureHandler.getInstance().getPicture("create");
        setMaximumSize(new Dimension(SIZE, SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(resize(image, SIZE, SIZE), 0, 0, this); // see javadoc for more info on the parameters
    }

    private BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}
