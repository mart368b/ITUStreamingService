package ui.panels;

import maincomponents.SearchComparator;
import maincomponents.controllers.PreviewController;
import medias.types.Genre;
import medias.Media;
import medias.types.MediaTypes;
import medias.types.SortTypes;
import reader.MediaHandler;
import ui.Display;
import ui.StyleArchive;
import ui.cards.HeaderCard;
import ui.components.ToggleImageButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class PreviewPage extends Page {

    private ArrayList<Media> displayedMedia = new ArrayList<Media>();
    private JPanel previewMenu;
    private JPanel noResult;
    private JTextField minRating, maxRating, minYear, maxYear;
    private int itemCount;

    protected PreviewPage(Display display){
        super();
        setLayout(new BorderLayout());
        Dimension d = new Dimension(720, 480);
        setSize(d);

        add(HeaderCard.getInstance(), BorderLayout.PAGE_START);

        JPanel cardPreviewPanel = new JPanel();
        cardPreviewPanel.setLayout(new BorderLayout());

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                int w = componentEvent.getComponent().getWidth();
                if(w > 0){
                    setViewPortWidth(componentEvent.getComponent().getWidth(), itemCount);
                }
            }
        });

        cardPreviewPanel.add(getOptionMenu(), BorderLayout.PAGE_START);
        
        initializeNoResultMessage();

        previewMenu = new JPanel();
        previewMenu.setBorder(BorderFactory.createEmptyBorder());
        previewMenu.setAutoscrolls(true);
        previewMenu.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        previewMenu.setBackground(StyleArchive.COLOR_BACKGROUND);

        JScrollPane scrollPane = new JScrollPane(previewMenu, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setViewportView(previewMenu);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        cardPreviewPanel.add(scrollPane, BorderLayout.CENTER);
        add(cardPreviewPanel, BorderLayout.CENTER);

        PreviewController.init(this);
        PreviewController.displayMedia();

    }

    private JPanel getOptionMenu(){
        JPanel panel = new JPanel( new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(StyleArchive.COLOR_BACKGROUND);

        panel.setBorder(BorderFactory.createEmptyBorder());

        JLabel sortText = new JLabel("sort:");
        panel.add(sortText);
        JComboBox<String> sortTypeBox = new JComboBox<String>(SortTypes.getSortTypeNames());
        sortTypeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String item = (String) sortTypeBox.getSelectedItem();
                SortTypes sortType = SortTypes.valueOf(item.toUpperCase());
                PreviewController.setSortingType(sortType);
            }
        });
        panel.add(sortTypeBox);

        JLabel reverseText = new JLabel("Reverse:");
        panel.add(reverseText);

        ToggleImageButton radioButton = new ToggleImageButton("up", "down");
        radioButton.setBackground(StyleArchive.COLOR_BACKGROUND);
        radioButton.setPrefferedWidth(30);
        radioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PreviewController.reverseSorting();
            }
        });
        panel.add(radioButton);

        Dimension deafaultSize = new Dimension(60, 20);

        JLabel yearText = new JLabel(" Release  min:");
        panel.add(yearText);

        KeyListener endFilterKey = new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    PreviewController.displayMedia();
                }
            }

            public void keyTyped(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}
        };

        minYear = new JTextField("");
        minYear.setPreferredSize(deafaultSize);
        minYear.addKeyListener(endFilterKey);
        panel.add(minYear);

        JLabel m = new JLabel("max:");
        panel.add(m);

        maxYear = new JTextField();
        maxYear.setPreferredSize(deafaultSize);
        maxYear.addKeyListener(endFilterKey);
        panel.add(maxYear);

        JLabel ratingText = new JLabel(" Rating  min:");
        panel.add(ratingText);

        minRating = new JTextField();
        minRating.setPreferredSize(deafaultSize);
        minRating.addKeyListener(endFilterKey);
        panel.add(minRating);

        JLabel m1 = new JLabel("max:");
        panel.add(m1);

        maxRating = new JTextField();
        maxRating.setPreferredSize(deafaultSize);
        maxYear.addKeyListener(endFilterKey);
        panel.add(maxRating);

        JButton resetLimit = new JButton("Reset limits");
        resetLimit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PreviewController.resetBoundaries();
            }
        });
        panel.add(resetLimit);

        return panel;
    }

    public void updatePreview(List<Media> displayedMedia){
        previewMenu.removeAll();
        if (displayedMedia.size() == 0){
            previewMenu.add(noResult);
        }else{
            for (Media media: displayedMedia){
                previewMenu.add(media.getPreviewCard());
            }
        }
        Dimension d = getSize();
        setViewPortWidth(d.width, displayedMedia.size());
        validate();
        repaint();
    }

    private void initializeNoResultMessage() {
        noResult = new JPanel();
        JLabel title = new JLabel("Nothing is here yet");
        noResult.add(title);
    }

    public void setViewPortWidth(int width, int itemCount) {
        this.itemCount = itemCount;
        int rowSize = Math.floorDiv(width, 150);
        int columnCount = -Math.floorDiv(-itemCount, rowSize);
        Dimension d = new Dimension(width, columnCount*219 + 10);
        previewMenu.setPreferredSize(d);
    }

    public JTextField getMinYear(){
        return minYear;
    }

    public JTextField getMaxYear(){
        return maxYear;
    }

    public JTextField getMinRating(){
        return minRating;
    }

    public JTextField getMaxRating(){
        return maxRating;
    }

}
