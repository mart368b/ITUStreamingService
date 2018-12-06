package ui.panels;

import maincomponents.AvMinArm;
import reader.MediaHandler;
import ui.Display;
import ui.StyleArchive;
import ui.cards.HeaderCard;
import ui.cards.ProfileCard;
import user.Profile;
import user.User;

import javax.swing.*;
import javax.swing.text.Style;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserPage extends Page {

    private JPanel panel, canvas, comp, p;
    private JPanel userprofiles = new JPanel();
    private JLabel label, profiletext;
    private JButton button, admin, profilebutton;

    protected UserPage(){
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
                Display.getInstance().setPage(Page.LOGINPAGE);
            }
        });

        canvas.add(button);

        admin = new JButton("Admin");
        admin.setFont(StyleArchive.SMALL_BUTTON);
        admin.setAlignmentX(Component.CENTER_ALIGNMENT);
        canvas.add(Box.createRigidArea(new Dimension(0, 5)));
        canvas.add(admin);
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
                    Display.getInstance().setPage(Page.ADMINPAGE);
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
                AvMinArm.profile = profile;
                HeaderCard.getInstance().setProfilePicture(profile);
                MediaHandler.getInstance().updateMediaCards(profile.getFavorites());
                Display.getInstance().setPage(Page.PREVIEWPAGE);
            }
        });
        p.add(profilebutton);

        p.add(Box.createRigidArea(new Dimension(0,10)));

        profilebutton = new JButton("Profile");
        profilebutton.setFont(StyleArchive.SMALL_BUTTON);
        profilebutton.setAlignmentX(Component.CENTER_ALIGNMENT);

        profilebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AvMinArm.profile = profile;
                ProfilePage profilePage = (ProfilePage) Page.getPage(Page.PROFILEPAGE);
                profilePage.open();
                Display.getInstance().setPage(profilePage);
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
                Display.getInstance().setPage(Page.CREATEPROFILEPAGE);
            }
        });

        p.add(profilebutton);
        p.add(profiletext);
        p.add(Box.createRigidArea(new Dimension(0,50)));
        return p;
    }
}
