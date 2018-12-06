package ui.cards;

import maincomponents.AvMinArm;
import maincomponents.ImageHandler;
import medias.Media;
import ui.Display;
import ui.StyleArchive;
import ui.components.ImageButton;
import ui.components.ToggleImageButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MediaPreviewCard extends ImageButton {
    private Media media;
    private ToggleImageButton button;

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

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        button = new ToggleImageButton("add", "remove");
        button.setPrefferedWidth(20);
        button.setBackground(StyleArchive.COLOR_BACKGROUND);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (button.isActive()){
                    AvMinArm.profile.removeFavorite(media);
                }else {
                    AvMinArm.profile.addFavorite(media);
                }
            }
        });
        c.anchor = GridBagConstraints.NORTHEAST;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        add(button, c);
    }

    public void setActive(boolean active) {
        if (button.isActive() != active){
            button.toggle();
        }
    }
}
