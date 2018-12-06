package medias.types;

import debugging.LogTypes;
import debugging.Logger;
import ui.StyleArchive;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public enum GenreTypes {
    ANY("Any"),
    CRIME("Crime"),
    DRAMA("Drama"),
    BIOGRAPHY("Biography"),
    HISTORY("History"),
    SPORT("Sport"),
    ROMANCE("Romance"),
    WAR("War"),
    MYSTERY("Mystery"),
    ADVENTURE("Adventure"),
    FAMILY("Family"),
    FANTASY("Fantasy"),
    THRILLER("Thriller"),
    HORROR("Horror"),
    FILMNOIR("Film-Noir"),
    MUSICAL("Musical"),
    ACTION("Action"),
    SCIFI("Sci-fi"),
    COMEDY("Comedy"),
    WESTERN("Western"),
    MUSIC("Music"),
    ANIMATION("Animation"),
    NINETIES("1995-1998"),
    DOCUMENTARY("Documentary"),
    TALKSHOW("Talk-show");

    public static Font genreFont;

    private final String name;
    private JPanel card;
    private GenreTypes(final String name){
        this.name = name;
        card = getCard();
    }

    private JPanel getCard() {
        JPanel panel = new JPanel();
        Label label = new Label(name);
        label.setFont(StyleArchive.HEADER);
        panel.setBackground(StyleArchive.COLOR_BACKGROUND);
        panel.add(label);
        return panel;
    }

    public String getName(){
        return name;
    }

    public static GenreTypes[] getGenreTypeByNames(String[] names){
        ArrayList<GenreTypes> categories = new ArrayList<GenreTypes>();
        for (int i = 0; i < names.length; i++){
            GenreTypes foundgenreType = getGenreTypeByName(names[i]);
            if ( foundgenreType != null ){
                categories.add(foundgenreType);
            }
        }
        return categories.toArray(new GenreTypes[categories.size()]);
    }


    public static GenreTypes getGenreTypeByName(String name ){
        if(name.length() == 0){
            return null;
        }
        for (GenreTypes genreType: GenreTypes.values()){
            if ( name.trim().equals(genreType.getName()) ){
                return genreType;
            }
        }
        Logger.log(name + " is not recognized as a genreType: consider adding " + name.toUpperCase() + "(\"" + name + "\")", LogTypes.SOFTERROR);
        return null;
    }

    public static String[] getGenreNames(){
        GenreTypes[] genreTypeList = values();
        String[] categories = new String[genreTypeList.length];
        for (int i = 0; i < genreTypeList.length; i++){
            categories[i] = genreTypeList[i].getName();
        }
        return categories;
    }

    public JPanel getGenreCard() {
        return card;
    }
}
