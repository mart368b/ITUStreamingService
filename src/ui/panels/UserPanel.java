package ui.panels;

import maincomponents.AvMinArm;
import ui.Display;
import ui.ProfileCard;
import user.Profile;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserPanel extends JPanel {

    private JPanel panel;
    private User user = AvMinArm.user;
    private final Font font = new Font("Arial", Font.PLAIN, 24);
    private final Font font2 = new Font("Arial", Font.PLAIN, 18);

    public UserPanel(){
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
                Display.getDisplay().setPanel(Display.LOGINPANEL);
                //TODO: GO TO LOGIN/SIGNUP PAGE
            }
        });

        add(label);
        add(Box.createRigidArea(new Dimension(0, 80)));
        add(getContent());
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(button);
    }

    private JPanel getContent(){
        panel = new JPanel();
        ArrayList<Profile> profiles = user.getProfiles();

        panel.setLayout(new BoxLayout(panel,BoxLayout.LINE_AXIS));

        for(Profile profile : user.getProfiles()){
            JPanel comp = getProfile(profile);
            comp.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(comp);
            panel.add(Box.createRigidArea(new Dimension(60, 0)));
        }
        if(profiles.size() != 5){
            JPanel comp = getCreate();
            comp.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(comp);
        }
        panel.add(Box.createRigidArea(new Dimension(0,80)));

        return panel;
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
                Display.getDisplay().setPanel(Display.PREVIEWPANEL);
                //TODO: GO TO PREVIEWPAGE
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
                //TODO: GO CREATE PROFILEPAGE
            }
        });

        p.add(button);
        p.add(label);
        return p;
    }
}
