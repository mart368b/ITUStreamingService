package ui.panels;

import medias.Categories;
import medias.Media;
import medias.MediaTypes;
import medias.SortTypes;
import reader.MediaHandler;
import ui.Display;
import ui.cards.HeaderCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class PreviewPage extends Page {

    private ArrayList<Media> displayedMedia = new ArrayList<Media>();
    private JPanel previewMenu;

    private JPanel noResult;

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

        JScrollPane scrollPane = new JScrollPane(previewMenu, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setViewportView(previewMenu);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        MediaHandler.getInstance().getAllMedia(displayedMedia);

        cardPreviewPanel.add(scrollPane, BorderLayout.CENTER);
        sortPreview(SortTypes.ALPHABETICLY);

        add(cardPreviewPanel, BorderLayout.CENTER);

    }

    private JPanel getOptionMenu(){
        JPanel panel = new JPanel( new FlowLayout(FlowLayout.LEFT));

        panel.setBorder(BorderFactory.createEmptyBorder());

        JLabel sortText = new JLabel("sort:");
        panel.add(sortText);
        JComboBox<String> sortTypeBox = new JComboBox<String>(SortTypes.getSortTypeNames());
        sortTypeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String item = (String) sortTypeBox.getSelectedItem();
                SortTypes sortTypes = SortTypes.valueOf(item.toUpperCase());
                sortPreview(sortTypes);
            }
        });
        panel.add(sortTypeBox);

        return panel;
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
        JLabel title = new JLabel("No results could be found");
        noResult.add(title);
    }

    public void setViewPortWidth(int width) {
        int rowSize = Math.floorDiv(width, 150);
        int columnCount = displayedMedia.size()/rowSize;
        Dimension d = new Dimension(width, columnCount*219 + 10);
        previewMenu.setPreferredSize(d);
    }

    public void setDisplayedMedia(){
        displayedMedia.clear();
        MediaHandler.getInstance().getAllMedia(displayedMedia);
        updatePreview();
    }

    public void setDisplayedMedia(MediaTypes mediaTypes){
        displayedMedia.clear();
        MediaHandler.getInstance().getAllMedia(displayedMedia, mediaTypes);
        updatePreview();
    }

    public void setDisplayedMedia(Categories category){
        displayedMedia.clear();
        MediaHandler.getInstance().getAllMedia(displayedMedia);
        filterDisplayedMedia(category);
    }

    public void filterDisplayedMedia(Categories category){
        if (category != Categories.ANY){
            for (Iterator<Media> it = displayedMedia.iterator(); it.hasNext(); ) {
                Media m = it.next();
                if (!m.haveCategory(category)){
                    it.remove();
                }
            }
        }
        updatePreview();
    }

    public void sortPreview(SortTypes sortType){
        Collections.sort(displayedMedia, sortType.getComparator());
        updatePreview();
    }
}
