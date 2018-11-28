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

    private static Display instance = null;

    private HeaderPanel headerPanel;

    public static Display getDisplay(){
        if ( instance == null ){
            instance = new Display();
        }
        return instance;
    }

    public final static int USERPANEL = 0;
    public final static int PREVIEWPANEL = 1;
    public final static int LOGINPANEL = 2;
    private int currentDisplayIndex = -1;

    private JPanel[] menues = new JPanel[3];

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

        menues[0] = new UserPanel();
        UserPanel userpanel = (UserPanel) menues[0];
        Dimension d = userpanel.getPreferredSize();

        d.width = (140 + 10)*6 + 100;
        setMinimumSize(d);
        d.height += 500;
        setPreferredSize(d);

        menues[1] = new PreviewPanel(this);
        PreviewPanel previewPanel = (PreviewPanel) menues[1];

        menues[2] = new LogInPanel();

        previewPanel.setViewPortWidth(getWidth());
        //add(previewPanel);

        setPanel(USERPANEL);
    }

    public void setPanel( JPanel newMenu ){
        int index = -1;
        for (int i = 0; i < menues.length; i++){
            if (menues[i].equals(newMenu)){
                index = i;
                break;
            }
        }
        if (index == -1){
            return;
        }
        Container contentPane = getContentPane();
        contentPane.removeAll();

        contentPane.add(newMenu);
        validate();
        repaint();
        currentDisplayIndex = index;
    }

    public JPanel getPanel( int index ){
        return menues[index];
    }

    public void setPanel( int menuIndex ){
        if ( menuIndex == currentDisplayIndex ){
            return;
        }
        if ( menuIndex < 0 && menuIndex >= menues.length){
            Logger.log("Failed to find menu " + menuIndex, LogTypes.SOFTERROR);
        }else{
            Container contentPane = getContentPane();
            contentPane.removeAll();

            contentPane.add(menues[menuIndex]);
            validate();
            repaint();
            currentDisplayIndex = menuIndex;
        }
    }

    public void displayOnPreview(Categories categories, SortTypes sortType, String name){
        PreviewPanel previewPanel = (PreviewPanel) Display.getDisplay().getPanel(Display.PREVIEWPANEL);
        previewPanel.setDisplayedMedia(categories, sortType);
        setPanel(previewPanel);
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
