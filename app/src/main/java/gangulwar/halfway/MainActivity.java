package gangulwar.halfway;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

public class MainActivity extends AppCompatActivity {

    Slider slider;
    TextView radiusValue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slider = findViewById(R.id.radiusSlider);
        radiusValue = findViewById(R.id.radiusValue);
        slider.setValue(100);
        slider.setValueFrom(100);
        slider.setValueTo(10000);

// Set up listener to snap slider values to multiples of 100
        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onValueChange(Slider slider, float value, boolean fromUser) {
                // Calculate the nearest multiple of 100
                int snappedValue = Math.round(value / 100) * 100;
                slider.setValue(snappedValue);
                radiusValue.setText(String.valueOf(snappedValue));
            }
        });

    }
}