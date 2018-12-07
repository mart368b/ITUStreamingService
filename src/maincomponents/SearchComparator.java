package maincomponents;

import medias.Media;

import java.util.ArrayList;
import java.util.Comparator;

public class SearchComparator implements Comparator<Media> {

    public static SearchComparator instance;

    public static SearchComparator getComparator(String key){
        if (SearchComparator.instance == null){
            instance = new SearchComparator();
        }
        return instance.search(key);
    }

    private SearchComparator(){}
    private String key;
    public SearchComparator search(String key){
        this.key = key.toLowerCase();
        return this;
    }

    private int getScore(Media m){
        String title = m.getTitle().toLowerCase();
        if (key.equals(title)){
            return 0;
        }
        int score = key.length();
        int maxSimilar = 0;
        for (int offset = 0; offset < (title.length() - key.length() + 1); offset++){
            int similar = 0;
            for (int i = 0; i < key.length(); i++){
                int index = i + offset;
                if (index >= title.length()){
                    break;
                }
                if (key.charAt(i) == title.charAt(index)){
                    similar++;
                }
            }
            if (similar > maxSimilar){
                maxSimilar = similar;
            }
        }
        score -= maxSimilar;

        return score;
    }

    @Override
    public int compare(Media m1, Media m2) {
        return Integer.compare(getScore(m1), getScore(m2));
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
