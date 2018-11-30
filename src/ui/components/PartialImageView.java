package ui.components;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PartialImageView extends ImageViewer {

    private double showed;

    public PartialImageView(LayoutManager layoutManager, BufferedImage image, double showed) {
        super(layoutManager, image);
        this.showed = showed;
    }

    public PartialImageView(BufferedImage image, double showed){
        super(image);
        this.showed = showed;
    }

    public void setShowed(double showed){
        this.showed = showed;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.clearGraphics(g);
        if (img != null){
            int newWidth = (int) (getWidth()*showed);
            BufferedImage cropedImage = img.getSubimage(0,0, (int) (img.getWidth()*showed), img.getHeight());
            Image scaledImage = cropedImage.getScaledInstance(newWidth, getHeight(), BufferedImage.SCALE_SMOOTH);
            g.drawImage(cropedImage, 0, 0, newWidth, getHeight(), null);
        }
    }


}
