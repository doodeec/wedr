package com.doodeec.weather.android.geoloc;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.doodeec.weather.android.WedrApplication;
import com.doodeec.weather.android.util.WedrLog;

/**
 * @author Dusan Bartos
 */
public class LocationService {

    private static OnLocationRetrievedListener sListener;

    public static void requestLocation(OnLocationRetrievedListener listener) {
        // request without listener is useless
        if (listener == null) return;

        // store listener as a static variable
        sListener = listener;

        LocationManager locationManager = (LocationManager) WedrApplication.getContext()
                .getSystemService(Context.LOCATION_SERVICE);

        // location provider criteria
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);

        // request location
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestSingleUpdate(
                    criteria,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            WedrLog.d("Location changed: LAT " + location.getLatitude() + " LON " + location.getLongitude());
                            sListener.onLocation(location.getLatitude(), location.getLongitude());
                            // release listener for GC
                            sListener = null;
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {
                            WedrLog.d("Status changed");
                        }

                        @Override
                        public void onProviderEnabled(String provider) {
                            WedrLog.d("Provider enabled");
                        }

                        @Override
                        public void onProviderDisabled(String provider) {
                            WedrLog.d("Provider disabled");
                        }
                    }, null);
        } else {
            WedrLog.d("Provider not enabled");
            sListener.onLocationError();
            sListener = null;
        }
    }

    public interface OnLocationRetrievedListener {
        void onLocation(double latitude, double longitude);

        void onLocationError();
    }
}
