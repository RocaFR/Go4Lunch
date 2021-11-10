package fr.ferrerasroca.go4lunch.data.api.places;

import fr.ferrerasroca.go4lunch.data.models.places.responses.PlaceDetailResponse;
import fr.ferrerasroca.go4lunch.data.models.places.responses.NearbyPlacesResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesCalls {
    @GET("nearbysearch/json?radius=1000&type=restaurant")
    Call<NearbyPlacesResponse> getNearbyPlaces(@Query(value = "location", encoded = true) String location, @Query("key") String api_key);

    @GET("details/json?")
    Call<PlaceDetailResponse> getPlaceDetails(@Query("place_id") String placeID , @Query("key") String apiKey);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}