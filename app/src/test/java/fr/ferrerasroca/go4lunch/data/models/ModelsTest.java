package fr.ferrerasroca.go4lunch.data.models;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.ferrerasroca.go4lunch.data.models.places.Geometry;
import fr.ferrerasroca.go4lunch.data.models.places.Location;
import fr.ferrerasroca.go4lunch.data.models.places.OpeningHours;
import fr.ferrerasroca.go4lunch.data.models.places.Photo;
import fr.ferrerasroca.go4lunch.data.models.places.Place;
import fr.ferrerasroca.go4lunch.data.models.places.PlusCode;
import fr.ferrerasroca.go4lunch.data.models.places.responses.NearbyPlacesResponse;
import fr.ferrerasroca.go4lunch.data.models.places.responses.PlaceDetailResponse;

/**
 * Basics models tests.
 */
public class ModelsTest {

    // User data
    private final String uuid = "A1B2C3";
    private final String username = "Bryan F.R.";
    private final String userEmail = "bryan.ferreras@gmail.com";
    private final String userBio = "Hey, i'm Bryan ! What about you ?";
    private final String userJob = "Android developer";
    private final String anotherUseruuid = "B3C4D5";
    private final String anotherUsername = "Henry";
    private final String anotherUserEmail = "henry@me.com";

    // Message data
    private final Date date = new Date();
    private final String message = "Hey, what's up ?";

    // Place data
    private final String businessStatus = "OPERATIONAL";
    private final Geometry geometry = new Geometry();
    private final Location location = new Location();
    private final String icon = "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/restaurant-71.png";
    private final String name = "WOK IN BAG";
    private final OpeningHours openingHours = new OpeningHours();
    private final List<Photo> photos = new ArrayList<>();
    private final Photo photo = new Photo();
    private final String placeId = "ChIJafHAg6meVA0RsSlc8Jsx3jk";
    private final PlusCode plusCode = new PlusCode();
    private final Float rating = -1f;
    private final String reference = "ChIJafHAg6meVA0RsSlc8Jsx3jk";
    private final String scope = "GOOGLE";
    private final List<String> types = new ArrayList<>();
    private final String typeRestaurant = "restaurant";
    private final String typeFood = "food";
    private final String typePointOfInterest = "point_of_interest";
    private final String typeEstablishment = "establishment";
    private final Integer userRatingsTotal = 157;
    private final String vicinity = "52 Avenue Saint-Exupéry, La Teste-de-Buch";
    private final Integer priceLevel = 2;
    private final List<User> usersParticipants = new ArrayList<>();

    // ###########################
    // Basics usage with an User.
    // ###########################

    @Test
    public void canICreateAnUser() {
        User user = new User(uuid, username, userEmail, null);

        Assert.assertNotNull(user);
    }

    @Test
    public void canIUpdateAnUser() {
        User user = new User(uuid, username, userEmail, null);
        user.setUsername("Bryan Ferreras-Roca");
        user.setEmail("bryan.ferrerasV2@gmail.com");
        user.setBio("Another bio");
        user.setJob("Lead developer");

        Assert.assertNotEquals(user.getUsername(), username);
        Assert.assertNotEquals(user.getEmail(), userEmail);
        Assert.assertNotEquals(user.getBio(), userBio);
        Assert.assertNotEquals(user.getJob(), userJob);
    }

    // ############################
    // Basics usage with a Message.
    // ############################

    @Test
    public void canICreateAMessage() {
        User user = new User(uuid, username, userEmail, null);
        Message message = new Message(user, date, this.message);

        Assert.assertNotNull(message);
    }

    @Test
    public void canIUpdateAMessage() {
        User user = new User(uuid, username, userEmail, null);
        User anotherUser = new User(anotherUseruuid, anotherUsername, anotherUserEmail, null);
        Message message = new Message(user, this.date, this.message);

        message.setMessage("Bye !");
        message.setSender(anotherUser);

        Assert.assertNotEquals(message.getMessage(), this.message);
        Assert.assertNotEquals(message.getSender(), user);
    }

    // ###########################
    // Basics usage with a Place.
    // ###########################

    @Test
    public void canIWorkWithAPlace() {
        Place place = this.configureAPlace();

        Assert.assertNotNull(place);
    }

    @Test
    public void canIWorkWithAnNearbyPlacesApiResponse() {
        NearbyPlacesResponse nearbyPlacesResponse = new NearbyPlacesResponse();
        Place place = this.configureAPlace();
        ArrayList<Place> places = new ArrayList<>();
        places.add(place);

        nearbyPlacesResponse.setPlaces(places);

        Assert.assertNotNull(nearbyPlacesResponse);
        Assert.assertEquals(1, nearbyPlacesResponse.getPlaces().size());
    }

    @Test
    public void canIWorkWithAPlaceDetailResponse() {
        PlaceDetailResponse placeDetailResponse = new PlaceDetailResponse();
        Place place = this.configureAPlace();

        placeDetailResponse.setPlace(place);

        Assert.assertNotNull(placeDetailResponse);
        Assert.assertNotNull(placeDetailResponse.getPlace());

    }

    // ##############
    // Utils methods.
    // ##############

    private Place configureAPlace() {
        Place place = new Place();
        place.setBusinessStatus(businessStatus);
        geometry.setLocation(location);
        place.setGeometry(geometry);
        place.setIcon(icon);
        place.setName(name);
        place.setOpeningHours(openingHours);
        photo.setPhotoReference("Aap_uEBbmD8UsSfMaEQe8nYwCH");
        photos.add(photo);
        place.setPhotos(photos);
        place.setPlaceId(place.getPlaceId());
        place.setPlusCode(plusCode);
        place.setRating(rating);
        place.setReference(reference);
        place.setScope(scope);
        types.add(typeEstablishment);
        types.add(typeFood);
        types.add(typePointOfInterest);
        types.add(typeRestaurant);
        place.setTypes(types);
        place.setUserRatingsTotal(userRatingsTotal);
        place.setVicinity(vicinity);
        place.setPriceLevel(priceLevel);
        usersParticipants.add(new User(uuid, username, userEmail, null));
        usersParticipants.add(new User(anotherUseruuid, anotherUsername, anotherUserEmail, null));
        place.setUsersParticipants(usersParticipants);

        return place;
    }
}