package ui.panels;


import debugging.Logger;
import maincomponents.AvMinArm;
import ui.Display;
import ui.StyleArchive;
import user.User;
import user.UserHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInPage extends Page {

    private JPanel panel, canvas, login, grid;
    private JButton button;
    private JLabel logintext, username, password;
    private JTextField usertext;
    private JPasswordField passfield;

    protected LogInPage(){
        super();
        setBackground(StyleArchive.COLOR_BACKGROUND);

        canvas = new JPanel();
        canvas.setLayout(new BoxLayout(canvas, BoxLayout.Y_AXIS));

        logintext = new JLabel("Log in");
        logintext.setFont(StyleArchive.HEADER);
        logintext.setAlignmentX(Component.CENTER_ALIGNMENT);

        login = getLogIn();
        login.setAlignmentX(Component.CENTER_ALIGNMENT);

        button = new JButton("No account? Sign up");
        button.setFont(StyleArchive.SMALL_BUTTON);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Display.setPage(Page.SIGNUPPAGE);
            }
        });
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        canvas.add(logintext);
        canvas.add(login);
        canvas.add(Box.createRigidArea(new Dimension(0,10)));
        canvas.add(button);
        canvas.setBackground(StyleArchive.COLOR_BACKGROUND);

        setLayout(new GridBagLayout());
        add(canvas);
    }

    public JPanel getLogIn(){
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(300,200));

        username = new JLabel("Username:");
        username.setFont(StyleArchive.NORMAL);
        password = new JLabel("Password:");
        password.setFont(StyleArchive.NORMAL);

        usertext = new JTextField(300);
        passfield = new JPasswordField(300);

        grid = new JPanel();
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
        button.setFont(StyleArchive.SMALL_BUTTON);
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
                        UserPage userpage = (UserPage) Page.getPage(Page.USERPAGE);
                        userpage.updateUsers();
                        Display.setPage(userpage);
                    }
                }
            }
        });
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        grid.setBackground(StyleArchive.COLOR_BACKGROUND);

        panel.add(grid);
        panel.add(button);
        panel.setBackground(StyleArchive.COLOR_BACKGROUND);


        //TODO
        // YOU SHOULD NEVER DO THIS IN A REAL PROJECT!!!!
        usertext.setText("root");
        passfield.setText("admin");

        return panel;
    }
}
