package ui.panels;

import medias.types.GenreTypes;
import medias.Media;
import ui.Display;
import ui.components.ImageViewer;
import ui.components.PartialImageView;
import maincomponents.PictureHandler;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MediaPreviewPage extends Page {

    public static Font titleFont = new Font("Arial", Font.PLAIN, 40);
    private ImageViewer imagePanel;
    private Label title;
    private JPanel genreContainer, ratingContainer;
    private Label genreText, ratingText;
    private BufferedImage starImg;
    private Media currentMedia;

    protected MediaPreviewPage(){
        super(new BorderLayout());

        JPanel titlePanel = getTitlePanel();
        add(titlePanel, BorderLayout.PAGE_START);

        JPanel body = getBody();
        add(body, BorderLayout.CENTER);

        starImg = PictureHandler.getInstance().getPicture("star");
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

        panel.add(getBioBody(), BorderLayout.CENTER);

        return panel;
    }

    private JPanel getBioBody() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel informationPanel = new JPanel();
        BoxLayout layout = new BoxLayout(informationPanel, BoxLayout.Y_AXIS);
        informationPanel.setLayout(layout);

        genreContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genreText = new Label("Genre: ");
        genreText.setFont(GenreTypes.getFont());
        genreContainer.add(genreText);
        informationPanel.add(genreContainer);

        ratingContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ratingText = new Label("Genre: ");
        ratingText.setFont(GenreTypes.getFont());
        ratingContainer.add(ratingText);
        informationPanel.add(ratingContainer);

        panel.add(informationPanel, BorderLayout.PAGE_START);
        panel.add(getActionPanel(), BorderLayout.CENTER);
        return panel;
    }

    private JPanel getActionPanel() {
        JPanel panel = new JPanel(new GridBagLayout());

        JPanel wrapper = new JPanel(new FlowLayout());

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Display.getInstance().setPage(Page.PREVIEWPAGE);
            }
        });
        wrapper.add(backButton);

        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("PLay + " + currentMedia.toString());
            }
        });
        wrapper.add(playButton);

        panel.add(wrapper);
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

    public void setGenre(GenreTypes[] genres ){
        genreContainer.removeAll();
        genreContainer.add(genreText);
        for (GenreTypes genre: genres){
            genreContainer.add(genre.getGenreCard());
        }
    }

    public void setRating(double rating){
        ratingContainer.removeAll();
        ratingContainer.add(ratingText);
        int wholeStars = (int) Math.floor(rating/2);
        for (int i = 0; i < wholeStars; i++){
            ImageViewer star = new ImageViewer(starImg);
            star.setPrefferedWidth(40);
            ratingContainer.add(star);
        }
        double remaining = (rating - (wholeStars*2))/2;
        if (remaining > 0){
            PartialImageView partialStar = new PartialImageView(starImg, remaining);
            partialStar.setPrefferedWidth(40);
            ratingContainer.add(partialStar);
        }
    }

    public void setMedia(Media media) {
        title.setText(media.getTitle());
        setGenre(media.getGenres());
        setRating(media.getRating());
    }
}
