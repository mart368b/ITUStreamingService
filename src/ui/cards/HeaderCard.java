package ui.cards;

import maincomponents.AvMinArm;
import maincomponents.controllers.PreviewController;
import medias.types.Genre;
import medias.types.MediaTypes;
import ui.Display;
import ui.StyleArchive;
import ui.components.ImageButton;
import ui.pages.PageHandler;
import ui.pages.PreviewPage;
import user.Profile;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;

public class HeaderCard extends JPanel {

    private Display display;
    private ImageButton userButton;
    private JTextField searchField;
    private JComboBox<String> genreBox;

    private static HeaderCard instance;
    public static HeaderCard getInstance() {
        if (instance == null){
            instance = new HeaderCard();
        }
        return instance;
    }

    private HeaderCard(){
        super();
        Border border = BorderFactory.createMatteBorder(0,0,2,0, Color.LIGHT_GRAY);
        setBorder(border);

        JPanel leftPanel = getLeftPanel();
        JPanel centerPanel = getCenterPanel();
        JPanel rightPanel = getRightPanel();

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(leftPanel);
        add(centerPanel);
        add(rightPanel);


    }

    private JPanel getLeftPanel(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(StyleArchive.COLOR_BACKGROUND);

        ImageButton startButton = new ImageButton("logo");
        startButton.setBorderPainted(false);
        startButton.setPrefferedHeight(30);
        startButton.setBackground(StyleArchive.COLOR_BACKGROUND);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PreviewPage previewPanel = (PreviewPage) PageHandler.getPage(PageHandler.PREVIEWPAGE);
                PreviewController.displayMedia();
            }
        });
        panel.add(startButton);

        return panel;
    }

    private  JPanel getCenterPanel(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(StyleArchive.COLOR_BACKGROUND);

        addButton(panel, "Movies", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                PreviewController.displayMedia(MediaTypes.MOVIE);
            }
        });

        addButton(panel, "Series", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                PreviewController.displayMedia(MediaTypes.SERIES);
            }
        });

        addButton(panel, "My List", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Profile profile = AvMinArm.profile;
                PreviewController.displayMedia(profile.getFavorites());
            }
        });

        panel.add(getSearchField());

        return panel;
    }

    private JPanel getRightPanel(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(StyleArchive.COLOR_BACKGROUND);
        userButton = new ImageButton();
        userButton.setPreferredSize(new Dimension(40,40));
        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Display.setPage(PageHandler.PROFILEPICKERPAGE);
            }
        });
        panel.add(userButton);


        return panel;
    }

    private static void addButton(JPanel panel, String name, ActionListener listener){
        JButton newButton = new JButton(name);
        newButton.addActionListener(listener);
        panel.add(newButton);
    }

    private JPanel getSearchField() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel genreText = new JLabel("Categories:");
        panel.add(genreText);
        genreBox = new JComboBox<String>(Genre.getGenreNames());
        genreBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = searchField.getText();
                Genre genre = Genre.getGenreByName((String) genreBox.getSelectedItem());
                PreviewController.displayMedia(title, genre);
            }
        });
        panel.add(genreBox);

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(100, 20));
        panel.add(searchField);
        panel.setBackground(StyleArchive.COLOR_BACKGROUND);

        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = searchField.getText();
                Genre genre = Genre.getGenreByName((String) genreBox.getSelectedItem());
                PreviewController.displayMedia(title, genre);
            }
        });

        return panel;
    }

    public void setProfilePicture(Profile profile){
        userButton.setImage(profile.getImage());
        userButton.repaint();
    }

    public void reset(){
        genreBox.setSelectedIndex(0);
        searchField.setText("");
    }

}
