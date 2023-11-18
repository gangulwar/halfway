package gangulwar.halfway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Objects;

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

        Window window = PlaceListActivity.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(PlaceListActivity.this, R.color.black));

        head = findViewById(R.id.header_title);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        String lat = String.valueOf(bundle.getDouble("middleLAT"));
        String lon = String.valueOf(bundle.getDouble("middleLON"));
        String radius = String.valueOf(bundle.getInt("radius"));

        String bundleCategory = bundle.getString("choice");

        head.setText(getCategoryName(bundle.getInt("category")));
        String category = "0";

        if (Objects.equals(bundleCategory, "1")) {
            category = "13000";
        } else if (Objects.equals(bundleCategory, "2")) {
            category = "16000";
        } else if (Objects.equals(bundleCategory, "3")) {
            category = "10000";
        } else {
            category = bundleCategory;
        }

        String finalCategory = category;

        progressBar = findViewById(R.id.progressBar);
        Glide.with(this)
                .asGif()
                .load(R.drawable.loading)
                .into(progressBar);

        context = getApplicationContext();

        new Thread(new Runnable() {
            @Override
            public void run() {

//                ArrayList<PlaceModal> arrayList = APIRequest.getDemoArrayList();

                ArrayList<PlaceModal> arrayList = null;
                try {
                   arrayList = APIRequest.APIRequest(lat, lon, radius, finalCategory);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // ArrayList<PlaceModal> arrayList=new ArrayList<>();
                //arrayList.add(new PlaceModal("Restaurant","Type Of Place","https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8cmVzdGF1cmFudHxlbnwwfHwwfHx8MA%3D%3D&w=1000&q=80",1500,23.918,79.123));

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

//                        if (finalArrayList==null){
//                            FrameLayout frameLayout = findViewById(R.id.frame_layout);
//                            ImageView cringe=findViewById(R.id.cringe_gif);
//                            Glide.with(context)
//                                    .asGif()
//                                    .load(R.drawable.cringe)
//                                    .into(c);
//                            View inflatedLayout = LayoutInflater.from(context).inflate(R.layout.no_places_found, null);
//                            frameLayout.addView(inflatedLayout);
//                        }else{
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

    private static ArrayList<String> getCategoryCodes(ArrayList<String> arrayList, int category) {

        ArrayList<String> stringArrayList = new ArrayList<>();
        if (category == 1) {
            //Dining and Drinking

            for (int i = 0; i < arrayList.size(); i++) {
                String place = arrayList.get(i);

                switch (place) {
                    case "Bar":
                        stringArrayList.add("13003");
                        break;
                    case "Bakery":
                        stringArrayList.add("13002");
                        break;
                    case "Cafe":
                        stringArrayList.add("13034");
                        break;
                    case "Coffee Shop":
                        stringArrayList.add("13035");
                        break;
                    case "Tea Room":
                        stringArrayList.add("13036");
                        break;
                    case "Cafeteria":
                        stringArrayList.add("13037");
                        break;
                    case "Dessert Shop":
                        stringArrayList.add("13040");
                        break;
                    case "Afgan":
                        stringArrayList.add("13066");
                        break;
                    case "African":
                        stringArrayList.add("13067");
                        break;
                    case "American":
                        stringArrayList.add("13068");
                        break;
                    case "Asian":
                        stringArrayList.add("13072");
                        break;
                    case "Australian":
                        stringArrayList.add("13073");
                        break;
                    case "Bangladeshi":
                        stringArrayList.add("13075");
                        break;
                    case "Chinese":
                        stringArrayList.add("13099");
                        break;
                    case "Dutch":
                        stringArrayList.add("13138");
                        break;
                    case "Fast Food":
                        stringArrayList.add("13145");
                        break;
                    case "French":
                        stringArrayList.add("13148");
                        break;
                    case "Indian":
                        stringArrayList.add("13199");
                        break;
                    case "Italian":
                        stringArrayList.add("13236");
                        break;
                    case "Mexican":
                        stringArrayList.add("13303");
                        break;
                    default:
                        stringArrayList.add("");
                        break;

                }
            }
            return stringArrayList;

        } else if (category == 2) {
            //Landmarks and Outdoors

            for (int i = 0; i < arrayList.size(); i++) {
                String place = arrayList.get(i);
                switch (place) {
                    case "Canal":
                        stringArrayList.add("16009");
                        break;
                    case "Castle":
                        stringArrayList.add("16011");
                        break;
                    case "Dam":
                        stringArrayList.add("16056");
                        break;
                    case "Farm":
                        stringArrayList.add("16014");
                        break;
                    case "Forest":
                        stringArrayList.add("16015");
                        break;
                    case "Garden":
                        stringArrayList.add("16017");
                        break;
                    case "Hill":
                        stringArrayList.add("16058");
                        break;
                    case "Historic":
                        stringArrayList.add("16020");
                        break;
                    case "Lake":
                        stringArrayList.add("16023");
                        break;
                    case "Monument":
                        stringArrayList.add("16026");
                        break;
                    case "Park":
                        stringArrayList.add("16032");
                        break;
                    case "River":
                        stringArrayList.add("16043");
                        break;
                    case "Structure":
                        stringArrayList.add("16007");
                        break;
                }
            }
            return stringArrayList;

        } else {
            //Entertainment

            for (int i = 0; i < arrayList.size(); i++) {
                String place = arrayList.get(i);
                switch (place) {
                    case "Amusement Park":
                        stringArrayList.add("10001");
                        break;
                    case "Arcade":
                        stringArrayList.add("10003");
                        break;
                    case "Casino":
                        stringArrayList.add("10008");
                        break;
                    case "Comedy Club":
                        stringArrayList.add("10010");
                        break;
                    case "Fair":
                        stringArrayList.add("10017");
                        break;
                    case "Movie Theater":
                        stringArrayList.add("10024");
                        break;
                    case "Museum":
                        stringArrayList.add("10027");
                        break;
                    case "Night Club":
                        stringArrayList.add("10032");
                        break;
                    case "Gaming Cafe":
                        stringArrayList.add("10018");
                        break;
                    case "Pool Hall":
                        stringArrayList.add("10045");
                        break;
                    case "VR Cafe":
                        stringArrayList.add("10054");
                        break;
                    case "Water Park":
                        stringArrayList.add("10055");
                        break;
                    case "Zoo":
                        stringArrayList.add("10056");
                        break;
                }
            }
            return stringArrayList;
        }
    }
}