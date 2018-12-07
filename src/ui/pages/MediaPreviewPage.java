package ui.pages;

import medias.Movie;
import medias.Serie;
import medias.SeriesEpisode;
import medias.types.Genre;
import medias.Media;
import ui.Display;
import ui.StyleArchive;
import ui.components.ImageViewer;
import ui.components.PartialImageView;
import maincomponents.ImageHandler;

import javax.swing.*;
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
    private JPanel genreContainer, ratingContainer, actionContainer, actionWrapper;
    private ImageViewer restrictionImg;
    private JLabel genreText, ratingText, yearText;
    private JButton backButton, backToSelectionButton, playButton;
    private PartialImageView partialStar;
    private ArrayList<ImageViewer> stars;
    private Media currentMedia;

    protected MediaPreviewPage(){
        super(new BorderLayout());
        initializeStars();

        JPanel titlePanel = getTitlePanel();
        titlePanel.setBackground(StyleArchive.COLOR_BACKGROUND);
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
            star.setBackground(StyleArchive.COLOR_BACKGROUND);
            stars.add(star);
        }
    }

    private JPanel getBody(){
        JPanel body = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel bioContianer = getBioBody();
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

    private JPanel getBioBody() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel informationPanel = new JPanel();
        BoxLayout layout = new BoxLayout(informationPanel, BoxLayout.Y_AXIS);
        informationPanel.setLayout(layout);

        genreContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genreContainer.setBackground(StyleArchive.COLOR_BACKGROUND);
        genreText = new JLabel("Genre: ");
        genreText.setFont(StyleArchive.HEADER);
        genreContainer.add(genreText);
        informationPanel.add(genreContainer);

        ratingContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ratingContainer.setBackground(StyleArchive.COLOR_BACKGROUND);
        ratingText = new JLabel("Rating: ");
        ratingText.setFont(StyleArchive.HEADER);
        ratingContainer.add(ratingText);
        informationPanel.add(ratingContainer);

        JPanel restrictionContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        restrictionContainer.setBackground(StyleArchive.COLOR_BACKGROUND);
        JLabel restrictionText = new JLabel("Restriction: ");
        restrictionText.setFont(StyleArchive.HEADER);
        restrictionContainer.add(restrictionText);

        restrictionImg = new ImageViewer();
        restrictionImg.setPreferredSize(new Dimension(40,40));
        restrictionImg.setBorder(BorderFactory.createLineBorder(Color.RED));
        restrictionContainer.add(restrictionImg);
        informationPanel.add(restrictionContainer);

        JPanel yearContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        yearContainer.setBackground(StyleArchive.COLOR_BACKGROUND);
        yearText = new JLabel("Release date: ");
        yearText.setFont(StyleArchive.HEADER);
        yearContainer.add(yearText);
        informationPanel.add(yearContainer);

        panel.add(informationPanel, BorderLayout.PAGE_START);
        panel.add(getActionPanel(), BorderLayout.CENTER);
        return panel;
    }

    private Component getActionPanel() {
        actionContainer = new JPanel(new GridBagLayout());
        actionContainer.setBackground(StyleArchive.COLOR_BACKGROUND);

        actionWrapper = new JPanel();
        actionWrapper.setBackground(StyleArchive.COLOR_BACKGROUND);
        BoxLayout layout = new BoxLayout(actionWrapper, BoxLayout.Y_AXIS);
        actionWrapper.setLayout(layout);

        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Display.setPage(PageHandler.PREVIEWPAGE);
            }
        });
        actionWrapper.add(backButton);

        playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MediaPlayerPage playerPage = (MediaPlayerPage) PageHandler.getPage(PageHandler.MEDIAPLAYERPAGE);
                playerPage.displayMovie((Movie) currentMedia, MediaPreviewPage.this);
                Display.setPage(playerPage);
            }
        });
        actionWrapper.add(playButton);

        actionContainer.add(actionWrapper);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.add(actionContainer);
        scrollPane.setViewportView(actionContainer);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }

    private JPanel getImageContainer(){
        JPanel panel = new JPanel();
        panel.setBackground(StyleArchive.COLOR_BACKGROUND);
        imagePanel = new ImageViewer();
        Dimension d = new Dimension(280, 418);
        imagePanel.setPreferredSize(d);
        imagePanel.setMinimumSize(d);
        panel.add(imagePanel);
        return panel;
    }

    private JPanel getTitlePanel(){
        JPanel titleWrapper = new JPanel();
        titleWrapper.setBorder(StyleArchive.UNDER_LINE_BORDER);

        title = new JLabel();
        title.setForeground(Color.BLACK);
        title.setFont(titleFont);
        titleWrapper.add(title);
        return titleWrapper;
    }

    public void setGenre(Genre[] genres ){
        genreContainer.removeAll();
        genreContainer.add(genreText);
        for (Genre genre: genres){
            genreContainer.add(genre.getGenreCard());
        }
        genreContainer.validate();
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
                partialStar.setBackground(StyleArchive.COLOR_BACKGROUND);
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
        actionWrapper.removeAll();
        actionWrapper.add(playButton);
        actionWrapper.add(backButton);
        actionWrapper.validate();
        actionWrapper.repaint();
    }

    private void addSeriesPlayButton(Serie serie) {
        actionWrapper.removeAll();
        HashMap<Integer, ArrayList<SeriesEpisode>> seasons = serie.getSeasons();
        for (int season: seasons.keySet()){
            ArrayList<SeriesEpisode> episodes = seasons.get(season);
            JButton episodeButton = new JButton(season + " - " + episodes.size());
            episodeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setEpisodePlayButton(episodes, serie);
                }
            });
            actionWrapper.add(episodeButton);
        }
        actionWrapper.add(backButton);
        actionWrapper.validate();
        actionWrapper.repaint();
    }

    private void setEpisodePlayButton(ArrayList<SeriesEpisode> episodes, Serie serie){
        actionWrapper.removeAll();
        for (SeriesEpisode episode: episodes){
            JButton episodeButton = new JButton(episode.toString());
            episodeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    MediaPlayerPage playerPage = (MediaPlayerPage) PageHandler.getPage(PageHandler.MEDIAPLAYERPAGE);
                    playerPage.displaySeries(episode, serie, (Page) MediaPreviewPage.this);
                    Display.setPage(playerPage);
                }
            });
            actionWrapper.add(episodeButton);
        }
        actionWrapper.add(backToSelectionButton);
        actionWrapper.validate();
        actionWrapper.repaint();
    }
}
