package ui.panels;


import debugging.Logger;
import maincomponents.AvMinArm;
import ui.Display;
import user.User;
import user.UserHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInPage extends Page {

    private static final Font HEADER = new Font("Arial", Font.PLAIN, 24);

    private JPanel panel;
    private JButton button;

    protected LogInPage(){
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel logintext = new JLabel("Log in");
        logintext.setFont(HEADER);
        logintext.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel login = getLogIn();
        login.setAlignmentX(Component.CENTER_ALIGNMENT);

        button = new JButton("No account? Sign up");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Display.getInstance().setPage(Page.SIGNUPPAGE);
            }
        });
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(logintext);
        add(login);
        add(Box.createRigidArea(new Dimension(0,10)));
        add(button);
    }

    public JPanel getLogIn(){
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JLabel username = new JLabel("Username");
        JLabel password = new JLabel("Password");

        JTextField usertext = new JTextField();
        JPasswordField passfield = new JPasswordField();

        JPanel grid = new JPanel();
        GroupLayout layout = new GroupLayout(grid);
        grid.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup().
                addComponent(username).addComponent(password));
        hGroup.addGroup(layout.createParallelGroup().
                addComponent(usertext).addComponent(passfield));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(username).addComponent(usertext));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(password).addComponent(passfield));
        layout.setVerticalGroup(vGroup);
        grid.setAlignmentX(Component.CENTER_ALIGNMENT);

        button = new JButton("Log in");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(usertext.getText().isEmpty() || passfield.getPassword().length == 0){
                    JOptionPane.showMessageDialog(new JFrame(),
                            "You have not filled in all the boxes! Try look!");
                }else{
                    User user = UserHandler.getInstance().getUser(usertext.getText(), String.copyValueOf(passfield.getPassword()));
                    if(user == null){
                        JOptionPane.showMessageDialog(new JFrame(),
                                "Password or username is wrong!");
                        passfield.setText("");
                    }else{
                        AvMinArm.user = user;
                        Logger.log("User with name: " + usertext.getText() + " logged in!");
                        usertext.setText("");
                        passfield.setText("");
                        Display.getInstance().setPage(Page.USERPAGE);
                        //TODO: GO TO USERPAGE
                    }
                }
            }
        });
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(grid);
        panel.add(button);

        return panel;
    }
}
