package gangulwar.halfway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.slider.Slider;

import java.util.Calendar;

import android.Manifest;

public class MainActivity extends AppCompatActivity {

    Slider slider;
    TextView radiusValue;
    LinearLayout diningAndDrinking;
    boolean isDiningClicked = false;
    LinearLayout landmarksAndOutdoorsLL;
    boolean isLandmarkClicked = false;
    LinearLayout entertainmentLL;
    boolean isEntertainmentClicked = false;
    TextView yourLocationTextView;
    TextView friendsLocationTextView;
    TextView greeting;
    Button findButton;
    double yourLocationLAT;
    double yourLocationLON;
    double friendsLocationLAT;
    double friendsLocationLON;
    int radius;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }



        greeting = findViewById(R.id.greeting);
        greeting.setText(getGreeting());

        slider = findViewById(R.id.radiusSlider);
        radiusValue = findViewById(R.id.radiusValue);

        diningAndDrinking = findViewById(R.id.diningAndDrinkingLL);
        landmarksAndOutdoorsLL = findViewById(R.id.landmarksAndOutdoorsLL);
        entertainmentLL = findViewById(R.id.entertainmentLL);

        yourLocationTextView = findViewById(R.id.yourLocation);
        friendsLocationTextView = findViewById(R.id.friendLocation);

        findButton = findViewById(R.id.findButton);

        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlaceListActivity.class);
                Bundle bundle = new Bundle();

                bundle.putDouble("middleLAT", (yourLocationLAT + friendsLocationLAT) / 2);
                bundle.putDouble("middleLON", (yourLocationLON + friendsLocationLON) / 2);
                System.out.println("MIddle\nLAT=" + (yourLocationLAT + friendsLocationLAT) / 2 + "\nLON=" + (yourLocationLON + friendsLocationLON) / 2);
                bundle.putInt("radius", radius);
                System.out.println("RADIUS=" + radius);
                int choice = 0;
                if (isDiningClicked) {
                    choice = 1;
                } else if (isLandmarkClicked) {
                    choice = 2;
                } else {
                    choice = 3;
                }
                //isDiningClicked ? 1 : (isLandmarkClicked ? 2 : 3)
                bundle.putInt("choice", choice);
                System.out.println("isDining=" + isDiningClicked + " isLandmark=" + isLandmarkClicked + " isEntertainment=" + isEntertainmentClicked);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });
        slider.setValue(2300);
        slider.setValueFrom(100);
        slider.setValueTo(10000);

        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onValueChange(Slider slider, float value, boolean fromUser) {
                int snappedValue = Math.round(value / 100) * 100;
                slider.setValue(snappedValue);
                String txt;
                radiusValue.setText(String.valueOf(snappedValue));
                radius = snappedValue;
            }
        });

        slider.setTrackActiveTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.buttonAfter)));

        diningAndDrinking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources resources = getResources();
                if (isDiningClicked) {
                    Toast.makeText(getApplicationContext(), "Already Selected", Toast.LENGTH_SHORT).show();
                } else {
                    isDiningClicked = true;
                    int resourceId = resources.getIdentifier("after_click", "drawable", getPackageName());
                    int before = resources.getIdentifier("border_radius", "drawable", getPackageName());
                    Drawable drawable = resources.getDrawable(resourceId);
                    diningAndDrinking.setBackground(drawable);
                    Drawable drawable1 = resources.getDrawable(before);
                    landmarksAndOutdoorsLL.setBackground(drawable1);
                    entertainmentLL.setBackground(drawable1);
                    isEntertainmentClicked = false;
                    isLandmarkClicked = false;

                    Bundle bundle = new Bundle();
                    bundle.putInt("key", 1);

                    SubCategoryFragment fragment = new SubCategoryFragment();
                    fragment.setArguments(bundle);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.subCategory, fragment,"dining_and_drinking")
                            .commit();

                    View overlay=findViewById(R.id.overlay);
                    overlay.setVisibility(View.VISIBLE);

                }
            }
        });

        landmarksAndOutdoorsLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources resources = getResources();
                if (isLandmarkClicked) {
                    Toast.makeText(getApplicationContext(), "Already Selected", Toast.LENGTH_SHORT).show();
                } else {
                    isLandmarkClicked = true;
                    int resourceId = resources.getIdentifier("after_click", "drawable", getPackageName());
                    int before = resources.getIdentifier("border_radius", "drawable", getPackageName());
                    Drawable drawable = resources.getDrawable(resourceId);
                    landmarksAndOutdoorsLL.setBackground(drawable);
                    Drawable drawable1 = resources.getDrawable(before);
                    diningAndDrinking.setBackground(drawable1);
                    entertainmentLL.setBackground(drawable1);
                    isEntertainmentClicked = false;
                    isDiningClicked = false;

                    Bundle bundle = new Bundle();
                    bundle.putInt("key", 2);

                    SubCategoryFragment fragment = new SubCategoryFragment();
                    fragment.setArguments(bundle);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.subCategory, fragment,"landmarks_and_outdoors")
                            .commit();

                }
            }
        });

        entertainmentLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources resources = getResources();
                if (isEntertainmentClicked) {
                    Toast.makeText(getApplicationContext(), "Already Selected", Toast.LENGTH_SHORT).show();
                } else {
                    isEntertainmentClicked = true;
                    int resourceId = resources.getIdentifier("after_click", "drawable", getPackageName());
                    int before = resources.getIdentifier("border_radius", "drawable", getPackageName());
                    Drawable drawable = resources.getDrawable(resourceId);
                    entertainmentLL.setBackground(drawable);
                    Drawable drawable1 = resources.getDrawable(before);
                    diningAndDrinking.setBackground(drawable1);
                    landmarksAndOutdoorsLL.setBackground(drawable1);
                    isLandmarkClicked = false;
                    isDiningClicked = false;

                    Bundle bundle = new Bundle();
                    bundle.putInt("key", 3);

                    SubCategoryFragment fragment = new SubCategoryFragment();
                    fragment.setArguments(bundle);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.subCategory, fragment,"entertainment")
                            .commit();

                }
            }
        });

        yourLocationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("key", 1);

                SelectLocationFragment mapFragment = new SelectLocationFragment();
                mapFragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mapContainer, mapFragment)
                        .commit();
            }

        });

        friendsLocationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putInt("key", 2);

                SelectLocationFragment mapFragment = new SelectLocationFragment();
                mapFragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mapContainer, mapFragment)
                        .commit();
            }
        });
    }

    public void updateCoordinates(LatLng selectedLatLng, int key) {

        if (selectedLatLng != null) {
            String coordinates = "Latitude: " + selectedLatLng.latitude + ", Longitude: " + selectedLatLng.longitude;
            if (key == 1) {
                yourLocationTextView.setText(coordinates);
                yourLocationLAT = selectedLatLng.latitude;
                yourLocationLON = selectedLatLng.longitude;
            } else if (key == 2) {
                friendsLocationTextView.setText(coordinates);
                friendsLocationLAT = selectedLatLng.latitude;
                friendsLocationLON = selectedLatLng.longitude;
            } else {
                System.out.println("ERRORRRRRRRR");
                System.out.println(key);
            }
        }
    }

    public static String getGreeting() {

        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        String greeting;

        if (hourOfDay >= 4 && hourOfDay < 12) {
            greeting = "Good Morning,";
        } else if (hourOfDay >= 12 && hourOfDay < 17) {
            greeting = "Good Afternoon,";
        } else if (hourOfDay >= 17 && hourOfDay < 21) {
            greeting = "Good Evening,";
        } else {
            greeting = "Good Night,";
        }
        return greeting;
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("dining_and_drinking");

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        } else {
            super.onBackPressed();
        }
    }


}