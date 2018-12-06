package ui;


import debugging.LogTypes;
import debugging.Logger;
import medias.*;
import medias.types.Genre;
import ui.panels.MediaPreviewPage;
import ui.panels.Page;
import ui.panels.PreviewPage;
import ui.panels.UserPage;
import javax.swing.*;
import java.awt.*;

public class Display extends JFrame  {

    private static Display instance = null;

    public static Display getInstance(){
        if ( instance == null ){
            instance = new Display();
        }
        return instance;
    }

    private int currentPageIndex = -1;

    private Display (){
        super("ITUStreaming");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        initializeDisplay();

        pack();
        setLocationRelativeTo(null);
    }

    private void initializeDisplay() {

        Page.initializePages(this);

        UserPage userPage = (UserPage) Page.getPage(Page.USERPAGE);
        Dimension d = userPage.getPreferredSize();

        d.width = (140 + 10)*6 + 100;
        setMinimumSize(d);
        d.height += 500;
        setPreferredSize(d);

        setPage(userPage);
    }

    public void setPage( Page newPage ) {
        int index = -1;
        for (int i = 0; i < Page.pageCount(); i++) {
            if (Page.getPage(i).equals(newPage)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return;
        }
        Page lastPanel = null;
        if (currentPageIndex != -1) {
            lastPanel = Page.getPage(currentPageIndex);
        }
        changePage(lastPanel, newPage, index);
    }

    public void setPage( int pageIndex ){
        if ( pageIndex == currentPageIndex ){
            return;
        }
        if ( pageIndex < 0 && pageIndex >= Page.pageCount()){
            Logger.log("Failed to find menu " + pageIndex, LogTypes.SOFTERROR);
        }else{
            Page lastPage = null;
            if (currentPageIndex != -1){
                lastPage = Page.getPage(currentPageIndex);
            }
            Page nextPage = Page.getPage(pageIndex);
            changePage(lastPage, nextPage, pageIndex);
        }
    }

    private void changePage(Page lastPage, Page nextPage, int nextIndex){
        if (lastPage != null){
            lastPage.removeFromDisplaye(this);
        }
        nextPage.addToDisplay(this);
        validate();
        repaint();
        currentPageIndex = nextIndex;
    }

    public void displayMedia(Media media){
        MediaPreviewPage mediaPreview = (MediaPreviewPage) Page.getPage(Page.MEDIAPREVIEWPAGE);
        mediaPreview.setMedia(media);
        setPage(Page.MEDIAPREVIEWPAGE);
    }
}
