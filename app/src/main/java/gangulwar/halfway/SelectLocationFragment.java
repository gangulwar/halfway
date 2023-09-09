package gangulwar.halfway;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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

public class SelectLocationFragment extends Fragment implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private Button selectLocationButton;
    private LatLng selectedLatLng;
    public static LatLng currentLocation;
    int key;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle args = getArguments();
        if (args != null) {
            key = args.getInt("key");
            System.out.println("Value Received=" + key);
        }

        View view = inflater.inflate(R.layout.fragment_select_location, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 150));

        System.out.println("SelectMapFragment=" + currentLocation);

        if (currentLocation != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        }


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

                                LatLng currentLatLng = new LatLng(latitude, longitude);

                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 14));
                            } else {
                                Toast.makeText(getContext(), "Permission Not Granted", Toast.LENGTH_SHORT);
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
            @Override
            public void onMapClick(LatLng latLng) {

                Resources resources = getResources();
                int after = resources.getIdentifier("after_click", "drawable", getContext().getPackageName());
                //int before = resources.getIdentifier("border_radius", "drawable", getPackageName());
                Drawable drawable = resources.getDrawable(after);
                selectLocationButton.setBackground(drawable);
                selectLocationButton.setText("Select This Location");
                mMap.clear();

                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Selected Location"));

                selectedLatLng = latLng;
            }
        });
    }

}