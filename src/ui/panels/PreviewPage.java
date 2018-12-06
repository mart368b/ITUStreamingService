package ui.panels;

import maincomponents.SearchComparator;
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
    private SortTypes lastSort;
    private boolean reversedSorting = false;
    private JTextField minRating, maxRating, minYear, maxYear;

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
                    setViewPortWidth(componentEvent.getComponent().getWidth());
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

        setDisplayedMedia();
        sortPreview(SortTypes.ALPHABETICLY, reversedSorting);

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
                SortTypes sortTypes = SortTypes.valueOf(item.toUpperCase());
                sortPreview(sortTypes, reversedSorting);
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
                reversedSorting = !reversedSorting;
                sortPreview(lastSort, reversedSorting);
            }
        });
        panel.add(radioButton);

        Dimension deafaultSize = new Dimension(60, 20);

        JLabel yearText = new JLabel(" Release  min:");
        panel.add(yearText);

        KeyListener endFilterKey = new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    getAllMedia();
                    filterBoundaries();
                    updatePreview();
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

        return panel;
    }

    public static boolean isNumber(String s){
        try {
            Integer.parseInt(s);
            return true;
        }catch (Exception e){return false;}
    }

    public static boolean isDouble(String s){
        try {
            Double.parseDouble(s);
            return true;
        }catch (Exception e){return false;}
    }

    public void updatePreview(){
        previewMenu.removeAll();
        if (displayedMedia.size() == 0){
            previewMenu.add(noResult);
        }else{
            for (Media media: displayedMedia){
                previewMenu.add(media.getPreviewCard());
            }
        }
        Dimension d = getSize();
        setViewPortWidth(d.width);
        validate();
        repaint();
    }

    private void initializeNoResultMessage() {
        noResult = new JPanel();
        JLabel title = new JLabel("Nothing is here yet");
        noResult.add(title);
    }

    public void setViewPortWidth(int width) {
        int rowSize = Math.floorDiv(width, 150);
        int columnCount = -Math.floorDiv(-displayedMedia.size(), rowSize);
        Dimension d = new Dimension(width, columnCount*219 + 10);
        previewMenu.setPreferredSize(d);
    }

    public void setDisplayedMedia(){
        displayedMedia.clear();
        getAllMedia();

        sortPreview(lastSort, reversedSorting);
        updatePreview();
    }

    public boolean validateLimits(){
        if (minRating.getText().length() == 0){
            double[] ratings = displayedMedia.stream().mapToDouble( Media::getRating).sorted().toArray();

            minRating.setText(Double.toString(ratings[0]));
            maxRating.setText(Double.toString(ratings[ratings.length - 1]));

            int[] years = displayedMedia.stream().mapToInt( m -> {
                String[] yearsPart = m.getYear().split("-");
                return Integer.parseInt(yearsPart[yearsPart.length - 1]);
            }).sorted().toArray();

            minYear.setText(Integer.toString(years[0]));
            maxYear.setText(Integer.toString(years[years.length - 1]));
            return true;
        }else{
            if (!isNumber(minYear.getText())){
                return false;
            }
            if (!isNumber(maxYear.getText())){
                return false;
            }
            if(!isDouble(minRating.getText())){
                return false;
            }
            if (!isDouble(maxRating.getText())){
                return false;
            }
            return true;
        }
    }

    public void getAllMedia(){
        MediaHandler.getInstance().getAllMedia(displayedMedia);
        filterBoundaries();
    }

    public void getAllMedia(MediaTypes mediaTypes){
        MediaHandler.getInstance().getAllMedia(displayedMedia, mediaTypes);
        filterBoundaries();
    }

    private void filterBoundaries() {
        boolean valid = validateLimits();
        if(!valid){
            return;
        }

        int lowYearLimit = Integer.parseInt(minYear.getText());
        int highYearLimit = Integer.parseInt(maxYear.getText());
        double lowRatingLimit = Double.parseDouble(minRating.getText());
        double highRatingLimit = Double.parseDouble(maxRating.getText());
        for (Iterator<Media> it = displayedMedia.iterator(); it.hasNext(); ) {
            Media m = it.next();
            String[] yearPart = m.getYear().split("-");
            int year = Integer.parseInt(yearPart[yearPart.length - 1]);
            double rating = m.getRating();
            if (year < lowYearLimit || year > highYearLimit || rating < lowRatingLimit || rating > highRatingLimit){
                it.remove();
            }
        }
    }

    public void setDisplayedMedia(MediaTypes mediaTypes){
        displayedMedia.clear();
        getAllMedia(mediaTypes);
        sortPreview(lastSort, reversedSorting);
        updatePreview();
    }

    public void setDisplayedMedia(Genre genre, String title){
        displayedMedia.clear();
        getAllMedia();
        filterDisplayedMedia(genre);
        SearchComparator c = SearchComparator.getSearchComparator(title);
        displayedMedia.sort(c);
        updatePreview();
    }

    public void setDisplayedMedia(List<Media> medias){
        displayedMedia.clear();
        getAllMedia();
        filterDisplayedMedia(medias);
        sortPreview(lastSort, reversedSorting);
    }

    public void filterDisplayedMedia(Genre genre){
        if (genre.getName() != "Any"){
            for (Iterator<Media> it = displayedMedia.iterator(); it.hasNext(); ) {
                Media m = it.next();
                if (!m.hasGenre(genre)){
                    it.remove();
                }
            }
        }
        updatePreview();
    }

    public void filterDisplayedMedia(List<Media> medias){
        for (Iterator<Media> it = displayedMedia.iterator(); it.hasNext(); ) {
            Media m = it.next();
            if (!medias.contains(m)){
                it.remove();
            }
        }
        updatePreview();
    }

    public void sortPreview(SortTypes sortType, boolean reverse){
        if (sortType == null){
            return;
        }
        Comparator<Media> comp = sortType.getComparator();
        if (reverse){
            comp = Collections.reverseOrder(comp);
        }
        Collections.sort(displayedMedia, comp);
        lastSort = sortType;
        updatePreview();
    }
}
