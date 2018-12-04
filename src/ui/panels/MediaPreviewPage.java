package ui.panels;

import medias.Movie;
import medias.Serie;
import medias.SeriesEpisode;
import medias.types.GenreTypes;
import medias.Media;
import ui.Display;
import ui.components.ImageViewer;
import ui.components.PartialImageView;
import maincomponents.ImageHandler;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class MediaPreviewPage extends Page {

    public static Font titleFont = new Font("Arial", Font.PLAIN, 40);
    private ImageViewer imagePanel;
    private JLabel title;
    private JPanel genreContainer, ratingContainer, playContainer, playWrapper;
    private ImageViewer restrictionImg;
    private JLabel genreText, ratingText, yearText;
    private JButton backButton, backToSelectionButton;
    private PartialImageView partialStar;
    private ArrayList<ImageViewer> stars;
    private Media currentMedia;

    protected MediaPreviewPage(){
        super(new BorderLayout());
        initializeStars();

        JPanel titlePanel = getTitlePanel();
        add(titlePanel, BorderLayout.PAGE_START);

        JPanel body = getBody();
        add(body, BorderLayout.CENTER);

        backToSelectionButton = new JButton("back");
        backToSelectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSeriesPlayButton((Serie) currentMedia);
            }
        });
    }

    private void initializeStars(){
        stars = new ArrayList<>();
        BufferedImage starImg = ImageHandler.getInstance().getImage("star");
        for (int i = 0; i < 5; i++){
            ImageViewer star = new ImageViewer(starImg);
            star.setPrefferedWidth(40);
            stars.add(star);
        }
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
        genreText = new JLabel("Genre: ");
        genreText.setFont(GenreTypes.getFont());
        genreContainer.add(genreText);
        informationPanel.add(genreContainer);

        ratingContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ratingText = new JLabel("Rating: ");
        ratingText.setFont(GenreTypes.getFont());
        ratingContainer.add(ratingText);
        informationPanel.add(ratingContainer);

        JPanel restrictionContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel restrictionText = new JLabel("Restriction: ");
        restrictionText.setFont(GenreTypes.getFont());
        restrictionContainer.add(restrictionText);
        restrictionImg = new ImageViewer();
        restrictionImg.setPreferredSize(new Dimension(40,40));
        restrictionImg.setBorder(BorderFactory.createLineBorder(Color.RED));
        restrictionContainer.add(restrictionImg);
        informationPanel.add(restrictionContainer);

        JPanel yearContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        yearText = new JLabel("Release date: ");
        yearText.setFont(GenreTypes.getFont());
        yearContainer.add(yearText);
        informationPanel.add(yearContainer);

        panel.add(informationPanel, BorderLayout.PAGE_START);
        panel.add(getActionPanel(), BorderLayout.CENTER);
        return panel;
    }

    private Component getActionPanel() {
        playContainer = new JPanel(new GridBagLayout());

        playWrapper = new JPanel();
        BoxLayout layout = new BoxLayout(playWrapper, BoxLayout.Y_AXIS);
        playWrapper.setLayout(layout);

        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Display.getInstance().setPage(Page.PREVIEWPAGE);
            }
        });
        playWrapper.add(backButton);

        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Play + " + currentMedia.toString());
            }
        });
        playWrapper.add(playButton);

        playContainer.add(playWrapper);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.add(playContainer);
        scrollPane.setViewportView(playContainer);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }

    private JPanel getImageContainer(){
        JPanel panel = new JPanel();
        imagePanel = new ImageViewer();
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

        title = new JLabel("The God Father");
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
            ratingContainer.add(stars.get(i));
        }
        double remaining = (rating - (wholeStars*2))/2;
        if (remaining > 0){
            if (partialStar == null){
                BufferedImage img = ImageHandler.getInstance().getImage("star");
                partialStar = new PartialImageView(img, remaining);
            }else{
                partialStar.setShowed(remaining);
            }

            partialStar.setPrefferedWidth(40);
            ratingContainer.add(partialStar);
        }
    }

    public void setMedia(Media media) {
        currentMedia = media;
        title.setText(media.getTitle());
        setGenre(media.getGenres());
        setRating(media.getRating());
        restrictionImg.setImage(media.getAgeRestriction().getImage());
        imagePanel.setImage(media.getImage());
        yearText.setText("Release date: " + media.getYear());
        if (media instanceof Movie){
            addDefaultPlayButton();
        }else if (media instanceof Serie){
            addSeriesPlayButton((Serie) media);
        }
    }

    private void addDefaultPlayButton() {
        playContainer.removeAll();
        playContainer.add(playWrapper);
        validate();
        repaint();
    }

    private void addSeriesPlayButton(Serie serie) {
        playContainer.removeAll();
        HashMap<Integer, ArrayList<SeriesEpisode>> seasons = serie.getSeasons();
        for (int season: seasons.keySet()){
            ArrayList<SeriesEpisode> episodes = seasons.get(season);
            JButton episodeButton = new JButton(season + " - " + episodes.size());
            episodeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setEpisodePlayButton(episodes);
                }
            });
            playContainer.add(episodeButton);
        }
        playContainer.add(backButton);
        validate();
        repaint();
    }

    private void setEpisodePlayButton(ArrayList<SeriesEpisode> episodes){
        playContainer.removeAll();
        for (SeriesEpisode episode: episodes){
            JButton episodeButton = new JButton(episode.toString());
            episodeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setEpisodePlayButton(episodes);
                }
            });
            playContainer.add(episodeButton);
        }
        playContainer.add(backToSelectionButton);
        System.out.println("E");
        validate();
        repaint();
    }
}
