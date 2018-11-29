package ui.panels;

import maincomponents.AvMinArm;
import ui.Display;
import ui.cards.HeaderCard;
import ui.cards.ProfileCard;
import user.Profile;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserPage extends Page {

    private JPanel panel;

    private JPanel userprofiles = new JPanel();

    private final Font font = new Font("Arial", Font.PLAIN, 24);
    private final Font font2 = new Font("Arial", Font.PLAIN, 18);

    public UserPage(){
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Choose profile");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(font);

        JButton button = new JButton("Sign out");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AvMinArm.user = null;
                Display.getInstance().setPage(Page.LOGINPAGE);
                //TODO: GO TO LOGIN/SIGNUP PAGE
            }
        });

        add(label);
        add(Box.createRigidArea(new Dimension(0, 80)));
        updateUsers();
        add(userprofiles);
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(button);
    }

    public void updateUsers(){
        userprofiles.removeAll();
        ArrayList<Profile> profiles = AvMinArm.user.getProfiles();

        userprofiles.setLayout(new BoxLayout(userprofiles,BoxLayout.LINE_AXIS));

        for(Profile profile : AvMinArm.user.getProfiles()){
            JPanel comp = getProfile(profile);
            comp.setAlignmentX(Component.CENTER_ALIGNMENT);
            userprofiles.add(comp);
            userprofiles.add(Box.createRigidArea(new Dimension(60, 0)));
        }
        if(profiles.size() != 5){
            JPanel comp = getCreate();
            comp.setAlignmentX(Component.CENTER_ALIGNMENT);
            userprofiles.add(comp);
        }
        userprofiles.add(Box.createRigidArea(new Dimension(0,80)));
        validate();
        repaint();
    }

    private JPanel getProfile(Profile profile){
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

        JLabel label = new JLabel(profile.getName());
        label.setFont(font2);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton button = new ProfileCard(profile);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AvMinArm.profile = profile;
                HeaderCard.getInstance().setProfilePicture(profile);
                Display.getInstance().setPage(Page.PREVIEWPAGE);
                //Display.getInstance().setPanel(Display.MEDIAPREVIEW);
            }
        });

        p.add(button);
        p.add(label);
        return p;
    }

    private JPanel getCreate(){
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

        JLabel label = new JLabel("New Profile");
        label.setFont(font2);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton button = new ProfileCard();
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Display.getInstance().setPage(Page.CREATEPROFILEPAGE);
            }
        });

        p.add(button);
        p.add(label);
        return p;
    }
}
