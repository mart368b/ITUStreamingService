package maincomponents.controllers;

import maincomponents.SearchComparator;
import medias.Media;
import medias.types.Genre;
import medias.types.MediaTypes;
import medias.types.SortTypes;
import reader.MediaHandler;
import ui.pages.PreviewPage;

import javax.swing.*;
import java.util.*;
import java.util.stream.Stream;

public class PreviewController extends Controller{

    private static PreviewPage preview;
    private static JTextField minYear, maxYear, minRating, maxRating;
    private static SortTypes sortType;
    private static boolean reversedSorting = false;
    private static List<Media> displayedMedia;
    private static String miYear, maYear, miRating, maRating;


    public static void init(PreviewPage preview){
        PreviewController.preview = preview;
        minYear = preview.getMinYear();
        maxYear = preview.getMaxYear();
        minRating = preview.getMinRating();
        maxRating = preview.getMaxRating();
        sortType = SortTypes.ALPHABETICLY;
    }

    private PreviewController(){}

    private static void resetDisplayedMedia(){
        displayedMedia = MediaHandler.getAllMedia();
    }

    private static void updatePreview(){
        preview.updatePreview(displayedMedia);
    }

    private static void filterDisplayedMediaByBoundaries() {
        boolean valid = validateLimits();
        if(!valid){
            return;
        }
        int lowYearLimit = Integer.parseInt(minYear.getText());
        int highYearLimit = Integer.parseInt(maxYear.getText());
        double lowRatingLimit = Double.parseDouble(minRating.getText());
        double highRatingLimit = Double.parseDouble(maxRating.getText());
        Iterator<Media> iter = displayedMedia.iterator();
        while (iter.hasNext()){
            Media m = iter.next();
            String[] yearPart = m.getYear().split("-");
            int year = Integer.parseInt(yearPart[yearPart.length - 1]);
            double rating = m.getRating();
            if (year < lowYearLimit || year > highYearLimit || rating < lowRatingLimit || rating > highRatingLimit){
                iter.remove();
            }
        }
    }

    private static boolean validateLimits(){
        if (minRating.getText().length() == 0){
            resetBoundaries();
            return true;
        }else{
            if (!isNumber(minYear.getText())){
                return false;
            }
            if (!isNumber(maxYear.getText())){
                return false;
            }
            if(!isDouble(minRating.getText())){
                return false;
            }
            if (!isDouble(maxRating.getText())){
                return false;
            }
            return true;
        }
    }

    private static void sortDisplayedMedia() {
        Comparator<Media> comp = sortType.getComparator();
        if (reversedSorting){
            comp = Collections.reverseOrder(comp);
        }
        Collections.sort(displayedMedia, comp);
        PreviewController.sortType = sortType;
    }

    /**
     * External funtions
     */

    public static void reverseSorting() {
        reversedSorting = !reversedSorting;
        sortDisplayedMedia();
        updatePreview();
    }

    public static void setSortingType(SortTypes sortType) {
        PreviewController.sortType = sortType;
        sortDisplayedMedia();
        updatePreview();
    }

    public static void resetBoundaries(){
        if (displayedMedia.size() == 0){
            minRating.setText(miRating);
            maxRating.setText(maRating);
            minYear.setText(miYear);
            maxYear.setText(maYear);
            return;
        }

        Stream<Media> mediaStream = displayedMedia.stream();
        double[] ratings = mediaStream.mapToDouble( Media::getRating).sorted().toArray();

        if (miRating == null) miRating = Double.toString(ratings[0]);
        if (maRating == null) maRating = Double.toString(ratings[ratings.length - 1]);

        minRating.setText(Double.toString(ratings[0]));
        maxRating.setText(Double.toString(ratings[ratings.length - 1]));

        mediaStream = displayedMedia.stream();
        int[] years = mediaStream.mapToInt( m -> {
            String[] yearsPart = m.getYear().split("-");
            return Integer.parseInt(yearsPart[yearsPart.length - 1]);
        }).sorted().toArray();

        if (miYear == null) miYear = Integer.toString(years[0]);
        if (maYear == null) maYear = Integer.toString(years[years.length - 1]);

        minYear.setText(Integer.toString(years[0]));
        maxYear.setText(Integer.toString(years[years.length - 1]));
    }

    public static void displayMedia(){
        resetDisplayedMedia();
        filterDisplayedMediaByBoundaries();
        sortDisplayedMedia();
        updatePreview();
    }

    public static void displayMedia(MediaTypes mediaTypes){
        resetDisplayedMedia();
        filterDisplayedMediaByBoundaries();

        Iterator<Media> iter = displayedMedia.iterator();
        while (iter.hasNext()){
            Media media = iter.next();
            if (!mediaTypes.equals(media)){
                iter.remove();
            }
        }

        sortDisplayedMedia();
        updatePreview();
    }

    public static void displayMedia(String title, Genre genre){
        resetDisplayedMedia();

        if (genre.getName() != "Any"){
            Iterator<Media> iter = displayedMedia.iterator();
            while (iter.hasNext()){
                Media media = iter.next();
                if (!media.hasGenre(genre)){
                    iter.remove();
                }
            }
        }

        SearchComparator c = SearchComparator.getComparator(title);
        Collections.sort(displayedMedia, c);

        updatePreview();
    }

    public static void displayMedia(List<Media> medias){
        displayedMedia = medias;
        sortDisplayedMedia();
        updatePreview();
    }

}
