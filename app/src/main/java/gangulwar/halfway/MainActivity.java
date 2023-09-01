package gangulwar.halfway;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;

public class MainActivity extends AppCompatActivity {

    Slider slider;
    TextView radiusValue;
    LinearLayout diningAndDrinking;
    boolean isDiningClicked = false;
    LinearLayout landmarksAndOutdoorsLL;
    boolean isLandmarkClicked = false;
    LinearLayout entertainmentLL;
    boolean isEntertainmentClicked = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slider = findViewById(R.id.radiusSlider);
        radiusValue = findViewById(R.id.radiusValue);

        diningAndDrinking = findViewById(R.id.diningAndDrinkingLL);
        landmarksAndOutdoorsLL = findViewById(R.id.landmarksAndOutdoorsLL);
        entertainmentLL = findViewById(R.id.entertainmentLL);

        slider.setValue(2300);
        slider.setValueFrom(100);
        slider.setValueTo(10000);

        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onValueChange(Slider slider, float value, boolean fromUser) {
                // Calculate the nearest multiple of 100
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
    }
}