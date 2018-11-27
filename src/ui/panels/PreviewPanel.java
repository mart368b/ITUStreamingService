package ui.panels;

import com.sun.javafx.css.Rule;
import medias.Categories;
import medias.Media;
import medias.MediaTypes;
import medias.SortTypes;
import org.omg.PortableInterceptor.DISCARDING;
import reader.MediaHandler;
import ui.Display;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class PreviewPanel extends JPanel {

    private ArrayList<Media> displayedMedia = new ArrayList<Media>();
    private JPanel previewMenu;

    private JPanel noResult;

    public PreviewPanel( Display display){
        super();
        setLayout(new BorderLayout());
        Dimension d = new Dimension(720, 480);
        setSize(d);

        add(display.getHeaderPanel(), BorderLayout.PAGE_START);

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
        previewMenu.setAutoscrolls(true);
        previewMenu.setBackground(Color.BLUE);
        previewMenu.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JScrollPane scrollPane = new JScrollPane(previewMenu, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(previewMenu);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        MediaHandler.getInstance().getAllMedia(displayedMedia);

        cardPreviewPanel.add(scrollPane, BorderLayout.CENTER);
        updatePreview();

        add(cardPreviewPanel, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLUE);
    }

    private JPanel getOptionMenu(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.RED);
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
    }

    private void initializeNoResultMessage() {
        noResult = new JPanel();
        noResult.setBackground(Color.GRAY);
        JLabel title = new JLabel("No results could be found");
        noResult.add(title);
    }

    public void setViewPortWidth(int width) {
        int rowSize = Math.floorDiv(width, 150);
        int columnCount = displayedMedia.size()/rowSize;
        Dimension d = new Dimension(width, columnCount*219 + 10);
        previewMenu.setPreferredSize(d);
    }

    public void setDisplayedMedia(Categories category, SortTypes sortTypes){
        displayedMedia.clear();
        MediaHandler.getInstance().getAllMedia(displayedMedia);
        if (category != Categories.ANY){
            for (Iterator<Media> it = displayedMedia.iterator(); it.hasNext(); ) {
                Media m = it.next();
                if (!m.haveCategory(category)){
                    it.remove();
                }
            }
        }

        sortPreview(sortTypes);
        updatePreview();
    }

    public void sortPreview(SortTypes sortType){
        Collections.sort(displayedMedia, sortType.getComparator());
    }
}
