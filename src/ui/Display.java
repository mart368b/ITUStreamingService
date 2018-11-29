package ui;


import debugging.LogTypes;
import debugging.Logger;
import medias.*;
import ui.cards.HeaderCard;
import ui.panels.*;
import ui.panels.LogInPage;
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

    public final static int USERPANEL = 0;
    public final static int PREVIEWPANEL = 1;
    public final static int LOGINPANEL = 2;
    public final static int SIGNUPPANEL = 3;
    public final static int CREATEPROFILEPANEL = 4;
    private int currentDisplayIndex = -1;

    private JPanel[] menues = new JPanel[5];
    private int currentPageIndex = -1;

    private Display (){
        super("ITUStreaming");
        HeaderCard.createHeader(this);
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

        menues[1] = new PreviewPage(this);
        PreviewPage previewPanel = (PreviewPage) menues[1];

        menues[2] = new LogInPage();
        menues[3] = new SignUpPanel();
        menues[4] = new CreateProfilePanel();

        previewPanel.setViewPortWidth(getWidth());
        //add(previewPanel);

        setPage(USERPANEL);
    }

    public void updatePanel(int panel){
        if(panel == USERPANEL) menues[panel] = new UserPage();
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
            Page lastPanel = null;
            if (currentPageIndex != -1){
                lastPanel = Page.getPage(currentPageIndex);
            }
            Page nextPage = Page.getPage(pageIndex);
            changePage(lastPanel, nextPage, pageIndex);
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

    public void displayOnPreview(Categories categories, String name){
        PreviewPage previewPanel = (PreviewPage) Page.getPage(Page.PREVIEWPAGE);
        previewPanel.setDisplayedMedia(categories);
        setPage(previewPanel);
    }

    public void displayMedia(Media media){
        MediaPreviewPage mediaPreview = (MediaPreviewPage) Page.getPage(Page.MEDIAPREVIEWPAGE);
        mediaPreview.setMedia(media);
        setPage(Page.MEDIAPREVIEWPAGE);
    }
}
