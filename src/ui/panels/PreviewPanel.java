package ui.panels;

import javax.swing.*;
import java.awt.*;

public class PreviewPanel extends JPanel {

    public PreviewPanel(){
        super();
        setLayout(new BorderLayout());

        add(getOptionMenu(), BorderLayout.PAGE_START);
        add(getPreviewMenu(), BorderLayout.CENTER);

    }

    private JPanel getPreviewMenu() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLUE);
        return panel;
    }

    private JPanel getOptionMenu(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.RED);
        return panel;
    }
}
