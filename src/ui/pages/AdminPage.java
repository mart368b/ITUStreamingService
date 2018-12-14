package ui.pages;

import debugging.Exceptions.InvalidInputException;
import maincomponents.controllers.AdminController;
import medias.types.AgeTypes;
import medias.types.MediaTypes;
import ui.Display;
import ui.StyleArchive;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPage extends Page {

    private JPanel panel, info, info2, cats, grid, catsc, buttons, file, seasonspanel;
    private JLabel label, title, rate, time, age,  cat, chosen, year, year2, picture, addseries;
    public JLabel filename;

    private JTextField titletext, ratetext, timetext, yeartext, year2text = new JTextField();
    public JTextField addtext;
    private JComboBox combobox, agebox;
    private JButton remove, add, addown, button, back, addepisode, addseasons, removeseasons;
    public JList addlist, removelist, seasonslist;
    private JScrollPane addlistScroller, removelistScroller, seasonsScroller;


    protected AdminPage(){
        super();
        AdminController.init(this);
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        label = new JLabel("Add New Media");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(StyleArchive.HEADER);

        String[] choices = MediaTypes.getLoadedMediaTypes();
        combobox = new JComboBox(choices);
        combobox.setSelectedIndex(0);
        combobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String med = (String)cb.getSelectedItem();
                MediaTypes mediatype = MediaTypes.getTypeFromString(med);
                AdminController.setMediaType(mediatype);
            }
        });

        panel.add(label);
        panel.add(combobox);
        getNormalInformation();
        panel.add(info);
        info2 = new JPanel();
        info2.setBackground(StyleArchive.COLOR_BACKGROUND);

        AdminController.setMediaType(MediaTypes.MOVIE);

        panel.add(info2);
        panel.setBackground(StyleArchive.COLOR_BACKGROUND);

        buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        buttons.setBackground(StyleArchive.COLOR_BACKGROUND);

        button = new JButton("Add media");
        button.setFont(StyleArchive.SMALL_BUTTON);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titletext.getText();
                String rating = ratetext.getText();
                String year = yeartext.getText();
                String year2 = year2text.getText();
                String selected = combobox.getSelectedItem().toString();
                String time = timetext.getText();
                String age = agebox.getSelectedItem().toString();
                try {
                    AdminController.addMedia(title, rating, year, year2, selected, time, age);
                }catch (InvalidInputException exc){
                    JOptionPane.showMessageDialog(new JFrame(),exc.getMessage());
                }
            }
        });
        buttons.add(button);
        buttons.add(Box.createRigidArea(new Dimension(20,0)));
        back = new JButton("Back");
        back.setFont(StyleArchive.SMALL_BUTTON);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Display.setPage(PageFactory.PROFILEPICKERPAGE);
            }
        });
        buttons.add(back);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(buttons);

        setLayout(new GridBagLayout());
        setBackground(StyleArchive.COLOR_BACKGROUND);
        add(panel);
    }

    public void setMedia(MediaTypes type){
        info2.removeAll();
        if(type == MediaTypes.MOVIE){
            getMovieInformation();
        }else if(type == MediaTypes.SERIES){
            getSeriesInformation();
        }
        validate();
        repaint();
    }

    public void addToDisplay(Display d){
        super.addToDisplay(d);
        combobox.setSelectedIndex(0);
        agebox.setSelectedIndex(5);
        titletext.setText("");
        ratetext.setText("");
        timetext.setText("");
        addtext.setText("");
        yeartext.setText("");
        year2text.setText("");
        picture.setText("*NONE*");
        AdminController.reset();
    }

    private void getNormalInformation(){
        info = new JPanel();
        title = new JLabel("Title:");
        title.setFont(StyleArchive.NORMAL);
        titletext = new JTextField();

        rate = new JLabel("Rating:");
        rate.setFont(StyleArchive.NORMAL);
        ratetext = new JTextField();

        age = new JLabel("Age:");
        age.setFont(StyleArchive.NORMAL);
        String[] choices = AgeTypes.getAgeTypes();
        agebox = new JComboBox(choices);
        agebox.setSelectedIndex(5);

        cat = new JLabel("Categories:");
        cat.setFont(StyleArchive.NORMAL);

        picture = new JLabel("Picture:");
        picture.setFont(StyleArchive.NORMAL);

        grid = new JPanel();
        GroupLayout layout = new GroupLayout(grid);
        getFile();
        getCats();
        grid.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup().
                addComponent(title).addComponent(rate).addComponent(age).addComponent(cat).addComponent(picture));
        hGroup.addGroup(layout.createParallelGroup().
                addComponent(titletext).addComponent(ratetext).addComponent(agebox).addComponent(cats).addComponent(file));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(title).addComponent(titletext));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(rate).addComponent(ratetext));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(age).addComponent(agebox));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(cat).addComponent(cats));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(picture).addComponent(file));
        layout.setVerticalGroup(vGroup);
        grid.setAlignmentX(Component.CENTER_ALIGNMENT);
        grid.setBackground(StyleArchive.COLOR_BACKGROUND);
        info.setBackground(StyleArchive.COLOR_BACKGROUND);
        info.add(grid);
    }

    private void getMovieInformation(){
        year = new JLabel("Year:");
        year.setFont(StyleArchive.NORMAL);
        yeartext = new JTextField(40);

        time = new JLabel("Playtime:");
        time.setFont(StyleArchive.NORMAL);
        timetext = new JTextField();

        grid = new JPanel();
        GroupLayout layout = new GroupLayout(grid);
        grid.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup().addComponent(year).addComponent(time));
        hGroup.addGroup(layout.createParallelGroup().addComponent(yeartext).addComponent(timetext));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(year).addComponent(yeartext));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(time).addComponent(timetext));
        layout.setVerticalGroup(vGroup);
        grid.setAlignmentX(Component.CENTER_ALIGNMENT);
        grid.setBackground(StyleArchive.COLOR_BACKGROUND);
        info2.add(grid);
    }

    private void getSeriesInformation(){
        year = new JLabel("Start year:");
        year.setFont(StyleArchive.NORMAL);
        yeartext = new JTextField(40);

        year2 = new JLabel("End year:");
        year2.setFont(StyleArchive.NORMAL);
        year2text = new JTextField();

        addseries = new JLabel("Seasons:");
        addseries.setFont(StyleArchive.NORMAL);

        getAddSeason();

        grid = new JPanel();
        GroupLayout layout = new GroupLayout(grid);
        grid.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup().addComponent(year).addComponent(year2).addComponent(addseries));
        hGroup.addGroup(layout.createParallelGroup().addComponent(yeartext).addComponent(year2text).addComponent(seasonspanel));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(year).addComponent(yeartext));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(year2).addComponent(year2text));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(addseries).addComponent(seasonspanel));
        layout.setVerticalGroup(vGroup);
        grid.setAlignmentX(Component.CENTER_ALIGNMENT);
        grid.setBackground(StyleArchive.COLOR_BACKGROUND);
        info2.add(grid);
    }

    private void getAddSeason(){
        seasonspanel = new JPanel();
        seasonspanel.setBackground(StyleArchive.COLOR_BACKGROUND);
        seasonspanel.setLayout(new BoxLayout(seasonspanel, BoxLayout.X_AXIS));

        addseasons = new JButton("Add season");
        addseasons.setFont(StyleArchive.SMALL_BUTTON);
        addseasons.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AdminController.addSeason();
                }catch (InvalidInputException exc){
                    JOptionPane.showMessageDialog(new JFrame(), exc.getMessage());
                }
            }
        });

        removeseasons = new JButton("Remove");
        removeseasons.setFont(StyleArchive.SMALL_BUTTON);
        removeseasons.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = seasonslist.getSelectedIndex();
                String selected = seasonslist.getSelectedValue().toString();
                try {
                    AdminController.deleteSeason(index, selected);
                }catch (InvalidInputException exc){
                    JOptionPane.showMessageDialog(new JFrame(),exc.getMessage());
                }
            }
        });

        addepisode = new JButton("Add episode");
        addepisode.setFont(StyleArchive.SMALL_BUTTON);
        addepisode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    AdminController.addEpisode();
                }catch (InvalidInputException exc){

                }
            }
        });

        seasonslist = new JList(AdminController.seasonsmodel.toArray());
        seasonslist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        seasonslist.setLayoutOrientation(JList.HORIZONTAL_WRAP);

        seasonsScroller = new JScrollPane(seasonslist);
        seasonsScroller.setPreferredSize(new Dimension(100, 100));

        seasonspanel.add(seasonsScroller);

        grid = new JPanel();
        grid.setLayout(new BoxLayout(grid, BoxLayout.Y_AXIS));
        grid.setBackground(StyleArchive.COLOR_BACKGROUND);

        grid.add(addseasons);
        grid.add(Box.createRigidArea(new Dimension(0,5)));
        grid.add(removeseasons);
        grid.add(Box.createRigidArea(new Dimension(0,5)));
        grid.add(addepisode);

        seasonspanel.add(grid);
    }

    private JPanel getFile(){
        file = new JPanel();
        file.setLayout(new BoxLayout(file, BoxLayout.X_AXIS));
        file.setBackground(StyleArchive.COLOR_BACKGROUND);

        filename = new JLabel("*NONE*");
        filename.setFont(StyleArchive.NORMAL);

        file.add(Box.createRigidArea(new Dimension(10,0)));
        file.add(filename);

        button = new JButton("Choose file");
        button.setFont(StyleArchive.SMALL_BUTTON);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AdminController.uploadImage();
                }catch (InvalidInputException exc){
                    JOptionPane.showMessageDialog(new JFrame(), exc.getMessage());
                }
            }
        });
        file.add(Box.createRigidArea(new Dimension(10,0)));
        file.add(button);
        file.add(Box.createRigidArea(new Dimension(10,0)));

        button = new JButton("Remove file");
        button.setFont(StyleArchive.SMALL_BUTTON);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminController.removeImage();
            }
        });

        file.add(button);
        return file;
    }

    public void updateList(){
        addlist.setModel(AdminController.offcategories);
        removelist.setModel(AdminController.oncategories);
    }

    private JPanel getCats(){
        cats = new JPanel();
        cats.setBackground(StyleArchive.COLOR_BACKGROUND);
        cats.setLayout(new BoxLayout(cats, BoxLayout.Y_AXIS));

        catsc = new JPanel();
        catsc.setLayout(new BoxLayout(catsc, BoxLayout.X_AXIS));
        catsc.setBackground(StyleArchive.COLOR_BACKGROUND);

        addlist = new JList(AdminController.offcategories.toArray());
        addlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addlist.setLayoutOrientation(JList.HORIZONTAL_WRAP);

        addlistScroller = new JScrollPane(addlist);
        addlistScroller.setPreferredSize(new Dimension(340, 100));

        add = new JButton("Add");
        add.setFont(StyleArchive.SMALL_BUTTON);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = addlist.getSelectedIndex();
                try {
                    AdminController.addGenre(index);
                }catch (InvalidInputException exc){
                    JOptionPane.showMessageDialog(new JFrame(), exc.getMessage());
                }
            }
        });

        catsc.add(addlistScroller);
        catsc.add(Box.createRigidArea(new Dimension(10,0)));
        catsc.add(add);
        cats.add(catsc);


        catsc = new JPanel();
        catsc.setLayout(new BoxLayout(catsc, BoxLayout.X_AXIS));
        catsc.setBackground(StyleArchive.COLOR_BACKGROUND);

        chosen = new JLabel("Add new:");
        chosen.setFont(StyleArchive.NORMAL);

        addtext = new JTextField();

        addown = new JButton("Add");
        addown.setFont(StyleArchive.SMALL_BUTTON);
        addown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = addtext.getText();
                try {
                    AdminController.createGenre(name);
                }catch (InvalidInputException exc){
                    JOptionPane.showMessageDialog(new JFrame(), exc.getMessage());
                }
            }
        });

        catsc.add(chosen);
        catsc.add(Box.createRigidArea(new Dimension(10,0)));
        catsc.add(addtext);
        catsc.add(Box.createRigidArea(new Dimension(10,0)));
        catsc.add(addown);
        cats.add(catsc);

        catsc = new JPanel();
        catsc.setLayout(new BoxLayout(catsc, BoxLayout.X_AXIS));
        catsc.setBackground(StyleArchive.COLOR_BACKGROUND);

        removelist = new JList(AdminController.oncategories.toArray());
        removelist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        removelist.setLayoutOrientation(JList.HORIZONTAL_WRAP);

        removelistScroller = new JScrollPane(removelist);
        removelistScroller.setPreferredSize(new Dimension(340, 100));

        remove = new JButton("Remove");
        remove.setFont(StyleArchive.SMALL_BUTTON);
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = removelist.getSelectedIndex();
                try {
                    AdminController.removeGenre(index);
                }catch (InvalidInputException exc){
                    JOptionPane.showMessageDialog(new JFrame(), exc.getMessage());
                }
            }
        });

        catsc.add(removelistScroller);
        catsc.add(Box.createRigidArea(new Dimension(10,0)));
        catsc.add(remove);
        cats.add(catsc);

        catsc = new JPanel();
        catsc.setLayout(new BoxLayout(catsc, BoxLayout.X_AXIS));
        catsc.setBackground(StyleArchive.COLOR_BACKGROUND);

        return cats;
    }
}
