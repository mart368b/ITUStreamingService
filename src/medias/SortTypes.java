package medias;

import java.util.Comparator;

public enum SortTypes {

    ALPHABETICLY(new Comparator<Media>() {
        @Override
        public int compare(Media o1, Media o2) {
            return o1.getTitle().compareTo(o2.getTitle());
        }
    }),
    RATING(new Comparator<Media>() {
        @Override
        public int compare(Media o1, Media o2) {
            return Double.compare(o1.getRating(),  o2.getRating());
        }
    }),
    DATE(new Comparator<Media>() {
        @Override
        public int compare(Media o1, Media o2) {
            String[] d1 = o1.getYear().split("-");
            int newd1 = Integer.parseInt(d1[d1.length - 1]);
            String[] d2 = o2.getYear().split("-");
            int newd2 = Integer.parseInt(d2[d2.length - 1]);
            return Integer.compare(newd1, newd2);
        }
    });

    private Comparator<Media> comparator;

    SortTypes( Comparator<Media> comparator){
        this.comparator = comparator;
    }

    public Comparator<Media> getComparator(){
        return comparator;
    }

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
