package medias.types;

import debugging.LogTypes;
import debugging.Logger;
import ui.StyleArchive;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Genre {

    private static ArrayList<Genre> genres = new ArrayList();
    static {
        genres.add(new Genre("Any"));
    }

    public static Font genreFont;

    private String name;
    private JPanel card;

    private Genre(final String name){
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
    public JPanel getGenreCard() {
        return card;
    }

    public String getName(){
        return name;
    }

    public boolean equals(Genre genre){
        return genre.getName() == this.getName();
    }

    public static Genre[] getGenresByNames(String[] names){
        ArrayList<Genre> genres = new ArrayList<Genre>();
        for (int i = 0; i < names.length; i++){
            Genre foundgenreType = getGenreByName(names[i]);
            if ( foundgenreType != null ){
                genres.add(foundgenreType);
            }
        }
        return genres.toArray(new Genre[genres.size()]);
    }

    public static Genre getGenreByName(String name ){
        if(name.length() == 0){
            return null;
        }
        for (Genre genre: genres){
            if ( name.trim().equals(genre.getName()) ){
                return genre;
            }
        }
        Genre newGenre = new Genre(name.trim());
        genres.add(newGenre);
        return newGenre;
    }

    public static String[] getGenreNames(){
        String[] names = new String[genres.size()];
        for(int i = 0; i < names.length; i++){
            names[i] = genres.get(i).getName();
        }
        return names;
    }

    public static ArrayList<Genre> getGenres(){
        return genres;
    }
}
