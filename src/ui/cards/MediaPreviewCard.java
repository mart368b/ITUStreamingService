package ui.cards;

import medias.Media;
import ui.Display;
import ui.components.ImageButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MediaPreviewCard extends ImageButton {
    private Media media;

    public MediaPreviewCard(Media media){
        super(media.getImage());
        setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
        this.media = media;

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Display.getInstance().displayMedia(media);
            }
        });
    }
}
