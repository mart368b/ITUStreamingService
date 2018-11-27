package ui;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_COLOR_BURNPeer;
import javafx.scene.input.KeyCode;
import medias.Categories;
import medias.SortTypes;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

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

        JButton userButton = new JButton("User");
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

        textField.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    String categoryName = (String) categoriesBox.getSelectedItem();
                    Categories category = Categories.getCategoryByName(categoryName);

                    String sortTypeName = (String) sortTypeBox.getSelectedItem();
                    SortTypes sortType = SortTypes.valueOf(sortTypeName.toUpperCase());

                    String searchedName = textField.getText();

                    System.out.println( "Search for " + category.name() + " " + searchedName + " sorted by " + sortType.name());
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        panel.add(textField);

        panel.setBackground(Color.CYAN);
        return panel;
    }

}
