package ui.panels;


import javax.swing.*;
import java.awt.*;

public class LogInPanel extends JPanel {

    public LogInPanel(){
        super();
        setLayout(new BorderLayout());

        add(getLogIn(), BorderLayout.LINE_START);
        add(getSignUp(), BorderLayout.LINE_END);
    }

    public JPanel getLogIn(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        return panel;
    }

    public JPanel getSignUp(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);
        return panel;
    }
}
