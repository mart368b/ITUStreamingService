package ui;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_COLOR_BURNPeer;
import javafx.scene.input.KeyCode;
import maincomponents.AvMinArm;
import medias.Categories;
import medias.SortTypes;
import user.User;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.zip.DeflaterInputStream;

public class HeaderPanel extends JPanel {

    private Display display;

    public HeaderPanel(Display display){
        super();
        setBackground(Color.WHITE);

        JPanel leftPanel = getLeftPanel();
        JPanel centerPanel = getCenterPanel();
        JPanel rightPanel = getRightPanel();

        leftPanel.setBackground( Color.RED );
        centerPanel.setBackground( Color.GREEN  );
        rightPanel.setBackground( Color.BLUE );

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(leftPanel);
        add(centerPanel);
        add(rightPanel);

    }

    private JPanel getLeftPanel(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Start pressed");
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
                System.out.println("Movie pressed");
            }
        });

        addButton(panel, "Series", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Series pressed");
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

        System.out.println(AvMinArm.user);
        JButton userButton = new JButton("Users");
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
        panel.setBackground(Color.CYAN);

        JLabel categoryText = new JLabel("categories:");
        panel.add(categoryText);
        JComboBox<String> categoriesBox = new JComboBox<String>(Categories.getCategorieNames());
        panel.add(categoriesBox);

        JLabel sortText = new JLabel("sort:");
        panel.add(sortText);
        JComboBox<String> sortTypeBox = new JComboBox<String>(SortTypes.getSortTypeNames());
        panel.add(sortTypeBox);

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(100, 20));
        panel.add(textField);

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                peformeSearch(textField, sortTypeBox, categoriesBox);
            }
        });

        return panel;
    }

    private void peformeSearch(JTextField textField, JComboBox sortTypeBox, JComboBox categoriesBox){
        String categoryName = (String) categoriesBox.getSelectedItem();
        Categories category = Categories.getCategoryByName(categoryName);

        String sortTypeName = (String) sortTypeBox.getSelectedItem();
        SortTypes sortType = SortTypes.valueOf(sortTypeName.toUpperCase());

        String searchedName = textField.getText();

        Display.getDisplay().displayOnPreview(category, sortType, searchedName);
        System.out.println( "Search for " + category.name() + " " + searchedName + " sorted by " + sortType.name());
    }

}
