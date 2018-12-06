package ui.panels;

import debugging.Logger;
import maincomponents.AvMinArm;
import ui.Display;
import ui.StyleArchive;
import ui.cards.CanvasCard;
import user.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateProfilePage extends Page {

    private JPanel panel, canvas, grid, create, pictureinfo;
    private JButton button;
    private JLabel profiletext, profilename, profileage, profilepicture;
    private JTextField nametext, agetext;
    private CanvasCard picture;

    public CreateProfilePage(){
        super();

        canvas = new JPanel();
        canvas.setLayout(new BoxLayout(canvas, BoxLayout.Y_AXIS));

        profiletext = new JLabel("New Profile");
        profiletext.setFont(StyleArchive.HEADER);
        profiletext.setAlignmentX(Component.CENTER_ALIGNMENT);
        canvas.add(profiletext);

        create = getCreate();
        create.setAlignmentX(Component.CENTER_ALIGNMENT);
        canvas.add(create);


        button = new JButton("Back to profiles");
        button.setFont(StyleArchive.SMALL_BUTTON);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
                Display.getInstance().setPage(Page.USERPAGE);
            }
        });
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        canvas.add(Box.createRigidArea(new Dimension(0,10)));
        canvas.add(button);
        canvas.setBackground(StyleArchive.COLOR_BACKGROUND);

        setBackground(StyleArchive.COLOR_BACKGROUND);
        setLayout(new GridBagLayout());
        add(canvas);
    }

    public void resetFields(){
        picture.reset();
        nametext.setText("");
        agetext.setText("");
    }

    public JPanel getCreate(){
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(300,340));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(StyleArchive.COLOR_BACKGROUND);

        profilename = new JLabel("Profile name:");
        profilename.setFont(StyleArchive.NORMAL);
        profileage = new JLabel("Age:");
        profileage.setFont(StyleArchive.NORMAL);

        profilepicture = new JLabel("Picture:");
        profilepicture.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilepicture.setFont(StyleArchive.NORMAL);

        nametext = new JTextField();
        agetext = new JTextField();

        picture = new CanvasCard();

        grid = new JPanel();
        GroupLayout layout = new GroupLayout(grid);
        grid.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup().
                addComponent(profilename).addComponent(profileage));
        hGroup.addGroup(layout.createParallelGroup().
                addComponent(nametext).addComponent(agetext));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(profilename).addComponent(nametext));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(profileage).addComponent(agetext));
        layout.setVerticalGroup(vGroup);
        grid.setAlignmentX(Component.CENTER_ALIGNMENT);
        grid.setBackground(StyleArchive.COLOR_BACKGROUND);
        panel.add(grid);

        pictureinfo = new JPanel();
        pictureinfo.setLayout(new BoxLayout(pictureinfo, BoxLayout.Y_AXIS));
        pictureinfo.add(profilepicture);
        pictureinfo.add(Box.createRigidArea(new Dimension(0,10)));
        pictureinfo.add(picture);
        pictureinfo.setBackground(StyleArchive.COLOR_BACKGROUND);
        panel.add(pictureinfo);

        button = new JButton("Create");
        button.setFont(StyleArchive.SMALL_BUTTON);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nametext.getText().isEmpty() || agetext.getText().isEmpty()){
                    JOptionPane.showMessageDialog(new JFrame(),
                            "You have not filled in all the boxes! Try look!");
                }else{
                    if(AvMinArm.user.hasProfile(nametext.getText())){
                        JOptionPane.showMessageDialog(new JFrame(),
                                "You already have a profile with that name!");
                    }else{
                        if(!isNumber(agetext.getText())){
                            JOptionPane.showMessageDialog(new JFrame(),
                                    "Age has to be a number!");
                        }else {
                            if(picture.getSelected() == null){
                                JOptionPane.showMessageDialog(new JFrame(),
                                        "No picture chosen!");
                            }else{
                                Logger.log("New profile " + nametext.getText() + " for user: " + AvMinArm.user.getUsername());
                                Profile profile = new Profile(nametext.getText(), Integer.parseInt(agetext.getText()), picture.getSelected());

                                AvMinArm.user.signUpProfile(profile);

                                UserPage userpage = (UserPage) Page.getPage(Page.USERPAGE);
                                userpage.updateUsers();
                                resetFields();
                                Display.getInstance().setPage(userpage);
                            }
                        }
                    }
                }
            }
        });
        button.setFont(StyleArchive.NORMAL);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(button);

        return panel;
    }

    public static boolean isNumber(String s){
        try {
            Integer.parseInt(s);
            return true;
        }catch (Exception e){return false;}
    }
}
