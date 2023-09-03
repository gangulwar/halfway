package gangulwar.halfway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class PlaceListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);

        ArrayList<PlaceModal> placeModalArrayList = new ArrayList<>();
        placeModalArrayList.add(new PlaceModal("TDS", "Restaurant", "600", "https://ss3.4sqi.net/img/categories_v2/food/default_120.png"));

        placeModalArrayList.add(new PlaceModal("Naivedhyam Veg Restaurant", "Vegan and Vegetarian Restaurant", "500", "https://ss3.4sqi.net/img/categories_v2/food/vegetarian_120.png"));

        placeModalArrayList.add(new PlaceModal("Jagat Restaurant and Ice Cream Centre", "Restaurant", "57", "https://ss3.4sqi.net/img/categories_v2/food/default_120.png"));

        placeModalArrayList.add(new PlaceModal("KFC", "Fast Food Restaurant", "988", "https://ss3.4sqi.net/img/categories_v2/food/fastfood_120.png"));

        placeModalArrayList.add(new PlaceModal("Regent Theatre", "Indian Restaurant", "852", "https://ss3.4sqi.net/img/categories_v2/food/indian_120.png"));

        placeModalArrayList.add(new PlaceModal("Hibiscus", "Restaurant", "948", "https://ss3.4sqi.net/img/categories_v2/food/default_120.png"));

        Context context = this;
        View view = new View(context);

        RecyclerView places = findViewById(R.id.recyclerView);
        places.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        PlaceListAdapter placeListAdapter = new PlaceListAdapter(context, placeModalArrayList);
        places.setAdapter(placeListAdapter);

    }
}