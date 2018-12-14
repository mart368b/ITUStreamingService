package ui.pages;

import maincomponents.AvMinArm;
import maincomponents.controllers.PreviewController;
import medias.Media;
import medias.types.Genre;
import medias.types.SortTypes;
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
    private HeaderCard header;
    private int itemCount;
    private JComboBox<String> sortTypeBox;
    private ToggleImageButton sortingDir;

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

    }

    private JPanel getOptionMenu(){
        JPanel panel = new JPanel( new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(StyleArchive.COLOR_BACKGROUND);

        panel.setBorder(BorderFactory.createEmptyBorder());

        JLabel sortText = new JLabel("Sort:");
        panel.add(sortText);

        sortTypeBox = new JComboBox<String>(SortTypes.getSortTypeNames());
        sortTypeBox.setBackground(StyleArchive.COLOR_BACKGROUND);
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

        sortingDir = new ToggleImageButton("up", "down");
        sortingDir.setBorderPainted(false);
        sortingDir.setBackground(StyleArchive.COLOR_BACKGROUND);
        sortingDir.setPrefferedWidth(30);
        sortingDir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PreviewController.reverseSorting();
            }
        });
        panel.add(sortingDir);

        Dimension deafaultSize = new Dimension(60, 20);

        JLabel yearText = new JLabel(" Release  min:");
        panel.add(yearText);

        ActionListener endFilterKey = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PreviewController.displayMedia();
            }
        };

        minYear = new JTextField("");
        minYear.setPreferredSize(deafaultSize);
        minYear.addActionListener(endFilterKey);
        panel.add(minYear);

        JLabel m = new JLabel("max:");
        panel.add(m);

        maxYear = new JTextField();
        maxYear.setPreferredSize(deafaultSize);
        maxYear.addActionListener(endFilterKey);
        panel.add(maxYear);

        JLabel ratingText = new JLabel(" Rating  min:");
        panel.add(ratingText);

        minRating = new JTextField();
        minRating.setPreferredSize(deafaultSize);
        minRating.addActionListener(endFilterKey);
        panel.add(minRating);

        JLabel m1 = new JLabel("max:");
        panel.add(m1);

        maxRating = new JTextField();
        maxRating.setPreferredSize(deafaultSize);
        maxRating.addActionListener(endFilterKey);
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
                if(AvMinArm.profile.getAge() <= 7){
                    for(Genre genre : media.getGenres()){
                        if(genre.getName().toLowerCase().equals("family")){
                            previewMenu.add(media.getPreviewCard());
                        }
                    }
                    continue;
                }
                previewMenu.add(media.getPreviewCard());
            }
        }
        AdminPage adminPage = (AdminPage) PageFactory.getPage(PageFactory.ADMINPAGE);


        Dimension d = getSize();
        setViewPortWidth(d.width, displayedMedia.size());
        validate();
        repaint();
    }

    private void initializeNoResultMessage() {
        noResult = new JPanel();
        noResult.setBackground(StyleArchive.COLOR_BACKGROUND);
        JLabel title = new JLabel("Nothing is here yet");
        noResult.setFont(StyleArchive.NORMAL);
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


    @Override
    public void addToDisplay(Display display){
        super.addToDisplay(display);
        PreviewController.displayMedia();
        PreviewController.resetBoundaries();
        HeaderCard.getInstance().reset();
        sortTypeBox.setSelectedIndex(0);
        if (sortingDir.isActive()){
            sortingDir.toggle();
            PreviewController.reverseSorting();
        }
        PreviewController.displayMedia();
    }
}
