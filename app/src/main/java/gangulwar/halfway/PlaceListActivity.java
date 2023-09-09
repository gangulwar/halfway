package gangulwar.halfway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PlaceListActivity extends AppCompatActivity {

    ImageView progressBar;
    Context context;
    TextView head;

    //    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_place_list);
//
//        Bundle bundle=getIntent().getBundleExtra("bundle");
//        String lat=String.valueOf(bundle.getDouble("middleLAT"));
//        String lon=String.valueOf(bundle.getDouble("middleLON"));
//        String radius=String.valueOf(bundle.getInt("radius"));
//        int category;
//
//        switch (bundle.getInt("category")) {
//            case 1:
//                category = 13000;
//                break;
//            case 2:
//                category = 16000;
//                break;
//            case 3:
//                category = 10000;
//                break;
//            default:
//                category=0;
//        }
//
//        ArrayList<PlaceModal> placeModalArrayList = APIRequest.APIRequest(lat,lon,radius,category);
//
////        placeModalArrayList.add(new PlaceModal("TDS", "Restaurant", "600", "https://ss3.4sqi.net/img/categories_v2/food/default_120.png"));
////
////        placeModalArrayList.add(new PlaceModal("Naivedhyam Veg Restaurant", "Vegan and Vegetarian Restaurant", "500", "https://ss3.4sqi.net/img/categories_v2/food/vegetarian_120.png"));
////
////        placeModalArrayList.add(new PlaceModal("Jagat Restaurant and Ice Cream Centre", "Restaurant", "57", "https://ss3.4sqi.net/img/categories_v2/food/default_120.png"));
////
////        placeModalArrayList.add(new PlaceModal("KFC", "Fast Food Restaurant", "988", "https://ss3.4sqi.net/img/categories_v2/food/fastfood_120.png"));
////
////        placeModalArrayList.add(new PlaceModal("Regent Theatre", "Indian Restaurant", "852", "https://ss3.4sqi.net/img/categories_v2/food/indian_120.png"));
////
////        placeModalArrayList.add(new PlaceModal("Hibiscus", "Restaurant", "948", "https://ss3.4sqi.net/img/categories_v2/food/default_120.png"));
//
//        Context context = this;
//        //View view = new View(context);
//
//        RecyclerView places = findViewById(R.id.recyclerView);
//        places.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//        PlaceListAdapter placeListAdapter = new PlaceListAdapter(context, placeModalArrayList);
//        places.setAdapter(placeListAdapter);
//
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);

        head=findViewById(R.id.header_title);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        String lat = String.valueOf(bundle.getDouble("middleLAT"));
        String lon = String.valueOf(bundle.getDouble("middleLON"));
        String radius = String.valueOf(bundle.getInt("radius"));
        int bundleCategory = bundle.getInt("choice");

        head.setText(getCategoryName(bundleCategory));
        int category = 0;

        if (bundleCategory == 1) {
            category = 13000;
        } else if (bundleCategory == 2) {
            category = 16000;
        } else if (bundleCategory == 3) {
            category = 10000;
        }
        int finalCategory = category;

        progressBar = findViewById(R.id.progressBar);
        Glide.with(this)
                .asGif()
                .load(R.drawable.loading)
                .into(progressBar);

        context = getApplicationContext();

        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<PlaceModal> arrayList = null;
                try {
                    arrayList = APIRequest.APIRequest(lat, lon, radius, finalCategory);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                for (int i = 0; i < arrayList.size(); i++) {
                    System.out.println(arrayList.get(i).nameOfPlace + "\n");
                }
                ArrayList<PlaceModal> finalArrayList = arrayList;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //ImageView progressBar = findViewById(R.id.progressBar);
                        Glide.with(context).clear(progressBar);
                        progressBar.setVisibility(View.INVISIBLE);
                        RecyclerView places = findViewById(R.id.recyclerView);
                        places.setLayoutManager(new LinearLayoutManager(PlaceListActivity.this, LinearLayoutManager.VERTICAL, false));
                        PlaceListAdapter placeListAdapter = new PlaceListAdapter(PlaceListActivity.this, finalArrayList);
                        places.setAdapter(placeListAdapter);
                    }
                });
            }
        }).start();
    }

//    private class APITask extends AsyncTask<String, Void, ArrayList<PlaceModal>> {
//
////        @Override
////        protected void onPreExecute() {
////            super.onPreExecute();
////            // Show the loading view
////            findViewById(R.id.loadingLayout).setVisibility(View.VISIBLE);
////        }
//
//        @Override
//        protected ArrayList<PlaceModal> doInBackground(String... params) {
//            String lat = params[0];
//            String lon = params[1];
//            String radius = params[2];
//            int category = Integer.parseInt(params[3]);
//            APIRequest apiRequest = new APIRequest();
//            return apiRequest.APIRequest(lat, lon, radius, category);
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<PlaceModal> placeModalArrayList) {
//            super.onPostExecute(placeModalArrayList);
//
//            //findViewById(R.id.loadingLayout).setVisibility(View.GONE);
//            // Update your UI with the received data
//            System.out.println(placeModalArrayList);
//            RecyclerView places = findViewById(R.id.recyclerView);
//            places.setLayoutManager(new LinearLayoutManager(PlaceListActivity.this, LinearLayoutManager.VERTICAL, false));
//            PlaceListAdapter placeListAdapter = new PlaceListAdapter(PlaceListActivity.this, placeModalArrayList);
//            places.setAdapter(placeListAdapter);
//        }
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private static String getCategoryName(int a) {
        String txt;
        if (a == 1) {
            txt = "Dining and Drinking";
        } else if (a == 2) {
            txt = "Landmarks and Outdoors";
        } else {
            txt = "Entertainment";
        }
        return txt;
    }
}