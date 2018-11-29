package medias;

import debugging.LogTypes;
import debugging.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public enum Categories {
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
    private Categories(final String name){
        this.name = name;
        card = getCard();
    }

    private JPanel getCard() {
        JPanel panel = new JPanel();
        Label label = new Label(name);
        label.setFont(getFont());
        panel.add(label);
        return panel;
    }

    private Font getFont(){
        if (genreFont == null){
            genreFont = new Font("Arial", Font.PLAIN, 24);
        }
        return genreFont;
    }

    public String getName(){
        return name;
    }

    public static Categories[] getCategoriesByNames( String[] names){
        ArrayList<Categories> categories = new ArrayList<Categories>();
        for (int i = 0; i < names.length; i++){
            Categories foundCategory = getCategoryByName(names[i]);
            if ( foundCategory != null ){
                categories.add(foundCategory);
            }
        }
        return categories.toArray(new Categories[categories.size()]);
    }


    public static Categories getCategoryByName( String name ){
        if(name.length() == 0){
            return null;
        }
        for (Categories category: Categories.values()){
            if ( name.trim().equals(category.getName()) ){
                return category;
            }
        }
        Logger.log(name + " is not recognized as a category: consider adding " + name.toUpperCase() + "(\"" + name + "\")", LogTypes.SOFTERROR);
        return null;
    }

    public static String[] getCategorieNames(){
        Categories[] categoryList = values();
        String[] categories = new String[categoryList.length];
        for (int i = 0; i < categoryList.length; i++){
            categories[i] = categoryList[i].getName();
        }
        return categories;
    }

    public JPanel getGenreCard() {
        return card;
    }
}
