package ui;


import debugging.LogTypes;
import debugging.Logger;
import ui.pages.Page;
import ui.pages.PageFactory;
import ui.pages.ProfilePickerPage;
import javax.swing.*;
import java.awt.*;

public class Display extends JFrame  {

    private static Display instance = null;

    private static Display getInstance(){
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

        PageFactory.initializePages(this);

        ProfilePickerPage profilePickerPage = (ProfilePickerPage) PageFactory.getPage(PageFactory.PROFILEPICKERPAGE);
        Dimension d = profilePickerPage.getPreferredSize();

        d.width = (140 + 10)*7 + 100;
        setMinimumSize(d);
        d.height += 700;
        setPreferredSize(d);

        pack();
        setLocationRelativeTo(null);
    }

    public static void setPage( Page newPage ) {
        Display ins = getInstance();
        int index = -1;
        for (int i = 0; i < PageFactory.pageCount(); i++) {
            if (PageFactory.getPage(i).equals(newPage)) {
                index = i;
                break;
            }
        }
        if (index == -1 || ins.currentPageIndex == index) {
            return;
        }
        Page lastPanel = null;
        if (ins.currentPageIndex != -1) {
            lastPanel = PageFactory.getPage(ins.currentPageIndex);
        }
        ins.changePage(lastPanel, newPage, index);
    }

    public static void setPage( int pageIndex ){
        Display ins = getInstance();
        if ( pageIndex == ins.currentPageIndex ){
            return;
        }
        if ( pageIndex < 0 && pageIndex >= PageFactory.pageCount()){
            Logger.log("Failed to find menu " + pageIndex, LogTypes.SOFTERROR);
        }else{
            Page lastPage = null;
            if (ins.currentPageIndex != -1){
                lastPage = PageFactory.getPage(ins.currentPageIndex);
            }
            Page nextPage = PageFactory.getPage(pageIndex);
            ins.changePage(lastPage, nextPage, pageIndex);
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
}
