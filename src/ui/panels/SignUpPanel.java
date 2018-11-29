package ui.panels;

import debugging.Logger;
import ui.Display;
import user.UserHandler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpPanel extends JPanel {

    private static final Font HEADER = new Font("Arial", Font.PLAIN, 24);

    private JPanel panel;
    private JButton button;

    public SignUpPanel(){
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel logintext = new JLabel("Sign up");
        logintext.setFont(HEADER);
        logintext.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel login = getSignUp();
        login.setAlignmentX(Component.CENTER_ALIGNMENT);

        button = new JButton("Back to login");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Display.getDisplay().setPanel(Display.LOGINPANEL);
            }
        });
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(logintext);
        add(login);
        add(Box.createRigidArea(new Dimension(0,10)));
        add(button);
    }

    public JPanel getSignUp(){
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel username = new JLabel("Username");
        JLabel password = new JLabel("Password");
        JLabel conpassword = new JLabel("Confirm Password");

        JTextField usertext = new JTextField();
        JPasswordField passtext = new JPasswordField();
        JPasswordField conpasstext = new JPasswordField();

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
        grid.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(grid);

        button = new JButton("Sign up and Log in");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(usertext.getText().isEmpty() || passtext.getPassword().length == 0 || conpasstext.getPassword().length == 0){
                    JOptionPane.showMessageDialog(new JFrame(),
                            "You have not filled in all the boxes! Try look!");
                }else{
                    if(!String.copyValueOf(passtext.getPassword()).equals(String.copyValueOf(conpasstext.getPassword()))){
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
                            UserHandler.getInstance().signUpUser(usertext.getText(), String.copyValueOf(passtext.getPassword()));
                            Logger.log("New user created with name: " + usertext.getText());
                            Display.getDisplay().setPanel(Display.USERPANEL);
                        }
                    }
                }
            }
        });
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(button);

        return panel;
    }

}
