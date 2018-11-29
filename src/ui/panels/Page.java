package ui.panels;

import ui.Display;

import javax.swing.*;
import java.awt.*;

public abstract class Page extends JPanel {


    public final static int USERPAGE = 0;
    public final static int PREVIEWPAGE = 1;
    public final static int LOGINPAGE = 2;
    public final static int SIGNUPPAGE = 3;
    public final static int CREATEPROFILEPAGE = 4;
    public final static int MEDIAPREVIEWPAGE = 5;

    private static Page[] pages = new Page[6];

    public Page() {
        super();
    }

    public Page(LayoutManager layoutManager) {
        super(layoutManager);
    }

    public static Page getPage(int pageIndex) {
        return pages[pageIndex];
    }

    public void addToDisplay(Display d){
        Container con = d.getContentPane();
        con.add(this);
    }

    public void removeFromDisplaye(Display d){
        Container con = d.getContentPane();
        con.remove(this);
    }

    public static int pageCount(){
        return pages.length;
    }

    public static void initializePages(Display d){
        pages[0] = new UserPage();
        pages[1] = new PreviewPage(d);
        pages[2] = new LogInPage();
        pages[3] = new SignUpPage();
        pages[4] = new CreateProfilePage();
        pages[5] = new MediaPreviewPage();
    }
}
