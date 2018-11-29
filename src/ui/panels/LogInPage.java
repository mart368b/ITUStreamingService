package ui.panels;


import debugging.Logger;
import maincomponents.AvMinArm;
import ui.Display;
import user.User;
import user.UserHandler;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInPage extends Page {

    private static final Font HEADER = new Font("Arial", Font.PLAIN, 24);

    private JPanel panel;
    private JButton button;
    private Border blackline = BorderFactory.createLineBorder(Color.black);

    public LogInPage(){
        super();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        c.gridx = 0;
        c.weightx = 0.2;
        add(new JPanel(), c);

        c.weightx = 1;
        c.gridx = 1;
        add(getLogIn(), c);

        c.gridx = 2;
        c.weightx = 0.2;
        add(new JPanel(), c);

        c.weightx = 1;
        c.gridx = 3;
        add(getSignUp(), c);

        c.gridx = 4;
        c.weightx = 0.2;
        add(new JPanel(), c);

    }

    public JPanel getLogIn(){
        panel = new JPanel();

        JLabel header = new JLabel("Log In");
        header.setFont(HEADER);

        JLabel username = new JLabel("Username");
        JLabel password = new JLabel("Password");

        JTextField usertext = new JTextField();
        JTextField passtext = new JTextField();

        JPanel grid = new JPanel();
        GroupLayout layout = new GroupLayout(grid);
        grid.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup().
                addComponent(username).addComponent(password));
        hGroup.addGroup(layout.createParallelGroup().
                addComponent(usertext).addComponent(passtext));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(username).addComponent(usertext));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(password).addComponent(passtext));
        layout.setVerticalGroup(vGroup);

        button = new JButton("Log in");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(usertext.getText().isEmpty() || passtext.getText().isEmpty()){
                    JOptionPane.showMessageDialog(new JFrame(),
                            "You have not filled in all the boxes! Try look!");
                }else{
                    User user = UserHandler.getInstance().getUser(usertext.getText(), passtext.getText());
                    if(user == null){
                        JOptionPane.showMessageDialog(new JFrame(),
                                "Password or username is wrong!");
                        passtext.setText("");
                    }else{
                        AvMinArm.user = user;
                        Logger.log("User with name: " + usertext.getText() + " logged in!");
                        usertext.setText("");
                        passtext.setText("");
                        Display.getInstance().setPage(Page.USERPAGE);
                        //TODO: GO TO USERPAGE
                    }
                }
            }
        });

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;
        c.weightx = 0.5;
        c.gridy = 0;
        panel.add(header, c);

        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(grid, c);

        c.weightx = 0.5;
        c.gridy = 2;
        panel.add(button, c);

        TitledBorder title = BorderFactory.createTitledBorder(
                blackline, "Already have an account?");
        title.setTitleJustification(TitledBorder.CENTER);
        panel.setBorder(title);

        return panel;
    }

    public JPanel getSignUp(){
        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel header = new JLabel("Sign up");
        header.setFont(HEADER);

        JLabel username = new JLabel("Username");
        JLabel password = new JLabel("Password");
        JLabel conpassword = new JLabel("Confirm Password");

        JTextField usertext = new JTextField();
        JTextField passtext = new JTextField();
        JTextField conpasstext = new JTextField();

        JPanel grid = new JPanel();
        GroupLayout layout = new GroupLayout(grid);
        grid.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup().
                addComponent(username).addComponent(password).addComponent(conpassword));
        hGroup.addGroup(layout.createParallelGroup().
                addComponent(usertext).addComponent(passtext).addComponent(conpasstext));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(username).addComponent(usertext));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(password).addComponent(passtext));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(conpassword).addComponent(conpasstext));
        layout.setVerticalGroup(vGroup);

        button = new JButton("Sign up and Log in");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(usertext.getText().isEmpty() || passtext.getText().isEmpty() || conpasstext.getText().isEmpty()){
                    JOptionPane.showMessageDialog(new JFrame(),
                            "You have not filled in all the boxes! Try look!");
                }else{
                    if(!passtext.getText().equals(conpasstext.getText())){
                        JOptionPane.showMessageDialog(new JFrame(),
                                "Your password and confirmed password are not the same!");
                        passtext.setText("");
                        conpasstext.setText("");
                    }else{
                        if(UserHandler.getInstance().hasUser(usertext.getText())){
                            JOptionPane.showMessageDialog(new JFrame(),
                                    "A user with that name is already created!");
                            passtext.setText("");
                            conpasstext.setText("");
                        }else{
                            UserHandler.getInstance().signUpUser(usertext.getText(), passtext.getText());
                        }
                    }
                }
                Logger.log("New user created with name: " + usertext.getText());
                //TODO: GO TO USERPAGE
            }
        });

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(header, c);

        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(grid, c);

        c.weightx = 0.5;
        c.gridy = 2;
        panel.add(button, c);

        TitledBorder title = BorderFactory.createTitledBorder(
                blackline, "I'm new, and I want to join!");
        title.setTitleJustification(TitledBorder.CENTER);
        panel.setBorder(title);

        return panel;
    }
}
