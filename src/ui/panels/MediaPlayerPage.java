package ui.panels;

import medias.Media;
import medias.Movie;
import medias.SeriesEpisode;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class MediaPlayerPage extends Page {

    private static Color pauseColor = Color.BLUE, playColor = Color.RED;
    private JPanel playerView, controller;
    private JButton playButton, backButton;
    private JSlider progressBar;
    private JLabel timeStamp;
    private Media displayedMedia;

    public MediaPlayerPage(){
        super(new BorderLayout());
        playerView = getPlayerView();
        add(playerView, BorderLayout.CENTER);

        controller = getController();
        add(controller, BorderLayout.PAGE_END);
    }

    private JPanel getPlayerView(){
        JPanel panel = new JPanel();
        panel.setBackground(pauseColor);
        return panel;
    }

    private JPanel getController() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.GREEN);
        GridBagConstraints c = new GridBagConstraints();

        JPanel buttonWrapper = getButtons();
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
        panel.add(progressBar, BorderLayout.CENTER);

        timeStamp = new JLabel("10:0");
        panel.add(timeStamp, BorderLayout.EAST);
        return panel;
    }

    private JPanel getButtons() {
        JPanel panel = new JPanel(new GridLayout(1,0));

        backButton = new JButton("Back");
        panel.add(backButton);
        playButton = new JButton("Play");
        panel.add(playButton);

        return panel;
    }

    private void setTimeStamp(int seconds){
        int secondCount = seconds % 60;
        int minutes = seconds / 60;
        int minuteCount = minutes % 60;
        int hours = minutes/60;
        StringBuilder builder = new StringBuilder();
        if (hours > 0){
            builder.append(hours + ":");
        }
        if (hours > 0 || minuteCount > 0){
            builder.append(minuteCount + ":");
        }
        builder.append(secondCount);
        timeStamp.setText(builder.toString());
        progressBar.setValue(seconds);
    }

    public void displayMovie(Movie movie){
        progressBar.setMaximum(movie.getDuration());
    }

    public void displaySeries(SeriesEpisode episode){
        progressBar.setMaximum(episode.getDuration());
    }
}
