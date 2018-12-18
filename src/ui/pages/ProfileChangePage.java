package ui.pages;

import debugging.Exceptions.InvalidInputException;
import maincomponents.AvMinArm;
import maincomponents.controllers.ProfileController;
import ui.Display;
import ui.StyleArchive;
import ui.cards.CanvasCard;
import ui.cards.ProfileCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileChangePage extends Page {

    private JPanel canvas, panel, grid, buttons, wrap;
    private ProfileCard pic;
    private JButton commit, back, delete;
    private JLabel nametext, agetext, picturetext, label, name, age, picture;
    private JTextField namechange, agechange;
    public CanvasCard selected;

    protected ProfileChangePage(){
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

    private JPanel getInformation(){
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
        commit = new JButton("Apply changes");
        commit.setFont(StyleArchive.SMALL_BUTTON);
        commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = namechange.getText();
                String age = agechange.getText();
                String imgName = selected.getSelected();
                try {
                    ProfileController.updateProfile(name, age, imgName);
                } catch (InvalidInputException exc){
                    JOptionPane.showMessageDialog(new JFrame(), exc.getMessage());
                }
            }
        });

        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        back = new JButton("Back");
        back.setFont(StyleArchive.SMALL_BUTTON);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProfilePickerPage profilePickerPage = (ProfilePickerPage) PageHandler.getPage(PageHandler.PROFILEPICKERPAGE);
                profilePickerPage.updateUsers();
                Display.setPage(profilePickerPage);
            }
        });

        delete = new JButton("Delete this profile");
        delete.setFont(StyleArchive.SMALL_BUTTON);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProfileController.deleteCurrentProfile();
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
        selected.setSelected(profilePicName);
        update(profilePicName);
    }

    public void update(String profilePicName){
        pic.setPicture(profilePicName, 256);
        nametext.setText(AvMinArm.profile.getName());
        agetext.setText(Integer.toString(AvMinArm.profile.getAge()));
        namechange.setText("");
        agechange.setText("");
        validate();
        repaint();
    }
}
