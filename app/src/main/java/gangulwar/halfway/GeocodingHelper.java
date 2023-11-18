package gangulwar.halfway;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeocodingHelper {

    public static String getAddressFromCoordinates(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                StringBuilder addressText = new StringBuilder();

                // You can extract various address components like this
                String locality = address.getLocality();
                String postalCode = address.getPostalCode();
                String country = address.getCountryName();

                if (locality != null) {
                    addressText.append(locality);
                }

                if (postalCode != null) {
                    if (addressText.length() > 0) {
                        addressText.append(", ");
                    }
                    addressText.append(postalCode);
                }

                if (country != null) {
                    if (addressText.length() > 0) {
                        addressText.append(", ");
                    }
                    addressText.append(country);
                }

                return addressText.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
