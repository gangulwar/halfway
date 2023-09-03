package gangulwar.halfway;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

public class SelectLocationFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button selectLocationButton;
    private LatLng selectedLatLng;

    int key;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle args=getArguments();
        if (args != null) {
            key = args.getInt("key");
            System.out.println("Value Received="+key);
        }

        View view = inflater.inflate(R.layout.fragment_select_location, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

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

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                mMap.clear();

                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Selected Location"));

                selectedLatLng = latLng;
            }
        });
    }

}