package gangulwar.halfway;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.Manifest;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SelectLocationFragment extends Fragment implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private Button selectLocationButton;
    private LatLng selectedLatLng;
    private LatLng currentLocation;
    int key;
    private SearchView mapSearchView;
    ImageButton myLocationButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        Bundle args = getArguments();
        if (args != null) {
            key = args.getInt("key");
            System.out.println("Value Received=" + key);
        }

        View view = inflater.inflate(R.layout.fragment_select_location, container, false);

//        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
//                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);
//        try {
//            assert autocompleteFragment != null;
//            autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG));
//            System.out.println("REACHED");
//            System.out.println(Place.Field.LAT_LNG);
//            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//                @Override
//                public void onPlaceSelected(@NonNull Place place) {
//                    System.out.println("CLicked...");
//                    LatLng selectedLocation = place.getLatLng();
//                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLocation, 15f));
////                updateSelectedLocation(selectedLocation);
//                    selectedLatLng = selectedLocation;
//                }
//
//                @Override
//                public void onError(@NonNull Status status) {
//                    // Handle the error
//                    Log.e("Places", "Error: " + status);
//                }
//            });
//        } catch (NullPointerException e) {
//            Toast.makeText(getContext(), "Try Again!", Toast.LENGTH_SHORT).show();
//        }
        mapSearchView = view.findViewById(R.id.searchView);
        mapSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = mapSearchView.getQuery().toString();
                List<Address> addressList = null;

                if (location != null) {
                    Geocoder geocoder = new Geocoder(getContext());

                    try {
                        addressList = geocoder.getFromLocationName(location, 1);

                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                        mapSearchView.clearFocus();
                    } catch (IndexOutOfBoundsException exception) {
                        Toast.makeText(getContext(), "Unable to find the searched location. Try providing an exact location.", Toast.LENGTH_LONG).show();
                    } catch (IOException exception) {
                        Toast.makeText(getContext(), "Try Again...", Toast.LENGTH_SHORT).show();
                    }

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        myLocationButton = view.findViewById(R.id.myLocationButton);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 150));

//        System.out.println("SelectMapFragment=" + currentLocation);
//
//        if (currentLocation != null) {
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
//        }


        mapFragment.getMapAsync(this);

        selectLocationButton = view.findViewById(R.id.selectLocationButton);
        selectLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedLatLng != null) {

                    if (getActivity() != null) {
                        ((MainActivity) getActivity()).updateCoordinates(selectedLatLng, key);
                    }

                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.remove(SelectLocationFragment.this);
                    transaction.commit();
                } else {
                    Toast.makeText(getContext(), "Please select a location on the map", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {

                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();

                                //LatLng currentLatLng = new LatLng(latitude, longitude);
                                currentLocation = new LatLng(latitude, longitude);
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12));

                            } else {
                                Toast.makeText(getContext(), "Permission Not Granted", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            myLocationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentLocation != null) {
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLocation, 14);

                        mMap.animateCamera(cameraUpdate, 1000, null);
                    } else {
                        Toast.makeText(getContext(), "Permission Not Granted", Toast.LENGTH_SHORT).show();

                        ActivityCompat.requestPermissions(requireActivity(),
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                LOCATION_PERMISSION_REQUEST_CODE);
                    }
                }
            });

        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }

//        double lat = 21.148822;
//        double lon = 79.080658;
//
//        LatLng latLng=new LatLng(lat,lon);
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 2));


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onMapClick(LatLng latLng) {

//                Resources resources = getResources();
//                int after = resources.getIdentifier("after_click", "drawable", getContext().getPackageName());
//                //int before = resources.getIdentifier("border_radius", "drawable", getPackageName());
//                Drawable drawable = resources.getDrawable(after);
//                selectLocationButton.setBackground(drawable);
                int after = R.drawable.after_click;
                selectLocationButton.setBackgroundResource(after);
                selectLocationButton.setText("Select This Location");
                selectLocationButton.setTextColor(Color.WHITE);
                mMap.clear();

                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Selected Location"));

                selectedLatLng = latLng;
            }
        });
    }

}