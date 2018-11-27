package medias;

public enum SortTypes {

    ALPHABETICLY, RATING, DATE;

    public static String[] getSortTypeNames(){
        SortTypes[] sortTypes = values();
        String[] names = new String[sortTypes.length];
        for (int i = 0; i < names.length; i++){
            String sortTypeName = sortTypes[i].name().toLowerCase();
            names[i] = sortTypeName.substring(0, 1).toUpperCase() + sortTypeName.substring(1, sortTypeName.length());
        }
        return names;
    }
}
