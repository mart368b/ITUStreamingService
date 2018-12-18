package ui.pages;

import ui.Display;

public class PageHandler {
    public final static int PROFILEPICKERPAGE = 0;
    public final static int PREVIEWPAGE = 1;
    public final static int LOGINPAGE = 2;
    public final static int SIGNUPPAGE = 3;
    public final static int CREATEPROFILEPAGE = 4;
    public final static int MEDIAPREVIEWPAGE = 5;
    public final static int CHANGEPROFILEPAGE = 6;
    public final static int MEDIAPLAYERPAGE = 7;
    public final static int ADMINPAGE = 8;

    private static Page[] pages = new Page[9];

    public static int pageCount(){
        return pages.length;
    }
    public static Page getPage(int pageIndex) {
        return pages[pageIndex];
    }

    public static void initializePages(Display d){
        pages[0] = new ProfilePickerPage();
        pages[1] = new PreviewPage(d);
        pages[2] = new LogInPage();
        pages[3] = new SignUpPage();
        pages[4] = new CreateProfilePage();
        pages[5] = new MediaPreviewPage();
        pages[6] = new ProfileChangePage();
        pages[7] = new MediaPlayerPage();
        pages[8] = new AdminPage();
    }


}
