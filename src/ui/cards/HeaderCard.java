package ui.cards;

import maincomponents.AvMinArm;
import medias.types.GenreTypes;
import medias.types.MediaTypes;
import ui.Display;
import ui.components.ImageButton;
import ui.panels.Page;
import ui.panels.PreviewPage;
import user.Profile;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;

public class HeaderCard extends JPanel {

    private Display display;
    private ImageButton userButton;

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

        ImageButton startButton = new ImageButton("logo");
        startButton.setBorderPainted(false);
        startButton.setPrefferedHeight(30);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PreviewPage previewPanel = (PreviewPage) Page.getPage(Page.PREVIEWPAGE);
                previewPanel.setDisplayedMedia();
            }
        });
        panel.add(startButton);

        return panel;
    }

    private  JPanel getCenterPanel(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));


        addButton(panel, "Movies", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                PreviewPage previewPanel = (PreviewPage) Page.getPage(Page.PREVIEWPAGE);
                previewPanel.setDisplayedMedia(MediaTypes.MOVIE);
            }
        });

        addButton(panel, "Series", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                PreviewPage previewPage = (PreviewPage) Page.getPage(Page.PREVIEWPAGE);
                previewPage.setDisplayedMedia(MediaTypes.SERIES);
            }
        });

        addButton(panel, "My List", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Profile profile = AvMinArm.profile;
                PreviewPage previewPage = (PreviewPage) Page.getPage(Page.PREVIEWPAGE);
                previewPage.setDisplayedMedia(profile.getFavorites());
            }
        });

        panel.add(getSearchField());

        return panel;
    }

    private JPanel getRightPanel(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userButton = new ImageButton();
        userButton.setPreferredSize(new Dimension(40,40));
        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Display.getInstance().setPage(Page.USERPAGE);
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

    private JPanel getSearchField(){
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel genreText = new JLabel("categories:");
        panel.add(genreText);
        JComboBox<String> categoriesBox = new JComboBox<String>(GenreTypes.getGenreNames());
        panel.add(categoriesBox);

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(100, 20));
        panel.add(textField);

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                peformeSearch(textField, categoriesBox);
            }
        });

        return panel;
    }

    private void peformeSearch(JTextField textField, JComboBox categoriesBox){
        String genreName = (String) categoriesBox.getSelectedItem();
        GenreTypes genre = GenreTypes.getGenreTypeByName(genreName);

        String searchedName = textField.getText();

        Display.getInstance().displayOnPreview(genre, searchedName);
        System.out.println( "Search for " + genre.name() + " " + searchedName);
    }

    public void setProfilePicture(Profile profile){
        userButton.setImage(profile.getImage());
        userButton.repaint();
    }

}
