package ui;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_COLOR_BURNPeer;
import javafx.scene.input.KeyCode;
import maincomponents.AvMinArm;
import medias.Categories;
import medias.MediaTypes;
import medias.SortTypes;
import ui.components.ImageButton;
import ui.panels.PreviewPanel;
import user.Profile;
import user.User;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.zip.DeflaterInputStream;

public class HeaderPanel extends JPanel {

    private Display display;
    private ImageButton userButton;

    public HeaderPanel(Display display){
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
                PreviewPanel previewPanel = (PreviewPanel) Display.getDisplay().getPanel(Display.PREVIEWPANEL);
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
                PreviewPanel previewPanel = (PreviewPanel) Display.getDisplay().getPanel(Display.PREVIEWPANEL);
                previewPanel.setDisplayedMedia(MediaTypes.MOVIE);
            }
        });

        addButton(panel, "Series", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                PreviewPanel previewPanel = (PreviewPanel) Display.getDisplay().getPanel(Display.PREVIEWPANEL);
                previewPanel.setDisplayedMedia(MediaTypes.SERIES);
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
                Display.getDisplay().setPanel(Display.USERPANEL);
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

        Display.getDisplay().displayOnPreview(category, searchedName);
        System.out.println( "Search for " + category.name() + " " + searchedName);
    }

    public void setProfilePicture(Profile profile){
        userButton.setImgage(profile.getImage());
        userButton.repaint();
    }

}
