package gangulwar.halfway;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

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
    ImageButton about_me;
    double yourLocationLAT = 0;
    double yourLocationLON = 0;
    double friendsLocationLAT = 0;
    double friendsLocationLON = 0;
    int radius = 2300;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private FragmentManager fragmentManager;
    private static ArrayList<String> categories = new ArrayList<>();
    boolean isAboutMeActive = false;
    DrawerLayout drawerLayout;
    ImageView random;
    LinearLayout dining, landmarks, entertainment;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    View alpha_layout;
    TextView dining_and_drinking_textView;
    TextView landmarksAndOutdoors_textView;
    TextView entertainment_textView;
    Resources resources;
    static boolean isScreenWidthShort = false;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }

        Window window = MainActivity.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.main_background));

//        drawerLayout = findViewById(R.id.drawer_layout);
//
//        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.developed_by, R.string.aarsh_gangulwar);
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//        //actionBarDrawerToggle.syncState();
//
        resources = getResources();

        greeting = findViewById(R.id.greeting);
        radiusValue = findViewById(R.id.radiusValue);
//        Button forest = findViewById(R.id.button_forest);
//        Button garden = findViewById(R.id.button_garden);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float screenWidth = displayMetrics.widthPixels / displayMetrics.density;

        if (screenWidth < 450) {
            greeting.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
            radiusValue.setTextSize(30);
            isScreenWidthShort = true;
//            forest.setTextSize(10);
//            garden.setTextSize(10);
        }
//        else {
//            greeting.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//        }


        alpha_layout = findViewById(R.id.alpha_layout);
        about_me = findViewById(R.id.about_me_button);
        about_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fragment.setArguments(bundle);
                //View alpha_layout = findViewById(R.id.alpha_layout);
                //layout.setAlpha((float) 0.1);
                alpha_layout.setVisibility(View.VISIBLE);
                AboutMeFragment fragment = new AboutMeFragment(alpha_layout);
                FragmentTransaction transaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_from_up, R.anim.slide_down);
                transaction.replace(R.id.about_me, fragment, "about_me");
//              isAboutMeActive=true;
//              getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.about_me, fragment)
//                        .commit();
//                transaction.setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_left);
                //transaction.setCustomAnimations(R.anim.center_scaling,R.anim.center_scaling);
                transaction.addToBackStack(null);
                transaction.commit();

                //replaceSubCategoryFragment(1, "temp", false);
//                //openDrawer(drawerLayout);
//                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//                    System.out.println("About Me...");
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                } else {
//
//                    drawerLayout.openDrawer(GravityCompat.START);
//                }
            }
        });
//
//        dining = findViewById(R.id.nav_drawer_dining);
//        landmarks = findViewById(R.id.nav_drawer_landmarks);
//        entertainment = findViewById(R.id.nav_drawer_entertainment);
//
//
//        dining.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("Dining Click", "Dining item clicked");
//                System.out.println("Clicked...");
//                Toast.makeText(getApplicationContext(), "Clicked Dining", Toast.LENGTH_LONG).show();
//
//            }
//        });
//
//        landmarks.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("Dining Click", "Dining item clicked");
//                System.out.println("Clicked...");
//                Toast.makeText(getApplicationContext(), "Clicked Dining", Toast.LENGTH_LONG).show();
//
//            }
//        });
//        entertainment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("Dining Click", "Dining item clicked");
//                System.out.println("Clicked...");
//                Toast.makeText(getApplicationContext(), "Clicked Dining", Toast.LENGTH_LONG).show();
//
//            }
//        });

        {
            //greeting.setText(getGreeting());


            slider = findViewById(R.id.radiusSlider);


            diningAndDrinking = findViewById(R.id.diningAndDrinkingLL);
            landmarksAndOutdoorsLL = findViewById(R.id.landmarksAndOutdoorsLL);
            entertainmentLL = findViewById(R.id.entertainmentLL);

            yourLocationTextView = findViewById(R.id.yourLocation);
            friendsLocationTextView = findViewById(R.id.friendLocation);

            findButton = findViewById(R.id.findButton);

            fragmentManager = getSupportFragmentManager();

            dining_and_drinking_textView = findViewById(R.id.dining_and_drinking_textView);
            landmarksAndOutdoors_textView = findViewById(R.id.landmarksAndOutdoors_textView);
            entertainment_textView = findViewById(R.id.entertainment_textView);

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

                    bundle.putInt("category", isDiningClicked ? 1 : (isLandmarkClicked ? 2 : 3));

                    String choice = "0";
                    if (yourLocationLON == 0) {
                        Toast.makeText(getApplicationContext(), "Please Select Your Location", Toast.LENGTH_SHORT).show();
                    } else if (friendsLocationLAT == 0) {
                        Toast.makeText(getApplicationContext(), "Please Select Your Friends Location", Toast.LENGTH_SHORT).show();
                    } else if (categories.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please Select A Category", Toast.LENGTH_SHORT).show();
                    } else {

                        String temp = categories.get(0);
                        if (Objects.equals(temp, "1")) {
                            choice = "1";
                            bundle.putString("choice", choice);
                        } else if (Objects.equals(temp, "2")) {
                            choice = "2";
                            bundle.putString("choice", choice);
                        } else if (Objects.equals(temp, "3")) {
                            choice = "3";
                            bundle.putString("choice", choice);
                        } else {

                            for (int i = 1; i < categories.size(); i++) {
                                temp = temp + "," + categories.get(i);
                            }
                            System.out.println("CATEGORIES=" + temp);
                            bundle.putString("choice", temp);
                        }

                        //isDiningClicked ? 1 : (isLandmarkClicked ? 2 : 3)
                        //bundle.putInt("choice", choice);
                        System.out.println("isDining=" + isDiningClicked + " isLandmark=" + isLandmarkClicked + " isEntertainment=" + isEntertainmentClicked);
                        intent.putExtra("bundle", bundle);

                        startActivity(intent);
                    }
                }
            });
            slider.setValue(2300);

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

//            if (!Places.isInitialized()) {
//                Places.initialize(getApplicationContext(), getString(R.string.api_key), Locale.US);
//            }
            //PlacesClient placesClient = Places.createClient(this);

            yourLocationTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("key", 1);

                    SelectLocationFragment mapFragment = new SelectLocationFragment();
                    mapFragment.setArguments(bundle);

//                    getSupportFragmentManager()
                    fragmentManager.beginTransaction()
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

//                    getSupportFragmentManager()
                    fragmentManager.beginTransaction()
                            .replace(R.id.mapContainer, mapFragment)
                            .commit();
                }
            });

            diningAndDrinking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Resources resources = getResources();
                    if (isDiningClicked) {
                        Toast.makeText(getApplicationContext(), "Already Selected", Toast.LENGTH_SHORT).show();
                    } else {
                        isDiningClicked = true;
//                    int resourceId = resources.getIdentifier("after_click", "drawable", getPackageName());
//                    int before = resources.getIdentifier("border_radius", "drawable", getPackageName());
//                    Drawable drawable = resources.getDrawable(resourceId);
                        diningAndDrinking.setBackgroundResource(R.drawable.after_click);
                        dining_and_drinking_textView.setTextColor(resources.getColor(R.color.white));

                        int border_radius = R.drawable.border_radius;
                        //Drawable drawable1 = resources.getDrawable(before);
                        landmarksAndOutdoorsLL.setBackgroundResource(border_radius);
                        landmarksAndOutdoors_textView.setTextColor(resources.getColor(R.color.black));

                        entertainmentLL.setBackgroundResource(border_radius);
                        entertainment_textView.setTextColor(resources.getColor(R.color.black));

                        isEntertainmentClicked = false;
                        isLandmarkClicked = false;

//                    Bundle bundle = new Bundle();
//                    bundle.putInt("key", 1);
//
//                    SubCategoryFragment fragment = new SubCategoryFragment();
//                    fragment.setArguments(bundle);
//
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.subCategory, fragment, "dining_and_drinking")
//                            .commit();

                        replaceSubCategoryFragment(1, "dining_and_drinking", true);
//                    View overlay=findViewById(R.id.overlay);
//                    overlay.setVisibility(View.VISIBLE);

                        System.out.println("REPLACE...");
                    }
                }
            });

            landmarksAndOutdoorsLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Resources resources = getResources();
                    if (isLandmarkClicked) {
                        Toast.makeText(getApplicationContext(), "Already Selected", Toast.LENGTH_SHORT).show();
                    } else {
                        isLandmarkClicked = true;
//                    int resourceId = resources.getIdentifier("after_click", "drawable", getPackageName());
//                    int before = resources.getIdentifier("border_radius", "drawable", getPackageName());
//                    Drawable drawable = resources.getDrawable(resourceId);
                        landmarksAndOutdoorsLL.setBackgroundResource(R.drawable.after_click);
                        landmarksAndOutdoors_textView.setTextColor(resources.getColor(R.color.white));

                        int border_radius = R.drawable.border_radius;

                        diningAndDrinking.setBackgroundResource(border_radius);
                        dining_and_drinking_textView.setTextColor(resources.getColor(R.color.black));

                        entertainmentLL.setBackgroundResource(border_radius);
                        entertainment_textView.setTextColor(resources.getColor(R.color.black));

                        isEntertainmentClicked = false;
                        isDiningClicked = false;

//                    Bundle bundle = new Bundle();
//                    bundle.putInt("key", 2);
//
//                    SubCategoryFragment fragment = new SubCategoryFragment();
//                    fragment.setArguments(bundle);
//
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.subCategory, fragment, "landmarks_and_outdoors")
//                            .commit();

                        replaceSubCategoryFragment(2, "landmarks_and_outdoors", true);
                    }
                }
            });

            entertainmentLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Resources resources = getResources();
                    if (isEntertainmentClicked) {
                        Toast.makeText(getApplicationContext(), "Already Selected", Toast.LENGTH_SHORT).show();
                    } else {
                        isEntertainmentClicked = true;
//                    int resourceId = resources.getIdentifier("after_click", "drawable", getPackageName());
//                    int before = resources.getIdentifier("border_radius", "drawable", getPackageName());
//                    Drawable drawable = resources.getDrawable(resourceId);
                        entertainmentLL.setBackgroundResource(R.drawable.after_click);
                        entertainment_textView.setTextColor(resources.getColor(R.color.white));

                        int border_radius = R.drawable.border_radius;

                        diningAndDrinking.setBackgroundResource(border_radius);
                        dining_and_drinking_textView.setTextColor(resources.getColor(R.color.black));

                        landmarksAndOutdoorsLL.setBackgroundResource(border_radius);
                        landmarksAndOutdoors_textView.setTextColor(resources.getColor(R.color.black));

                        isLandmarkClicked = false;
                        isDiningClicked = false;

                    /*
                    Bundle bundle = new Bundle();
                    bundle.putInt("key", 3);

                    SubCategoryFragment fragment = new SubCategoryFragment();
                    fragment.setArguments(bundle);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.subCategory, fragment, "entertainment")
                            .commit();
*/

                        replaceSubCategoryFragment(3, "entertainment_", true);
                    }
                }
            });
        }
    }

    private void replaceSubCategoryFragment(int flag, String tag, Boolean isCategory) {
        System.out.println("REPLACE...");
        FragmentTransaction transaction;
        if (isCategory) {
            SubCategoryFragment categoryFragment = new SubCategoryFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("key", flag);
            //bundle.putBoolean("fromNavDrawer",false);
            categoryFragment.setArguments(bundle);
            transaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_left);
            transaction.replace(R.id.subCategory, categoryFragment, tag);
            transaction.addToBackStack(null);
            //transaction.commit();
        } else {
            LinearLayout layout = findViewById(R.id.alpha_layout);
            // AboutMeFragment aboutMeFragment = new AboutMeFragment(layout);
            NavigationDrawerFragment navigationDrawerFragment = new NavigationDrawerFragment(getApplicationContext(), layout);
            transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_left);
            transaction.replace(R.id.about_me, navigationDrawerFragment, tag);
            //LinearLayout layout = findViewById(R.id.layout);
            layout.setAlpha((float) 0.1);
            isAboutMeActive = true;


        }

        // transaction.addToBackStack(null);
        transaction.commit();
    }

    public void updateCoordinates(LatLng selectedLatLng, int key) {

        if (selectedLatLng != null) {
            String coordinates = "Latitude: " + selectedLatLng.latitude + ", Longitude: " + selectedLatLng.longitude;
            if (key == 1) {
                String address = GeocodingHelper.getAddressFromCoordinates(getApplicationContext(), selectedLatLng.latitude, selectedLatLng.longitude);
                System.out.println("Address = " + address);

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


    /* @Override
     public void onBackPressed() {
         Fragment fragment1 = getSupportFragmentManager().findFragmentByTag("dining_and_drinking");

         if (fragment!= null) {
             getSupportFragmentManager().beginTransaction().remove(fragment).commit();
         } else {
             super.onBackPressed();
         }
     }*/
//   entertainment landmarks_and_outdoors dining_and_drinking
//    @Override
//    public void onBackPressed() {
//        Fragment aboutMe = fragmentManager.findFragmentByTag("about_me");
//        if (aboutMe != null) {
//            fragmentManager.beginTransaction()
//                    .setCustomAnimations(R.anim.slide_in_from_up, R.anim.slide_down)
//                    .remove(aboutMe)
//                    .commit();
//            alpha_layout.setVisibility(View.INVISIBLE);
//        } else {
//            Fragment dining_and_drinking = fragmentManager.findFragmentByTag("dining_and_drinking");
//            if (dining_and_drinking != null) {
//                removeFragment(dining_and_drinking);
//                System.out.println("dining_and_drinking REACHED...");
//            } else {
//                Fragment landmarks_and_outdoors = fragmentManager.findFragmentByTag("landmarks_and_outdoors");
//                if (landmarks_and_outdoors != null) {
//                    removeFragment(landmarks_and_outdoors);
//                    System.out.println("landmarks_and_outdoors REACHED...");
//                } else {
//                    Fragment entertainment = fragmentManager.findFragmentByTag("entertainment_");
//                    if (entertainment != null) {
//                        removeFragment(entertainment);
//                        System.out.println("entertainment REACHED...");
//                    } else {
//                        super.onBackPressed();
//                    }
//                }
//            }
//        }
//        if (fragmentManager.getBackStackEntryCount() > 0) {
//            fragmentManager.popBackStack();
//            if (isAboutMeActive) {
//                //LinearLayout layout = findViewById(R.id.layout);
//                //layout.setAlpha((float) 1);
//                alpha_layout.setVisibility(View.INVISIBLE);
//                isAboutMeActive = false;
//
//
//            }
//        } else {
//            super.onBackPressed();
//        }
    @Override
    public void onBackPressed() {
        Fragment aboutMe = fragmentManager.findFragmentByTag("about_me");
        Fragment dining_and_drinking = fragmentManager.findFragmentByTag("dining_and_drinking");
        Fragment landmarks_and_outdoors = fragmentManager.findFragmentByTag("landmarks_and_outdoors");
        Fragment entertainment = fragmentManager.findFragmentByTag("entertainment_");

        if (aboutMe != null) {
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_from_up, R.anim.slide_down)
                    .remove(aboutMe)
                    .commit();
            alpha_layout.setVisibility(View.INVISIBLE);
        } else if (entertainment != null) {
            removeFragment(entertainment);
            System.out.println("entertainment REACHED...");
            fragmentManager.popBackStack();
        } else if (landmarks_and_outdoors != null) {
            removeFragment(landmarks_and_outdoors);
            System.out.println("landmarks_and_outdoors REACHED...");
        } else if (dining_and_drinking != null) {
            removeFragment(dining_and_drinking);
            System.out.println("dining_and_drinking REACHED...");
        } else {
            super.onBackPressed();
        }
    }

    private void removeFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_left)
                .remove(fragment)
                .commit();
        fragmentManager.popBackStack();
    }

    public static void updateArrayList(ArrayList<String> temp) {
        categories = temp;
    }

    public static ArrayList<String> getCategoryCodes(ArrayList<String> arrayList, int category) {

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
                        stringArrayList.add(place);
                        break;
                }
            }
            return stringArrayList;

        } else if (category == 2) {
            //Landmarks and Outdoors

            for (int i = 0; i < arrayList.size(); i++) {
                String place = arrayList.get(i);
                switch (place) {
                    case "2":
                        return arrayList;
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
                    case "3":
                        return arrayList;
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