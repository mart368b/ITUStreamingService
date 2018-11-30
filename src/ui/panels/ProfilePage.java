package ui.panels;

import maincomponents.AvMinArm;
import ui.Display;
import ui.cards.ProfileCard;
import maincomponents.PictureHandler;
import user.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ProfilePage extends Page {

    private static final Font font = new Font("Arial", Font.PLAIN, 24);

    private String selected = "";
    private JButton button;
    private ProfileCard pic;
    private JLabel nametext, agetext, picturetext;
    private Random rand = new Random();

    public ProfilePage(){
        super();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        pic = new ProfileCard("create", 256);
        pic.setBorderPainted(false);
        pic.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(pic);
        add(getInformation());
        setSize(new Dimension(400,400));
    }
    private String a = "";

    public void open(Profile profile){
        selected = AvMinArm.profile.getProfilePicture();
        String profilePicName = AvMinArm.profile.getProfilePicture();
        pic.setPicture(profilePicName, 256);
        nametext.setText(AvMinArm.profile.getName());
        agetext.setText(Integer.toString(AvMinArm.profile.getAge()));
        picturetext.setText(AvMinArm.profile.getProfilePicture().toString());
        validate();
    }

    public JPanel getInformation(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Information:");
        label.setFont(font);
        panel.add(label);

        JLabel name = new JLabel("Profile name:");
        nametext = new JLabel("");
        JTextField namechange = new JTextField(20);
        namechange.setMaximumSize(new Dimension(230,0));
        JButton namebutton = new JButton("Change");
        namebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(namechange.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Please insert a new name!");
                }else{
                    if(AvMinArm.user.hasProfile(namechange.getText())){
                        JOptionPane.showMessageDialog(new JFrame(),
                                "Name already taken by anotehr profile!");
                    }else{
                        AvMinArm.profile.setName(namechange.getText());

                        ProfilePage profilePage = (ProfilePage) Page.getPage(Page.PROFILEPAGE);
                        profilePage.open(AvMinArm.profile);
                    }
                }
            }
        });

        JLabel age = new JLabel("Profile age:");
        agetext = new JLabel("");
        JTextField agechange = new JTextField();
        agechange.setMaximumSize(new Dimension(230,0));
        JButton agebutton = new JButton("Change");
        agebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(agechange.getText().isEmpty()){
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Please insert a new age!");
                }else{
                    if(!isNumber(agechange.getText())){
                        JOptionPane.showMessageDialog(new JFrame(),
                                "Age has to be a number!");
                    }else{
                        AvMinArm.profile.setAge(Integer.parseInt(agechange.getText()));

                        ProfilePage profilePage = (ProfilePage) Page.getPage(Page.PROFILEPAGE);
                        profilePage.open(AvMinArm.profile);
                    }
                }
            }
        });

        JLabel picture = new JLabel("Profile picture:");
        picturetext = new JLabel("");
        Dimension d = new Dimension(100,20);
        picturetext.setMinimumSize(d);
        picturetext.setPreferredSize(d);

        JPanel picturechange = getPics();
        JButton picturebutton = new JButton("Change");
        picturebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AvMinArm.profile.setPicture(selected);
                ProfilePage profilePage = (ProfilePage) Page.getPage(Page.PROFILEPAGE);
                profilePage.open(AvMinArm.profile);
            }
        });

        JPanel grid = new JPanel();
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
                .addComponent(namechange).addComponent(agechange).addComponent(picturechange));
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(namebutton).addComponent(agebutton).addComponent(picturebutton));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(name).addComponent(nametext).addComponent(namechange).addComponent(namebutton));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(age).addComponent(agetext).addComponent(agechange).addComponent(agebutton));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(picture).addComponent(picturetext).addComponent(picturechange).addComponent(picturebutton));
        layout.setVerticalGroup(vGroup);
        grid.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(grid);

        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserPage userpage = (UserPage) Page.getPage(Page.USERPAGE);
                userpage.updateUsers();
                Display.getInstance().setPage(userpage);
            }
        });

        JButton delete = new JButton("Delete this profile");
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
        panel.add(delete);

        return panel;
    }

    public JPanel getPics(){
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
