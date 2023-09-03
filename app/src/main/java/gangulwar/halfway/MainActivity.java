package gangulwar.halfway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.slider.Slider;

import java.sql.SQLOutput;
import java.util.Calendar;

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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        greeting = findViewById(R.id.greeting);
        greeting.setText(getGreeting());

        slider = findViewById(R.id.radiusSlider);
        radiusValue = findViewById(R.id.radiusValue);

        diningAndDrinking = findViewById(R.id.diningAndDrinkingLL);
        landmarksAndOutdoorsLL = findViewById(R.id.landmarksAndOutdoorsLL);
        entertainmentLL = findViewById(R.id.entertainmentLL);

        yourLocationTextView = findViewById(R.id.yourLocation);
        friendsLocationTextView = findViewById(R.id.friendLocation);

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
            }
        });

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
                }
            }
        });

        yourLocationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("key",1);

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
                bundle.putInt("key",2);

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
            } else if (key == 2) {
                friendsLocationTextView.setText(coordinates);
            }else{
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
}