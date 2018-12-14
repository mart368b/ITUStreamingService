package ui.pages;

import maincomponents.AvMinArm;
import maincomponents.Tickable;
import maincomponents.Timer;
import medias.Media;
import medias.Movie;
import medias.Serie;
import medias.SeriesEpisode;
import medias.types.MediaTypes;
import ui.Display;
import ui.StyleArchive;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MediaPlayerPage extends Page implements Tickable {

    private static Color pauseColor = Color.BLUE, playColor = Color.RED;
    private JPanel playerView, controller;
    private JButton playButton, backButton;
    private JSlider progressBar;
    private JLabel timeStamp;
    private MediaTypes displayedType;
    private Media displayedMedia;
    private SeriesEpisode episode;
    private Page previousPage;
    private Color[] colors = new Color[]{
            Color.RED, Color.BLUE
    };
    private boolean playing = false;
    private Timer timer;

    protected MediaPlayerPage(){
        super(new BorderLayout());
        setBackground(StyleArchive.COLOR_BACKGROUND);
        playerView = getPlayerView();
        add(playerView, BorderLayout.CENTER);

        controller = getController();
        add(controller, BorderLayout.PAGE_END);

        timer = new Timer(this);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (displayedMedia != null){
                updateWatchList();
            }
        }));
    }

    private JPanel getPlayerView(){
        JPanel panel = new JPanel();
        panel.setBackground(pauseColor);
        return panel;
    }

    private JPanel getController() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(StyleArchive.COLOR_BACKGROUND);
        GridBagConstraints c = new GridBagConstraints();

        JPanel buttonWrapper = getButtons();
        buttonWrapper.setBackground(StyleArchive.COLOR_BACKGROUND);
        buttonWrapper.setMaximumSize(buttonWrapper.getPreferredSize());
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        panel.add(buttonWrapper, c);
        
        JPanel progressBarWrapper = getProgressBar();
        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        panel.add(progressBarWrapper, c);

        return panel;
    }

    private JPanel getProgressBar() {
        JPanel panel = new JPanel(new BorderLayout());

        progressBar = new JSlider();
        progressBar.setBackground(StyleArchive.COLOR_BACKGROUND);
        progressBar.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setTimeStamp(progressBar.getValue());
            }
        });
        panel.add(progressBar, BorderLayout.CENTER);

        timeStamp = new JLabel("10:0");
        panel.add(timeStamp, BorderLayout.EAST);
        return panel;
    }

    private JPanel getButtons() {
        JPanel panel = new JPanel(new GridLayout(1,0));

        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playing){
                    togglePlay();
                }
                updateWatchList();
                displayedMedia = null;
                Display.setPage(MediaPlayerPage.this.previousPage);
            }
        });
        panel.add(backButton);
        playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePlay();
            }
        });
        panel.add(playButton);

        return panel;
    }

    private void setTimeStamp(int seconds){
        int secondCount = seconds % 60;
        int minutes = seconds / 60;
        int minuteCount = minutes % 60;
        int hours = minutes/60;
        StringBuilder builder = new StringBuilder();

        if (hours > 0)builder.append(hours + ":");
        else builder.append("0:");

        if (hours > 0 || minuteCount > 0){
            if(minuteCount < 10) builder.append("0" + minuteCount + ":");
            else builder.append(minuteCount + ":");
        }else{ builder.append("00:");}

        if(secondCount < 10) builder.append("0" + secondCount);
        else builder.append(secondCount);

        timeStamp.setText(builder.toString());
        progressBar.setValue(seconds);
    }

    public void displayMovie(Movie movie, Page previousPage){
        int timeStamp = AvMinArm.profile.getMovieTimeStamp(movie.getId());
        updateDisplayed(movie, timeStamp, movie.getDuration(), previousPage);
    }

    public void displaySeries(SeriesEpisode episode, Serie serie, Page previousPage){
        int timeStamp = AvMinArm.profile.getSeriesTimeStamp(serie.getId(), episode.getSeasonNumber(), episode.getEpisodeNumber());
        this.episode = episode;
        updateDisplayed(serie, timeStamp, episode.getDuration(), previousPage);
    }

    private void updateDisplayed(Media media, int timeStamp, int duration, Page previousPage){
        this.previousPage = previousPage;
        displayedMedia = media;
        displayedType = MediaTypes.getMediaType(media);
        progressBar.setMaximum(duration);
        setTimeStamp(timeStamp);
        this.previousPage = previousPage;
        if (playing){
            togglePlay();
        }
    }

    private void togglePlay(){
        playing = !playing;
        playButton.setText(playing?"Pause":"Play");
        playerView.setBackground(colors[playing?0:1]);
        if (playing){
            timer.play();
        }else {
            timer.pause();
        }
    }

    private void updateWatchList(){
        switch (displayedType){
            case MOVIE:
                AvMinArm.profile.addWatchedMovie(displayedMedia.getId(), progressBar.getValue());
                break;
            case SERIES:
                Serie serie = (Serie) displayedMedia;
                AvMinArm.profile.addWatchedSeriesEpisode(serie.getId(), episode.getSeasonNumber(), episode.getEpisodeNumber(), progressBar.getValue());
                break;
        }
    }

    @Override
    public void tick() {
        setTimeStamp(progressBar.getValue() + 1);
    }
}
