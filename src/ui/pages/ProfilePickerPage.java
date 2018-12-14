package ui.pages;

import maincomponents.AvMinArm;
import maincomponents.controllers.ProfileController;
import maincomponents.controllers.UserController;
import reader.MediaHandler;
import ui.Display;
import ui.StyleArchive;
import ui.cards.ProfileCard;
import user.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProfilePickerPage extends Page {

    private JPanel panel, canvas, comp, p;
    private JPanel userprofiles = new JPanel();
    private JLabel label, profiletext;
    private JButton button, admin, delete, profilebutton;

    protected ProfilePickerPage(){
        super();

        canvas = new JPanel();
        canvas.setLayout(new BoxLayout(canvas, BoxLayout.Y_AXIS));

        label = new JLabel("Choose profile");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(StyleArchive.HEADER);


        canvas.add(label);
        canvas.add(Box.createRigidArea(new Dimension(0, 20)));
        updateUsers();
        canvas.add(userprofiles);
        canvas.add(Box.createRigidArea(new Dimension(0, 20)));

        button = new JButton("Sign out");
        button.setFont(StyleArchive.SMALL_BUTTON);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AvMinArm.user = null;
                Display.setPage(PageFactory.LOGINPAGE);
            }
        });

        canvas.add(button);

        admin = new JButton("Admin");
        admin.setFont(StyleArchive.SMALL_BUTTON);
        admin.setAlignmentX(Component.CENTER_ALIGNMENT);
        canvas.add(Box.createRigidArea(new Dimension(0, 5)));
        canvas.add(admin);

        delete = new JButton("Delete user");
        delete.setFont(StyleArchive.SMALL_BUTTON);
        delete.setAlignmentX(Component.CENTER_ALIGNMENT);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserController.deleteUser();
            }
        });
        canvas.add(Box.createRigidArea(new Dimension(0,5)));
        canvas.add(delete);

        canvas.setBackground(StyleArchive.COLOR_BACKGROUND);

        setLayout(new GridBagLayout());
        setBackground(StyleArchive.COLOR_BACKGROUND);
        add(canvas);
    }

    public void updateUsers(){
        userprofiles.removeAll();
        if(AvMinArm.user == null) return;
        ArrayList<Profile> profiles = AvMinArm.user.getProfiles();

        if(AvMinArm.user.isAdmin()){
            admin.setVisible(true);
            admin.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Display.setPage(PageFactory.ADMINPAGE);
                }
            });
        }else{
            admin.setVisible(false);
        }

        userprofiles.setLayout(new BoxLayout(userprofiles,BoxLayout.LINE_AXIS));
        userprofiles.setBackground(StyleArchive.COLOR_BACKGROUND);

        userprofiles.add(Box.createRigidArea(new Dimension(60, 0)));
        for(Profile profile : AvMinArm.user.getProfiles()){
            comp = getProfile(profile);
            comp.setAlignmentX(Component.CENTER_ALIGNMENT);
            userprofiles.add(comp);
            userprofiles.add(Box.createRigidArea(new Dimension(60, 0)));
        }
        if(profiles.size() != 5){
            comp = getCreate();
            comp.setAlignmentX(Component.CENTER_ALIGNMENT);
            userprofiles.add(comp);
            userprofiles.add(Box.createRigidArea(new Dimension(60, 0)));
        }
        userprofiles.add(Box.createRigidArea(new Dimension(0,80)));
        validate();
        repaint();
    }

    private JPanel getProfile(Profile profile){
        p = new JPanel();
        p.setBackground(StyleArchive.COLOR_BACKGROUND);
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

        profiletext = new JLabel(profile.getName());
        profiletext.setFont(StyleArchive.NORMAL);
        profiletext.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilebutton = new ProfileCard(profile);
        profilebutton.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilebutton.setPreferredSize(new Dimension(128,128));

        profilebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProfileController.setProfile(profile);
            }
        });
        p.add(profilebutton);

        p.add(Box.createRigidArea(new Dimension(0,10)));

        profilebutton = new JButton("Update");
        profilebutton.setFont(StyleArchive.SMALL_BUTTON);
        profilebutton.setAlignmentX(Component.CENTER_ALIGNMENT);

        profilebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AvMinArm.profile = profile;
                ProfileChangePage profilePage = (ProfileChangePage) PageFactory.getPage(PageFactory.CHANGEPROFILEPAGE);
                profilePage.open();
                Display.setPage(profilePage);
            }
        });
        p.add(profilebutton);
        p.add(Box.createRigidArea(new Dimension(0,10)));
        p.add(profiletext);
        return p;
    }

    private JPanel getCreate(){
        p = new JPanel();
        p.setBackground(StyleArchive.COLOR_BACKGROUND);
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

        profiletext = new JLabel("New Profile");
        profiletext.setFont(StyleArchive.NORMAL);
        profiletext.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilebutton = new ProfileCard();
        profilebutton.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilebutton.setPreferredSize(new Dimension(128,128));
        profilebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Display.setPage(PageFactory.CREATEPROFILEPAGE);
            }
        });

        p.add(profilebutton);
        p.add(profiletext);
        p.add(Box.createRigidArea(new Dimension(0,50)));
        return p;
    }
}
