package medias.types;

import maincomponents.ImageHandler;

import java.awt.image.BufferedImage;

public enum AgeTypes {
    GENERAL("G", 0),
    AGE13("13", 13),
    AGE15("15", 15),
    AGE16("16", 16),
    AGE18("18", 18),
    MATURE("M", 14),
    PARENTALGUIDANCE("PG", 14),
    RESTRICTED("R", 19),
    NONE("NONE", 0);

    private String name;
    private BufferedImage img;
    private int age;

    AgeTypes(final String name, final int age){
        this.name = name;
        this.age = age;
        img = ImageHandler.getInstance().getImage(name);
    }

    public static AgeTypes getAgeTypeFromName(String name){
        for (AgeTypes ageType: values()){
            if (name.equals(ageType.name)){
                return ageType;
            }
        }
        return NONE;
    }

    public int getAge(){
        return age;
    }

    public String getName(){
        return name;
    }

    public BufferedImage getImage(){
        return img;
    }

    public static String[] getAgeTypes(){
        String[] array = new String[AgeTypes.values().length];
        int index = 0;
        for(AgeTypes type : AgeTypes.values()){
            array[index] = type.getName();
            index++;
        }
        return array;
    }
}
