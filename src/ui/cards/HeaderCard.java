package ui.cards;

import maincomponents.AvMinArm;
import medias.Categories;
import medias.MediaTypes;
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
            throw new NullPointerException();
        }
        return instance;
    }

    public static void createHeader( Display display ){
        instance = new HeaderCard(display);
    }

    private HeaderCard(Display display){
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

        ImageButton startButton = new ImageButton(AvMinArm.logoImage);
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
                System.out.println("My List pressed");
            }
        });

        panel.add(getSearchField());

        return panel;
    }

    private JPanel getRightPanel(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userButton = new ImageButton(null);
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

        JLabel categoryText = new JLabel("categories:");
        panel.add(categoryText);
        JComboBox<String> categoriesBox = new JComboBox<String>(Categories.getCategorieNames());
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
        String categoryName = (String) categoriesBox.getSelectedItem();
        Categories category = Categories.getCategoryByName(categoryName);

        String searchedName = textField.getText();

        Display.getInstance().displayOnPreview(category, searchedName);
        System.out.println( "Search for " + category.name() + " " + searchedName);
    }

    public void setProfilePicture(Profile profile){
        userButton.setImgage(profile.getImage());
        userButton.repaint();
    }

}
