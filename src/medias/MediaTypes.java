package medias;

public enum MediaTypes {
    MOVIE(6, "movies"),
    SERIES(6, "series"),
    NONE(0, "unknown");

    private int columnCount; // Number of required columns in csv file
    private String name; // name of the media

    MediaTypes(final int columnCount, final String name){
        this.columnCount = columnCount;
        this.name = name;
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
        if (media instanceof Movie){
            return MOVIE;
        }
        if (media instanceof Series){
            return SERIES;
        }
        return NONE;
    }

    /**
     * Specify which media to be loaded
     * @return : Array containing all MediaTypes to be loaded
     */
    public static String[] getLoadedMediaTypes(){
        return new String[]{"movies", "series"};
    }
}
