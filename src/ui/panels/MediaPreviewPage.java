package ui.panels;

import medias.Categories;
import medias.Media;
import ui.Display;
import ui.components.ImageViewer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class MediaPreviewPage extends Page {

    public static Font titleFont = new Font("Arial", Font.PLAIN, 40);
    private ImageViewer imagePanel;
    private Label title;
    private JPanel genreContainer;
    private PreviewPage previewPanel;

    protected MediaPreviewPage(){
        super(new BorderLayout());

        JPanel titlePanel = getTitlePanel();
        add(titlePanel, BorderLayout.PAGE_START);

        JPanel body = getBody();
        add(body, BorderLayout.CENTER);
    }

    private JPanel getBody(){
        JPanel body = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel bioContianer = getBioContainer();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.6;
        c.weighty = 1;
        body.add(bioContianer, c);

        JPanel imageContainer = getImageContainer();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.1;
        body.add(imageContainer, c);
        return body;
    }

    private JPanel getBioContainer(){
        JPanel panel = new JPanel( new BorderLayout());

        panel.add(getBioBody(panel), BorderLayout.CENTER);

        return panel;
    }

    private JPanel getBioBody(JPanel con) {
        JPanel panel = new JPanel();
        BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);

        genreContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(genreContainer);

        return panel;
    }

    private JPanel getImageContainer(){
        JPanel panel = new JPanel();
        imagePanel = new ImageViewer(null);
        Dimension d = new Dimension(280, 418);
        imagePanel.setPreferredSize(d);
        imagePanel.setMinimumSize(d);
        panel.add(imagePanel);
        return panel;
    }

    private JPanel getTitlePanel(){
        JPanel titleWrapper = new JPanel();
        Border underlineBorder = BorderFactory.createMatteBorder(0,0,4,0, Color.LIGHT_GRAY);
        titleWrapper.setBorder(underlineBorder);

        title = new Label("The God Father");
        title.setForeground(Color.BLACK);
        title.setFont(titleFont);
        titleWrapper.add(title);
        return titleWrapper;
    }

    public void setGenre(Categories[] genres ){
        genreContainer.removeAll();
        for (Categories genre: genres){
            genreContainer.add(genre.getGenreCard());
        }
    }

    public void setMedia(Media media) {
        title.setText(media.getTitle());
        setGenre(media.getGenres());
    }
}
