package maincomponents.controllers;

import debugging.Exceptions.InvalidInputException;
import maincomponents.ImageHandler;
import medias.types.Genre;
import medias.types.MediaTypes;
import reader.MediaHandler;
import ui.Display;
import ui.StyleArchive;
import ui.pages.AdminPage;
import ui.pages.PageHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AdminController extends Controller {

    private static HashMap<Integer, ArrayList<String[]>> seasons = new HashMap<Integer, ArrayList<String[]>>();
    private static BufferedImage uploadimage;
    private static MediaTypes mediatype;
    private static AdminPage adminPage;

    public static DefaultListModel offcategories = new DefaultListModel();
    public static DefaultListModel oncategories = new DefaultListModel();
    public static DefaultListModel seasonsmodel = new DefaultListModel();

    public static void init(AdminPage adminPage){
        AdminController.adminPage = adminPage;

        for(String type : Genre.getGenreNames()){
            offcategories.addElement(type);
        }
        mediatype = MediaTypes.MOVIE;
    }

    public static void setMediaType(MediaTypes type){
        mediatype = type;
        adminPage.setMedia(type);
    }

    public static void addMedia(String title, String rating, String year, String year2, String selected, String time, String age){
        if(title.isEmpty() || rating.isEmpty() || year.isEmpty()){
            throw new InvalidInputException("All fields has to be filled!");
        }

        if(!isNumber(year)){
            throw new InvalidInputException("Year has to be a number!");
        }
        int yearInt = Integer.parseInt(year);
        if(Calendar.getInstance().get(Calendar.YEAR) < yearInt){
            throw new InvalidInputException("Year can not be later than your current year!"
                    +"\n" + Calendar.getInstance().get(Calendar.YEAR));
        }
        if(yearInt < 0){
            throw new InvalidInputException("Year has to be after jesus was born!");
        }
        if (!isDouble(rating)) {
            throw new InvalidInputException("Rating has to be a decimal number!");
        }
        double ratingDou = Double.parseDouble(rating);
        if(ratingDou < 0 || ratingDou > 10){
            throw new InvalidInputException("Rating must be between 0.0 - 10.0!");
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

        if (selected.toLowerCase().equals("movies")) {
            if (time.isEmpty()) {
                throw new InvalidInputException("All fields has to be filled!");
            }
            MediaHandler.addMovie(title, year, ratingDou, age,
                    time + "min", oncategories.toArray(), uploadimage);

        }else if(selected.toLowerCase().equals("series")){
            if(year2.isEmpty()){
                throw new InvalidInputException("All fields has to be filled!");
            }
            if(!isNumber(year2)){
                throw new InvalidInputException("Year2 has to be a number!");
            }
            int year2Int = Integer.parseInt(year2);
            if(Calendar.getInstance().get(Calendar.YEAR) < year2Int){
                throw new InvalidInputException("Year2 can not be later than your current year!"
                        +"\n" + Calendar.getInstance().get(Calendar.YEAR));
            }
            if(year2Int < 0){
                throw new InvalidInputException("Year2 has to be after jesus was born!");
            }
            if(seasons.size() == 0){
                throw new InvalidInputException("No seasons are made!");
            }
            for(int season : seasons.keySet()){
                if(seasons.get(season).size() == 0){
                    throw new InvalidInputException("All seasons needs at least one episode!");
                }
            }
            MediaHandler.addSeries(title, ratingDou, age, oncategories.toArray(),
                    uploadimage, year, year2, seasons);
        }

        Display.setPage(PageHandler.USERPAGE);
    }

    public static void deleteSeason(int index, String selected){
        if(index < 0){
            throw new InvalidInputException("No season chosen!");
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

        seasons.remove(index+1);
        seasonsmodel.removeElement(selected);

        adminPage.seasonslist.setModel(seasonsmodel);

    }

    public static void addSeason(){
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
            throw new InvalidInputException("Field has to be filled!");
        }

        if(!Controller.isNumber(seasonnumber.getText())){
            throw new InvalidInputException("Season has to be a number!");
        }

        if (!isNumber(seasonnumber.getText())){
            throw new InvalidInputException("Season number has to be a number");
        }

        int s = Integer.parseInt(seasonnumber.getText());

        if(s == 0){
            throw new InvalidInputException("Season has to be different from 0!");
        }

        if(seasons.containsKey(s)){
            throw new InvalidInputException("Season already exists!");
        }

        seasons.put(s, array);
        seasonsmodel.addElement(s + "-" + 0);
        adminPage.seasonslist.setModel(seasonsmodel);
    }

    public static void addEpisode(){
        int index = adminPage.seasonslist.getSelectedIndex();
        if(index < 0){
            throw new InvalidInputException("No season chosen!");
        }

        JPanel msg = new JPanel();

        JLabel title = new JLabel("Title:");
        JLabel time = new JLabel("Time:");

        JTextField titletext = new JTextField(20);
        JTextField timetext = new JTextField(20);

        JPanel grid = new JPanel();
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
            throw new InvalidInputException("Both fields must be filled!");
        }
        String[] info = new String[]{titletext.getText(), timetext.getText() + "min"};
        seasons.get(index + 1).add(info);

        seasonsmodel.setElementAt((index+1) + "-" + seasons.get(index+1).size(), index);
    }

    public static void uploadImage(){
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
                throw new InvalidInputException("ERROR!\n" + "Image could not be loaded!");
            }
            if(image.getWidth() != 140 || image.getHeight() != 209){
                JOptionPane.showMessageDialog(new JFrame(),
                        "ERROR!\n" + "Image has to be 140x209 pixels!");
                return;
            }

            adminPage.filename.setText(file.getName());
            uploadimage = image;
        }
    }

    public static void reset() {
        uploadimage = null;
        mediatype = MediaTypes.MOVIE;
        oncategories = new DefaultListModel();
        offcategories = new DefaultListModel();
        for(String type : Genre.getGenreNames()){
            offcategories.addElement(type);
        }
    }

    public static void removeImage() {
        if(uploadimage != null){
            uploadimage = null;
            adminPage.filename.setText("*NONE*");
        }
    }

    public static void removeGenre(int index) {

        if(index == -1){
            throw new InvalidInputException("No selected category!");
        }
        String cat = adminPage.removelist.getModel().getElementAt(index).toString();

        oncategories.remove(index);
        offcategories.insertElementAt(cat, 0);

        adminPage.addlist.ensureIndexIsVisible(0);
        adminPage.updateList();
    }

    public static void addGenre(int index) {
        if(index == -1){
            throw new InvalidInputException("No selected category!");
        }
        String cat = adminPage.addlist.getModel().getElementAt(index).toString();

        offcategories.remove(index);
        oncategories.insertElementAt(cat, 0);

        adminPage.removelist.ensureIndexIsVisible(0);
        adminPage.updateList();
    }

    public static void createGenre(String name) {
        if(name.isEmpty()){
            throw new InvalidInputException("The category needs a name!");
        }
        if(AdminController.offcategories.contains(name) || AdminController.oncategories.contains(name)){
            throw new InvalidInputException("Category already exists!");
        }
        Genre.getGenreByName(name);
        AdminController.oncategories.addElement(name);

        adminPage.addtext.setText("");
        adminPage.updateList();
    }
}
