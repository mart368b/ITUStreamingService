package ui.pages;

import debugging.Exceptions.InvalidInputException;
import debugging.Logger;
import maincomponents.AvMinArm;
import maincomponents.controllers.UserController;
import ui.Display;
import ui.StyleArchive;
import user.UserHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpPage extends Page {

    private JPanel panel, canvas, login, grid;
    private JButton button;
    private JLabel logintext, username, password, conpassword;
    private JTextField usertext;
    private JPasswordField passtext, conpasstext;

    public SignUpPage(){
        super();

        canvas = new JPanel();
        canvas.setLayout(new BoxLayout(canvas, BoxLayout.Y_AXIS));

        logintext = new JLabel("Sign up");
        logintext.setFont(StyleArchive.HEADER);
        logintext.setAlignmentX(Component.CENTER_ALIGNMENT);

        login = getSignUp();
        login.setAlignmentX(Component.CENTER_ALIGNMENT);

        button = new JButton("Back to login");
        button.setFont(StyleArchive.SMALL_BUTTON);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Display.setPage(PageHandler.LOGINPAGE);
            }
        });
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        canvas.add(logintext);
        canvas.add(login);
        canvas.add(Box.createRigidArea(new Dimension(0,10)));
        canvas.add(button);
        canvas.setBackground(StyleArchive.COLOR_BACKGROUND);

        setBackground(StyleArchive.COLOR_BACKGROUND);
        setLayout(new GridBagLayout());
        add(canvas);
    }

    public JPanel getSignUp(){
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(300,200));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        username = new JLabel("Username:");
        username.setFont(StyleArchive.NORMAL);
        password = new JLabel("Password:");
        password.setFont(StyleArchive.NORMAL);
        conpassword = new JLabel("Confirm Password:");
        conpassword.setFont(StyleArchive.NORMAL);

        usertext = new JTextField();
        passtext = new JPasswordField();
        conpasstext = new JPasswordField();

        grid = new JPanel();
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
        grid.setBackground(StyleArchive.COLOR_BACKGROUND);
        panel.add(grid);
        panel.setBackground(StyleArchive.COLOR_BACKGROUND);

        button = new JButton("Sign up and Log in");
        button.setFont(StyleArchive.SMALL_BUTTON);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //usertext.text passtext.pass conpass.pass
                String name = usertext.getText();
                String kode = String.copyValueOf(passtext.getPassword());
                String kodeCheck = String.copyValueOf(conpasstext.getPassword());
                try {
                    UserController.signUpUser(name, kode, kodeCheck);
                } catch (InvalidInputException exc){
                    JOptionPane.showMessageDialog(new JFrame(),exc.getMessage());
                }
            }
        });
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(button);

        return panel;
    }

    @Override
    public void addToDisplay(Display display){
        super.addToDisplay(display);
        usertext.setText("");
        passtext.setText("");
        conpasstext.setText("");
    }

}
