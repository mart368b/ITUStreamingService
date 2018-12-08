package medias.types;

import maincomponents.ImageHandler;

import java.awt.image.BufferedImage;

public enum AgeTypes {
    AGE13("13"),
    AGE15("15"),
    AGE16("16"),
    AGE18("18"),
    GENERAL("G"),
    MATURE("M"),
    PARENTALGUIDANCE("PG"),
    RESTRICTED("R"),
    NONE("NONE");

    private String name;
    private BufferedImage img;

    AgeTypes(final String name){
        this.name = name;
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
        int age = 0;
        try{
            age = Integer.parseInt(name);
        }catch (Exception e){}
        if(age == 0){
            switch (name){
                case "PG": age = 3; break;
                case "M": age = 16; break;
                case "R": age = 18; break;
                case "NONE": age = 7; break;
                default: age = 0;
            }
        }
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
