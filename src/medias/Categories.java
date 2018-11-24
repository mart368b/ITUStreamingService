package medias;

import debugging.LogTypes;
import debugging.Logger;

import java.util.ArrayList;
import java.util.HashMap;

public enum Categories {
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

    private final String name;
    private Categories(final String name){
        this.name = name;
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
        for (Categories category: Categories.values()){
            if ( name.trim().equals(category.getName()) ){
                return category;
            }
        }
        Logger.log(name + " is not recognized as a category: consider adding " + name.toUpperCase() + "(\"" + name + "\")", LogTypes.SOFTERROR);
        return null;
    }

}
