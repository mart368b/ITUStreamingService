package ui.panels;

import maincomponents.ImageHandler;
import medias.types.AgeTypes;
import medias.types.Genre;
import medias.types.MediaTypes;
import reader.MediaHandler;
import ui.Display;
import ui.StyleArchive;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AdminPage extends Page {

    private JPanel panel, info, info2, cats, grid, catsc, buttons, file, seasonspanel;
    private JLabel label, title, rate, time, age, cat, chosen, year, year2, picture, filename, addseries;
    private JTextField titletext, ratetext, timetext, addtext, yeartext, year2text = new JTextField();
    private JComboBox combobox, agebox;
    private JButton remove, add, addown, button, back, addepisode, addseasons, removeseasons;
    private JList addlist, removelist, seasonslist;
    private JScrollPane addlistScroller, removelistScroller, seasonsScroller;

    private BufferedImage uploadimage;
    private MediaTypes mediatype;

    private DefaultListModel offcategories = new DefaultListModel();
    private DefaultListModel oncategories = new DefaultListModel();
    private DefaultListModel seasonsmodel = new DefaultListModel();

    private HashMap<Integer, ArrayList<String[]>> seasons = new HashMap<Integer, ArrayList<String[]>>();

    public AdminPage(){
        super();

        for(String genre : Genre.getGenreNames()){
            offcategories.addElement(genre);
        }

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        label = new JLabel("Add New Media");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(StyleArchive.HEADER);

        String[] choices = MediaTypes.getLoadedMediaTypes();
        combobox = new JComboBox(choices);
        combobox.setSelectedIndex(0);
        mediatype = MediaTypes.MOVIE;
        combobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String med = (String)cb.getSelectedItem();
                mediatype = MediaTypes.getTypeFromString(med);
                AdminPage page = (AdminPage) Page.getPage(Page.ADMINPAGE);
                page.setMedia(mediatype);
            }
        });

        panel.add(label);
        panel.add(combobox);
        getNormalInformation();
        panel.add(info);
        info2 = new JPanel();
        info2.setBackground(StyleArchive.COLOR_BACKGROUND);
        if(mediatype == MediaTypes.MOVIE){
            getMovieInformation();
        }else if(mediatype == MediaTypes.SERIES){
            getSeriesInformation();
        }
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

                if(titletext.getText().isEmpty() || ratetext.getText().isEmpty()
                    || yeartext.getText().isEmpty()){
                    JOptionPane.showMessageDialog(new JFrame(),
                            "All fields has to be filled!");
                    return;
                }

                if(CreateProfilePage.isNumber(yeartext.getText())){
                    if(Calendar.getInstance().get(Calendar.YEAR) < Integer.parseInt(yeartext.getText())){
                        JOptionPane.showMessageDialog(new JFrame(),
                                "Year can not be later than your current year!"
                                        +"\n" + Calendar.getInstance().get(Calendar.YEAR));
                        return;
                    }
                    if(Integer.parseInt(yeartext.getText()) < 0){
                        JOptionPane.showMessageDialog(new JFrame(),
                                "Year has to be after jesus was born!");
                        return;
                    }
                }

                if (!CreateProfilePage.isDouble(ratetext.getText())) {
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Rating has to be a decimal number!");
                    return;
                }

                if(Double.parseDouble(ratetext.getText()) < 0 || Double.parseDouble(ratetext.getText()) > 10){
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Rating must be between 0.0 - 10.0!");
                    return;
                }
                if (oncategories.size() == 0) {
                    int answer = JOptionPane.showConfirmDialog(new JFrame(),
                            "You have not chosen any category!\n"
                                    + "You need at least one!\n"
                                    + "Do you want to add category 'any'?",
                            "Click a button",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);
                    if (answer != 0) {
                        return;
                    }
                }

                if (uploadimage == null) {
                    int answer = JOptionPane.showConfirmDialog(new JFrame(),
                            "You have not chosen a picture!\n"
                                    + "Do you want to continue?\n",
                            "Click a button",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);
                    if (answer != 0) {
                        return;
                    }
                    uploadimage = ImageHandler.getInstance().getImage("stock");
                }

                if (combobox.getSelectedItem().toString().toLowerCase().equals("movies")) {
                    if (timetext.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(new JFrame(),
                                "All fields has to be filled!");
                        return;
                    }
                }

                MediaHandler.getInstance().addMovie(titletext.getText(),
                        yeartext.getText(),
                        Double.parseDouble(ratetext.getText()),
                        agebox.getSelectedItem().toString(),
                        timetext.getText(),
                        oncategories.toArray(),
                        uploadimage);
                reset();
                Display.getInstance().setPage(Page.USERPAGE);
            }
        });
        buttons.add(button);
        buttons.add(Box.createRigidArea(new Dimension(20,0)));
        back = new JButton("Back");
        back.setFont(StyleArchive.SMALL_BUTTON);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                Display.getInstance().setPage(Page.USERPAGE);
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

    private void reset(){
        combobox.setSelectedIndex(0);
        agebox.setSelectedIndex(5);
        titletext.setText("");
        ratetext.setText("");
        timetext.setText("");
        addtext.setText("");
        yeartext.setText("");
        year2text.setText("");
        uploadimage = null;
        mediatype = MediaTypes.MOVIE;
        oncategories = new DefaultListModel();
        offcategories = new DefaultListModel();
        for(String genre : Genre.getGenreNames()){
            offcategories.addElement(genre);
        }
        picture.setText("*NONE*");
    }

    private void updateList(){
        addlist.setModel(offcategories);
        removelist.setModel(oncategories);
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
                ArrayList<String[]> array = new ArrayList<String[]>();

                JPanel msg = new JPanel();
                JLabel label = new JLabel("What season is this?");
                label.setFont(StyleArchive.NORMAL);

                JTextField seasonnumber = new JTextField(10);

                msg.add(label);
                msg.add(seasonnumber);

                int result = JOptionPane.showConfirmDialog(new JFrame(), msg, "New Episode",
                        JOptionPane.OK_CANCEL_OPTION);

                if(result != 0){
                    return;
                }

                if(seasonnumber.getText().isEmpty()){
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Field has to be filled!");
                    return;
                }

                if(!CreateProfilePage.isNumber(seasonnumber.getText())){
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Season has to be a number!");
                    return;
                }

                int s = Integer.parseInt(seasonnumber.getText());

                if(s == 0){
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Season has to be different from 0!");
                    return;
                }

                if(seasons.containsKey(s)){
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Season already exists!");
                    return;
                }

                seasons.put(s, array);
                seasonsmodel.addElement(s + "-" + 0);
                seasonslist.setModel(seasonsmodel);
            }
        });

        removeseasons = new JButton("Remove");
        removeseasons.setFont(StyleArchive.SMALL_BUTTON);
        removeseasons.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = seasonslist.getSelectedIndex();
                if(index < 0){
                    JOptionPane.showMessageDialog(new JFrame(),
                            "No season chosen!");
                    return;
                }

                int answer = JOptionPane.showConfirmDialog(new JFrame(),
                        "Are you sure you want to:\n"
                                + "Delete this season and all its episodes?!\n",
                        "Click a button",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if(answer != 0){
                    return;
                }

                String selected = seasonslist.getSelectedValue().toString();
                seasonsmodel.removeElement(selected);
                seasonslist.setModel(seasonsmodel);
                seasons.remove(index+1);
            }
        });

        addepisode = new JButton("Add episode");
        addepisode.setFont(StyleArchive.SMALL_BUTTON);
        addepisode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = seasonslist.getSelectedIndex();
                if(index < 0){
                    JOptionPane.showMessageDialog(new JFrame(),
                            "No season chosen!");
                    return;
                }

                JPanel msg = new JPanel();

                JLabel title = new JLabel("Title:");
                JLabel time = new JLabel("Time:");

                JTextField titletext = new JTextField(20);
                JTextField timetext = new JTextField(20);

                grid = new JPanel();
                GroupLayout layout = new GroupLayout(grid);
                grid.setLayout(layout);
                layout.setAutoCreateGaps(true);
                layout.setAutoCreateContainerGaps(true);
                GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
                hGroup.addGroup(layout.createParallelGroup().addComponent(title).addComponent(time));
                hGroup.addGroup(layout.createParallelGroup().addComponent(titletext).addComponent(timetext));
                layout.setHorizontalGroup(hGroup);

                GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
                vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(title).addComponent(titletext));
                vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(time).addComponent(timetext));
                layout.setVerticalGroup(vGroup);
                grid.setAlignmentX(Component.CENTER_ALIGNMENT);
                grid.setBackground(StyleArchive.COLOR_BACKGROUND);

                msg.add(grid);

                int result = JOptionPane.showConfirmDialog(new JFrame(), msg, "New Episode", JOptionPane.OK_CANCEL_OPTION);

                if(result != JOptionPane.OK_OPTION){
                    return;
                }

                if(titletext.getText().isEmpty() || timetext.getText().isEmpty()){
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Both fields must be filled!");
                    return;
                }
                String[] info = new String[]{titletext.getText(), timetext.getText()};
                seasons.get(index+1).add(info);

                seasonsmodel.setElementAt((index+1) + "-" + seasons.get(index+1).size(), index);
            }
        });

        seasonslist = new JList(seasonsmodel.toArray());
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
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG","jpg");
                int returnval = chooser.showOpenDialog(new JFrame());
                if(returnval == JFileChooser.APPROVE_OPTION){
                    File file = chooser.getSelectedFile();
                    BufferedImage image = null;
                    try{
                        image = ImageIO.read(file);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                    if(image == null){
                        JOptionPane.showMessageDialog(new JFrame(),
                                "ERROR!\n" + "Image could not be loaded!");
                        return;
                    }
                    if(image.getWidth() != 140 || image.getHeight() != 209){
                        JOptionPane.showMessageDialog(new JFrame(),
                                "ERROR!\n" + "Image has to be 140x209 pixels!");
                        return;
                    }
                    filename.setText(file.getName());
                    uploadimage = image;
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
                if(uploadimage != null){
                    uploadimage = null;
                    filename.setText("*NONE*");
                }
            }
        });

        file.add(button);
        return file;
    }

    private JPanel getCats(){
        cats = new JPanel();
        cats.setBackground(StyleArchive.COLOR_BACKGROUND);
        cats.setLayout(new BoxLayout(cats, BoxLayout.Y_AXIS));

        catsc = new JPanel();
        catsc.setLayout(new BoxLayout(catsc, BoxLayout.X_AXIS));
        catsc.setBackground(StyleArchive.COLOR_BACKGROUND);

        addlist = new JList(offcategories.toArray());
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
                if(index == -1){
                    JOptionPane.showMessageDialog(new JFrame(),
                            "No selected category!");
                }else {
                    String cat = addlist.getModel().getElementAt(index).toString();

                    offcategories.remove(index);

                    oncategories.insertElementAt(cat, 0);
                    removelist.ensureIndexIsVisible(0);

                    updateList();
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
                if(addtext.getText().isEmpty()){
                    JOptionPane.showMessageDialog(new JFrame(),
                            "The category needs a name!");

                }else{
                    if(offcategories.contains(addtext.getText()) || oncategories.contains(addtext.getText())){
                        JOptionPane.showMessageDialog(new JFrame(),
                                "Category already exists!");
                    }else{
                        Genre.getGenreByName(addtext.getText());
                        oncategories.addElement(addtext.getText());
                        addtext.setText("");
                        updateList();
                    }
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

        removelist = new JList(oncategories.toArray());
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
                if(index == -1){
                    JOptionPane.showMessageDialog(new JFrame(),
                            "No selected category!");
                }else {
                    String cat = removelist.getModel().getElementAt(index).toString();

                    oncategories.remove(index);

                    offcategories.insertElementAt(cat, 0);
                    addlist.ensureIndexIsVisible(0);

                    updateList();
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
