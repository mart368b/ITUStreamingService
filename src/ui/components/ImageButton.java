package ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageButton extends JButton {

    protected BufferedImage img;
    protected double widthAspect, heightAspect;

    public ImageButton (BufferedImage img, LayoutManager layoutManager){
        setLayout(layoutManager);
        initalizeButton(img);
    }

    public ImageButton( BufferedImage img){
        initalizeButton(img);
    }

    private void initalizeButton(BufferedImage img){
        this.img = img;

        setOpaque(false);
        setContentAreaFilled(false);
        if(img != null) {
            recalculateAspectRation();
        }
    }

    private void recalculateAspectRation(){
        widthAspect = img.getHeight() / (img.getWidth() + 0.);
        heightAspect = img.getWidth() / (img.getHeight() + 0.);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image scaledImage = img.getScaledInstance(getWidth(), getHeight(), BufferedImage.SCALE_SMOOTH);
        g.drawImage(scaledImage, 0, 0, getWidth(), getHeight(), null);
    }

    public void setPrefferedWidth(int width){
        Dimension d = new Dimension(width, (int)(width*widthAspect));
        setPreferredSize(d);
    }

    public void setPrefferedHeight(int height){
        Dimension d = new Dimension((int)(height*heightAspect), height);
        setPreferredSize(d);
    }

    public void setImgage( BufferedImage img ){
        this.img = img;
        recalculateAspectRation();
    }

}
