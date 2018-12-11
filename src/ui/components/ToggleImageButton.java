package ui.components;

import maincomponents.ImageHandler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class ToggleImageButton extends ImageButton {
    private BufferedImage img1, img2;
    private boolean active = false;

    public ToggleImageButton(String img1, String img2){
        super(img1);
        super.recalculateAspectRation();
        ImageHandler handler = ImageHandler.getInstance();
        this.img1 = handler.getImage(img1);
        this.img2 = handler.getImage(img2);
        setPrefferedWidth(this.img1.getWidth());

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggle();
            }
        });
    }

    public void toggle(){
        active = !active;
        if (active){
            img = img2;
        }else {
            img = img1;
        }
    }

    public void setInvsible(){
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    public boolean isActive(){
        return active;
    }
}
