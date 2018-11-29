package ui.panels;

import debugging.Logger;
import javafx.print.PageLayout;
import maincomponents.AvMinArm;
import ui.Display;
import ui.cards.ProfileCard;
import user.PictureHandler;
import user.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateProfilePage extends Page {

    private static final Font HEADER = new Font("Arial", Font.PLAIN, 24);
    private static final Font HUGE = new Font("Arial", Font.PLAIN, 28);

    private String selected = "default-orange";
    private JPanel panel;
    private JButton button;

    public CreateProfilePage(){
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel profiletext = new JLabel("New Profile");
        profiletext.setFont(HEADER);
        profiletext.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(profiletext);

        JPanel create = getCreate();
        create.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(create);


        button = new JButton("Back to profiles");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Display.getInstance().setPage(Page.USERPAGE);
            }
        });
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0,10)));
        add(button);
    }

    public JPanel getCreate(){
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel profilename = new JLabel("Profile name");
        JLabel profileage = new JLabel("Age");
        JLabel profilepicture = new JLabel("Picture");

        JTextField nametext = new JTextField();
        JTextField agetext = new JTextField();
        JPanel pictures = getPics();

        JPanel grid = new JPanel();
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
        panel.add(grid);

        JLabel label = new JLabel("Choose profile picture");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
        panel.add(getPics());

        button = new JButton("Create");
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
                            Logger.log("New profile " + nametext.getText() + " for user: " + AvMinArm.user.getUsername());
                            Profile profile = new Profile(nametext.getText(), Integer.parseInt(agetext.getText()), selected, new String[]{});

                            AvMinArm.user.signUpProfile(profile);

                            UserPage userpage = (UserPage) Page.getPage(Page.USERPAGE);
                            userpage.updateUsers();
                            Display.getInstance().setPage(userpage);
                        }
                    }
                }
            }
        });
        button.setFont(HUGE);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(button);

        return panel;
    }

    private JPanel getPics(){
        int rowL = PictureHandler.types.length;
        int colL = PictureHandler.colors.length;
        JButton[][] buttons = new JButton[rowL][colL];
        for(int row = 0; row < rowL; row++){
            for(int col = 0; col < colL; col++) {
                String pictureName = PictureHandler.types[row] + "-" + PictureHandler.colors[col];
                button = new ProfileCard(pictureName, 34);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selected = pictureName;
                    }
                });
                buttons[row][col] = button;
            }
        }

        JPanel grid = new JPanel();
        GroupLayout layout = new GroupLayout(grid);
        grid.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        for(int col = 0; col < colL; col++){
            GroupLayout.ParallelGroup parGroup = layout.createParallelGroup();
            for(int row = 0; row < rowL; row++) {
                parGroup.addComponent(buttons[row][col]);
            }
            hGroup.addGroup(parGroup);
        }
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        for(int row = 0; row < rowL; row++) {
            GroupLayout.ParallelGroup parGroup = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
            for(int col = 0; col < colL; col++){
                parGroup.addComponent(buttons[row][col]);
            }
            vGroup.addGroup(parGroup);
        }
        layout.setVerticalGroup(vGroup);
        grid.setAlignmentX(Component.CENTER_ALIGNMENT);

        return grid;
    }

    public static boolean isNumber(String s){
        try {
            Integer.parseInt(s);
            return true;
        }catch (Exception e){return false;}
    }
}
