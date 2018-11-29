package ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageViewer extends JPanel {

    private BufferedImage img;

    public ImageViewer(LayoutManager layoutManager, BufferedImage image){
        super(layoutManager);
        this.img = image;
    }

    public ImageViewer(BufferedImage image){
        super();
        this.img = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (img != null){
            Image scaledImage = img.getScaledInstance(getWidth(), getHeight(), BufferedImage.SCALE_SMOOTH);
            g.drawImage(scaledImage, 0, 0, getWidth(), getHeight(), null);
        }else{
            Color c = g.getColor();
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(c);
        }
    }

}
