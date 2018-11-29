package medias;

public enum MediaTypes{
    MOVIE(Movie.class, 6, "movies"),
    SERIES(Serie.class, 6, "series"),
    NONE();

    private int columnCount; // Number of required columns in csv file
    private String name; // name of the media
    private Class classType;

    MediaTypes(){
        this.columnCount = 0;
        this.name = "";
        this.classType = null;
    }

    MediaTypes(final Class classType, final int columnCount, final String name){
        this.columnCount = columnCount;
        this.name = name;
        this.classType = classType;
    }
    /*
            Getters
     */
    public int getColumnCount(){
        return columnCount;
    }

    public String getName(){
        return name;
    }

    /**
     * Guess the MediaType based on a string
     * @param mediaType : name of MediaType
     * @return : MediaType corresponding to string
     */
    public static MediaTypes getTypeFromString( String mediaType){
        switch(mediaType.toLowerCase().trim()){
            case "movies":
                return MOVIE;
            case "series":
                return SERIES;
            default:
                return NONE;
        }
    }

    public static MediaTypes getMediaType( Media media ){
        for (MediaTypes mediaType: values()){
            if (mediaType.equals(media)){
                return mediaType;
            }
        }
        return null;
    }

    public boolean equals( Media media){
        return classType.isInstance(media);
    }

    /**
     * Specify which media to be loaded
     * @return : Array containing all MediaTypes to be loaded
     */
    public static String[] getLoadedMediaTypes(){
        return new String[]{"movies", "series"};
    }
}
