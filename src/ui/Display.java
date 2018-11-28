package ui;


import debugging.LogTypes;
import debugging.Logger;
import medias.*;
import ui.panels.LogInPanel;
import ui.panels.PreviewPanel;
import ui.panels.UserPanel;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame  {

    public static void main(String[] args){
        Display.getDisplay();
    }

    private static Display instance = null;

    private HeaderPanel headerPanel;

    public static Display getDisplay(){
        if ( instance == null ){
            instance = new Display();
        }
        return instance;
    }

    public final static int PREVIEWMENU = 0;
    private int currentDisplayIndex = 0;

    private JPanel[] menues = new JPanel[1];

    private Display (){
        super("ITUStreaming");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        headerPanel = new HeaderPanel(this);

        initializeDisplay();

        pack();
        setLocationRelativeTo(null);
    }

    private void initializeDisplay() {

        //TODO:
        //menues[0] = new PreviewPanel(this);
        //PreviewPanel previewPanel = (PreviewPanel) menues[0];
        //Dimension d = previewPanel.getPreferredSize();

        menues[0] = new UserPanel();
        UserPanel userpanel = (UserPanel) menues[0];
        Dimension d = userpanel.getPreferredSize();

        d.width = (140 + 10)*6 + 100;
        setMinimumSize(d);
        d.height += 500;
        setPreferredSize(d);

        //previewPanel.setViewPortWidth(getWidth());
        //add(previewPanel);

        add(userpanel);
    }

    public void setMenu( int menuIndex ){
        if ( menuIndex == currentDisplayIndex ){
            return;
        }
        if ( menuIndex < 0 && menuIndex >= menues.length){
            Logger.log("Failed to find menu " + menuIndex, LogTypes.SOFTERROR);
        }else{
            getContentPane().removeAll();
            add(menues[0]);
            pack();
            currentDisplayIndex = menuIndex;
        }
    }

    public void displayOnPreview(Categories categories, SortTypes sortType, String name){
        setMenu(PREVIEWMENU);
        PreviewPanel preview = (PreviewPanel) menues[PREVIEWMENU];
        preview.setDisplayedMedia(categories, sortType);
        revalidate();
        repaint();
    }

    public HeaderPanel getHeaderPanel(){
        return headerPanel;
    }

    private void initEksFuckingDee(){
        setLayout(new BorderLayout());
        LogInPanel panel = new LogInPanel();
        add(panel, BorderLayout.CENTER);
    }
}
