package ui;

import medias.Media;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MediaPreviewCard extends JButton {
    private Media media;

    public MediaPreviewCard(Media media){
        super();
        this.media = media;

        BufferedImage image = media.getImage();
        setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(media.getImage(), 0, 0, this); // see javadoc for more info on the parameters
    }

}
