package fr.ferrerasroca.go4lunch.utils;

import android.content.Context;
import android.location.LocationManager;

public class LocationUtils {

    public static Boolean isLocationEnabled(Context context) {
        LocationManager systemService = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        String gpsProvider = LocationManager.GPS_PROVIDER;
        String networkProvider = LocationManager.NETWORK_PROVIDER;

        return systemService.isProviderEnabled(gpsProvider) && systemService.isProviderEnabled(networkProvider);
    }

}
