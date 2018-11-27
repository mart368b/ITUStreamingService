package ui;

import ui.panels.PreviewPanel;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame {

    public static void main(String[] args){
        Display.getDisplay();
    }

    private static Display instance = null;

    public static Display getDisplay(){
        if ( instance == null ){
            instance = new Display();
        }
        return instance;
    }

    public final static int PREVIEWMENU = 0;

    private Display (){
        super("ITUStreaming");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        initializeBody();

        pack();
    }

    private void initializeBody() {
        setLayout(new BorderLayout());

        HeaderPanel headerPanel = new HeaderPanel(this);
        add(headerPanel, BorderLayout.PAGE_START);

        PreviewPanel previewPanel = new PreviewPanel();
        add(previewPanel, BorderLayout.CENTER);
        Dimension d = headerPanel.getPreferredSize();
        d.width += 100;
        this.setMinimumSize(d);
    }
}
