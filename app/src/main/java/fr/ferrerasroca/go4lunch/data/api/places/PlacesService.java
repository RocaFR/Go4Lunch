package fr.ferrerasroca.go4lunch.data.api.places;

import fr.ferrerasroca.go4lunch.data.models.places.Results;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesService {
    @GET("json?radius=1000&type=restaurant")
    Call<Results> getResults(@Query(value = "location", encoded = true) String location, @Query("key") String api_key);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/nearbysearch/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}