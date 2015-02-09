package com.doodeec.weather.android.geoloc;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.doodeec.weather.android.WedrApplication;
import com.doodeec.weather.android.client.data.SessionData;
import com.doodeec.weather.android.util.WedrLog;

/**
 * Location service provides method for requesting a device location
 *
 * @author Dusan Bartos
 */
public class LocationService {

    /**
     * Requests single location report
     */
    public static boolean requestLocation() {
        LocationManager locationManager = (LocationManager) WedrApplication.getContext()
                .getSystemService(Context.LOCATION_SERVICE);

        // location provider criteria
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);

        // request location
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            SessionData.getInstance().getGeoLocation().setOngoingRequest(true);
            locationManager.requestSingleUpdate(
                    criteria,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            WedrLog.d("Location changed: LAT " + location.getLatitude() + " LON " + location.getLongitude());
                            // notifies all observers
                            SessionData.getInstance().getGeoLocation().setLocation(location);
                            SessionData.getInstance().getGeoLocation().setOngoingRequest(false);
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {
                            WedrLog.d("Location status changed");
                        }

                        @Override
                        public void onProviderEnabled(String provider) {
                            WedrLog.d("Location provider enabled");
                        }

                        @Override
                        public void onProviderDisabled(String provider) {
                            WedrLog.w("Location provider disabled");
                        }
                    }, null);
            return true;
        } else {
            WedrLog.w("Provider not enabled");
            return false;
        }
    }
}
