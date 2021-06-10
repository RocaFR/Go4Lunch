package fr.ferrerasroca.go4lunch.utils;

public class PlaceUtils {

    public static Float convertRating(Float highestBaseRating, Float newHighestBaseRating, Float rating) {
        return (rating / highestBaseRating) * newHighestBaseRating;
    }
}
