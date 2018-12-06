package ui.panels;

import maincomponents.AvMinArm;
import ui.Display;
import ui.StyleArchive;
import ui.cards.CanvasCard;
import ui.cards.ProfileCard;
import maincomponents.ImageHandler;
import ui.components.ImageButton;
import user.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class ProfilePage extends Page {

    private JPanel canvas, panel, grid, buttons, wrap;
    private ProfileCard pic;
    private JButton commit, back, delete;
    private JLabel nametext, agetext, picturetext, label, name, age, picture;
    private JTextField namechange, agechange;
    public CanvasCard selected;

    public ProfilePage(){
        super();
        setLayout(new GridBagLayout());

        wrap = new JPanel();
        wrap.setLayout(new BoxLayout(wrap, BoxLayout.Y_AXIS));

        label = new JLabel("Information");
        label.setFont(StyleArchive.HEADER);
        wrap.add(label);

        canvas = new JPanel();
        canvas.setLayout(new BoxLayout(canvas, BoxLayout.X_AXIS));
        pic = new ProfileCard();
        pic.setPreferredSize(new Dimension(256,256));
        pic.setBorderPainted(false);
        pic.setAlignmentX(Component.CENTER_ALIGNMENT);
        canvas.add(pic);
        canvas.add(getInformation());
        canvas.setBackground(StyleArchive.COLOR_BACKGROUND);
        wrap.add(canvas);
        wrap.setBackground(StyleArchive.COLOR_BACKGROUND);

        setBackground(StyleArchive.COLOR_BACKGROUND);
        add(wrap);
    }

    public JPanel getInformation(){
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(StyleArchive.COLOR_BACKGROUND);

        name = new JLabel("Profile name:");
        name.setFont(StyleArchive.NORMAL);
        nametext = new JLabel();
        nametext.setFont(StyleArchive.NORMAL);
        namechange = new JTextField();

        age = new JLabel("Profile age:");
        age.setFont(StyleArchive.NORMAL);
        agetext = new JLabel();
        agetext.setFont(StyleArchive.NORMAL);
        agechange = new JTextField();

        picture = new JLabel("Profile picture:");
        picture.setFont(StyleArchive.NORMAL);
        picturetext = new JLabel();

        selected = new CanvasCard();

        grid = new JPanel();
        GroupLayout layout = new GroupLayout(grid);
        grid.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(name).addComponent(age).addComponent(picture));
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(nametext).addComponent(agetext).addComponent(picturetext));
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(namechange).addComponent(agechange).addComponent(selected));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(name).addComponent(nametext).addComponent(namechange));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(age).addComponent(agetext).addComponent(agechange));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(picture).addComponent(picturetext).addComponent(selected));
        layout.setVerticalGroup(vGroup);
        grid.setAlignmentX(Component.CENTER_ALIGNMENT);
        grid.setBackground(StyleArchive.COLOR_BACKGROUND);
        panel.add(grid);

        buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        commit = new JButton("Commit changes");
        commit.setFont(StyleArchive.SMALL_BUTTON);
        commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!namechange.getText().isEmpty()) {
                    if (AvMinArm.user.hasProfile(namechange.getText())) {
                        JOptionPane.showMessageDialog(new JFrame(),
                                "Name already taken by another profile!");
                    } else {
                        AvMinArm.profile.setName(namechange.getText());
                    }
                }
                if(!agechange.getText().isEmpty()){
                    if(!isNumber(agechange.getText())){
                        JOptionPane.showMessageDialog(new JFrame(),
                                "Age has to be a number!");
                    }else{
                        AvMinArm.profile.setAge(Integer.parseInt(agechange.getText()));
                    }
                }
                if (selected.getSelected() != null){
                    AvMinArm.profile.setPicture(selected.getSelected());
                }

                namechange.setText("");
                agechange.setText("");
                ProfilePage profilePage = (ProfilePage) Page.getPage(Page.PROFILEPAGE);
                profilePage.open();
            }
        });

        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        back = new JButton("Back");
        back.setFont(StyleArchive.SMALL_BUTTON);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserPage userpage = (UserPage) Page.getPage(Page.USERPAGE);
                userpage.updateUsers();
                Display.getInstance().setPage(userpage);
            }
        });

        delete = new JButton("Delete this profile");
        delete.setFont(StyleArchive.SMALL_BUTTON);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int answer = JOptionPane.showConfirmDialog(new JFrame(),
                        "Are you sure you want to delete this profile?",
                        "Are you sure?",
                        JOptionPane.YES_NO_OPTION);
                if(answer == JOptionPane.YES_OPTION){
                    AvMinArm.user.removeProfile(AvMinArm.profile);

                    UserPage userpage = (UserPage) Page.getPage(Page.USERPAGE);
                    userpage.updateUsers();
                    Display.getInstance().setPage(userpage);
                }
            }
        });
        buttons.add(commit);
        buttons.add(Box.createRigidArea(new Dimension(20,0)));
        buttons.add(delete);
        buttons.add(Box.createRigidArea(new Dimension(20,0)));
        buttons.add(back);
        buttons.setBackground(StyleArchive.COLOR_BACKGROUND);
        panel.add(buttons);

        return panel;
    }

    public void open(){
        String profilePicName = AvMinArm.profile.getProfilePictureName();
        pic.setPicture(profilePicName, 256);
        nametext.setText(AvMinArm.profile.getName());
        agetext.setText(Integer.toString(AvMinArm.profile.getAge()));
        picturetext.setText("");
        validate();
        repaint();
    }

    public static boolean isNumber(String s){
        try {
            Integer.parseInt(s);
            return true;
        }catch (Exception e){return false;}
    }
}
