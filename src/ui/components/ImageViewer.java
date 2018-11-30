package ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageViewer extends JPanel {

    protected BufferedImage img;
    protected double widthAspect, heightAspect;

    public ImageViewer(){
        super();
    }

    public ImageViewer(LayoutManager layoutManager){
        super(layoutManager);
    }

    public ImageViewer(LayoutManager layoutManager, BufferedImage image){
        super(layoutManager);
        this.img = image;
        recalculateAspectRation();
    }

    public ImageViewer(BufferedImage image){
        super();
        this.img = image;
        recalculateAspectRation();
    }

    public void setImage(BufferedImage img){
        this.img = img;
        repaint();
    }

    private void recalculateAspectRation(){
        widthAspect = img.getHeight() / (img.getWidth() + 0.);
        heightAspect = img.getWidth() / (img.getHeight() + 0.);
    }

    public void setPrefferedWidth(int width){
        Dimension d = new Dimension(width, (int)(width*widthAspect));
        setPreferredSize(d);
    }

    public void setPrefferedHeight(int height){
        Dimension d = new Dimension((int)(height*heightAspect), height);
        setPreferredSize(d);
    }

    protected void clearGraphics(Graphics g){
        super.paintComponent(g);
    }

    @Override
    protected void paintComponent(Graphics g) {
        clearGraphics(g);
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
