package gangulwar.halfway;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class SubCategoryFragment extends Fragment implements View.OnClickListener {

    View view;

    ArrayList<String> categoriesArrayList = new ArrayList<>();
    private int totalButtonsClicked = 0;
    boolean isButtonsSelected = false;
    Button doneButton;

    public SubCategoryFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();

        int subCategory = args.getInt("key");
//        boolean fromNavDrawer = args.getBoolean("fromNavDrawer");

        if (subCategory == 1) {
            view = inflater.inflate(R.layout.fragment_sub_category, container, false);

//            View.OnClickListener buttonClickListener = new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // Check if the button is a toggle button (i.e., has a selected state)
//                    if (v instanceof Button) {
//                        Button button = (Button) v;
//                        if (!button.isSelected()) {
//                            button.setBackgroundResource(R.drawable.fragment_button_after);
//                            button.setSelected(true);
//                        } else {
//                            button.setBackgroundResource(R.drawable.fragment_button_before);
//                            button.setSelected(false);
//                        }
//                    }
//                }
//            };

            Button bar = view.findViewById(R.id.button_bar);
            bar.setOnClickListener(this);

            Button bakery = view.findViewById(R.id.button_bakery);
            bakery.setOnClickListener(this);

            Button cafe = view.findViewById(R.id.button_cafe);
            cafe.setOnClickListener(this);

            Button coffeeShop = view.findViewById(R.id.button_coffee_shop);
            coffeeShop.setOnClickListener(this);

            Button teaRoom = view.findViewById(R.id.button_tea_room);
            teaRoom.setOnClickListener(this);

            Button cafeteria = view.findViewById(R.id.button_cafeteria);
            cafeteria.setOnClickListener(this);

            Button dessertShop = view.findViewById(R.id.button_dessert);
            dessertShop.setOnClickListener(this);

            Button buttonAfgan = view.findViewById(R.id.button_afgan);
            buttonAfgan.setOnClickListener(this);

            Button buttonAfrican = view.findViewById(R.id.button_african);
            buttonAfrican.setOnClickListener(this);

            Button buttonAmerican = view.findViewById(R.id.button_american);
            buttonAmerican.setOnClickListener(this);

            Button buttonAsian = view.findViewById(R.id.button_asian);
            buttonAsian.setOnClickListener(this);

            Button buttonAustralian = view.findViewById(R.id.button_australian);
            buttonAustralian.setOnClickListener(this);

            Button buttonChinese = view.findViewById(R.id.button_chinese);
            buttonChinese.setOnClickListener(this);

            Button buttonDutch = view.findViewById(R.id.button_dutch);
            buttonDutch.setOnClickListener(this);

            Button buttonFrench = view.findViewById(R.id.button_french);
            buttonFrench.setOnClickListener(this);

            Button buttonIndian = view.findViewById(R.id.button_indian);
            buttonIndian.setOnClickListener(this);

            Button buttonItalian = view.findViewById(R.id.button_italian);
            buttonItalian.setOnClickListener(this);

            Button buttonMexican = view.findViewById(R.id.button_mexican);
            buttonMexican.setOnClickListener(this);

            Button bangladeshi = view.findViewById(R.id.button_bangladeshi);
            bangladeshi.setOnClickListener(this);

            Button fastFood = view.findViewById(R.id.button_fast_food);
            fastFood.setOnClickListener(this);

            doneButton = view.findViewById(R.id.dining_and_drinking_done_button);
            doneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (categoriesArrayList.isEmpty()) {
                        System.out.println("EMPTY ARRAYLIST...");
                        categoriesArrayList.add("1");
                        System.out.println(categoriesArrayList.get(0));
                    }
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_left).remove(SubCategoryFragment.this).commit();
                    MainActivity.updateArrayList(MainActivity.getCategoryCodes(categoriesArrayList, 1));


                }
            });

        } else if (subCategory == 2) {
            view = inflater.inflate(R.layout.fragment_landmarks_and_outdoors, container, false);

            TextView landmarkTextView = view.findViewById(R.id.subCategory_landmark_textview);

            if (MainActivity.isScreenWidthShort){
                landmarkTextView.setTextSize(45);

            }

            Button buttonCanal = view.findViewById(R.id.button_canal);
            Button buttonCastle = view.findViewById(R.id.button_castle);
            Button buttonDam = view.findViewById(R.id.button_dam);
            Button buttonFarm = view.findViewById(R.id.button_farm);
            Button buttonForest = view.findViewById(R.id.button_forest);
            Button buttonGarden = view.findViewById(R.id.button_garden);
            Button buttonHill = view.findViewById(R.id.button_hill);
            Button buttonHistoric = view.findViewById(R.id.button_historic);
            Button buttonLake = view.findViewById(R.id.button_lake);
            Button buttonMonument = view.findViewById(R.id.button_monument);
            Button buttonPark = view.findViewById(R.id.button_park);
            Button buttonRiver = view.findViewById(R.id.button_river);
            Button buttonStructure = view.findViewById(R.id.button_structure);
            doneButton = view.findViewById(R.id.landmarksAndOutdoors_done_button);

            buttonCanal.setOnClickListener(this);
            buttonCastle.setOnClickListener(this);
            buttonDam.setOnClickListener(this);
            buttonFarm.setOnClickListener(this);
            buttonForest.setOnClickListener(this);
            buttonGarden.setOnClickListener(this);
            buttonHill.setOnClickListener(this);
            buttonHistoric.setOnClickListener(this);
            buttonLake.setOnClickListener(this);
            buttonMonument.setOnClickListener(this);
            buttonPark.setOnClickListener(this);
            buttonRiver.setOnClickListener(this);
            buttonStructure.setOnClickListener(this);

            doneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (categoriesArrayList.isEmpty()) {
                        System.out.println("EMPTY ARRAYLIST...");
                        categoriesArrayList.add("2");
                        System.out.println(categoriesArrayList.get(0));
                    }

                    MainActivity.updateArrayList(MainActivity.getCategoryCodes(categoriesArrayList, 2));

                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_left).remove(SubCategoryFragment.this).commit();
                }
            });
        } else {
            view = inflater.inflate(R.layout.fragment_entertainment, container, false);

            TextView entertainment=view.findViewById(R.id.subCategory_entertainment_textview);
            if (MainActivity.isScreenWidthShort){
                entertainment.setTextSize(40);
            }

            Button buttonAmusementPark = view.findViewById(R.id.button_amusement_park);
            Button buttonArcade = view.findViewById(R.id.button_arcade);
            Button buttonCasino = view.findViewById(R.id.button_casino);

            if (MainActivity.isScreenWidthShort) {
                buttonArcade.setPadding(5, 5, 5, 5);
                buttonCasino.setPadding(5, 5, 5, 5);
            }

            Button buttonComedyClub = view.findViewById(R.id.button_comedy_club);
            Button buttonFair = view.findViewById(R.id.button_fair);
            Button buttonMovieTheater = view.findViewById(R.id.button_movie_theater);
            Button buttonMuseum = view.findViewById(R.id.button_museum);
            Button buttonNightClub = view.findViewById(R.id.button_night_club);
            Button buttonGamingCafe = view.findViewById(R.id.button_gaming_cafe);
            Button buttonPoolHall = view.findViewById(R.id.button_pool_hall);
            Button buttonVrCafe = view.findViewById(R.id.button_vr_cafe);
            Button buttonWaterPark = view.findViewById(R.id.button_water_park);
            Button buttonZoo = view.findViewById(R.id.button_zoo);


            buttonAmusementPark.setOnClickListener(this);
            buttonArcade.setOnClickListener(this);
            buttonCasino.setOnClickListener(this);
            buttonComedyClub.setOnClickListener(this);
            buttonFair.setOnClickListener(this);
            buttonMovieTheater.setOnClickListener(this);
            buttonMuseum.setOnClickListener(this);
            buttonNightClub.setOnClickListener(this);
            buttonGamingCafe.setOnClickListener(this);
            buttonPoolHall.setOnClickListener(this);
            buttonVrCafe.setOnClickListener(this);
            buttonWaterPark.setOnClickListener(this);
            buttonZoo.setOnClickListener(this);

            doneButton = view.findViewById(R.id.entertainment_done_button);
            doneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (categoriesArrayList.isEmpty()) {
                        System.out.println("EMPTY ARRAYLIST...");
                        categoriesArrayList.add("3");
                        System.out.println(categoriesArrayList.get(0));
                    }

                    MainActivity.updateArrayList(MainActivity.getCategoryCodes(categoriesArrayList, 3));

                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_left).remove(SubCategoryFragment.this).commit();
                }
            });
        }

        return view;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        Button button = (Button) v;

        if (!button.isSelected()) {
            button.setBackgroundResource(R.drawable.fragment_button_after);
            button.setTextColor(ContextCompat.getColor(v.getContext(), R.color.white));
            button.setSelected(true);
            String categoryName = button.getText().toString();
            categoriesArrayList.add(categoryName);
            System.out.println("CATEGORY NAME = " + categoryName);

            totalButtonsClicked++;
            if (totalButtonsClicked == 1) {
                // isButtonsSelected=true;
                doneButton.setText(R.string.done);
                System.out.println("Changed To Done");
            }
            if (totalButtonsClicked > 0) {

            }
//            if (totalButtonsClicked>0){
//                doneButton.setText("Done");
//                System.out.println("Changed To Done");
//            }
        } else {
            button.setBackgroundResource(R.drawable.fragment_button_before);
            button.setTextColor(ContextCompat.getColor(v.getContext(), R.color.black));
            button.setSelected(false);
            String categoryName = button.getText().toString();
            categoriesArrayList.remove(categoryName);
            System.out.println("CATEGORY NAME = " + categoryName);

            totalButtonsClicked--;

            if (totalButtonsClicked == 0) {
                //isButtonsSelected=false;
                doneButton.setText(R.string.select_all);
                System.out.println("Changed To Select All");
            }
//            if (totalButtonsClicked<=0){
//                doneButton.setText("Select All");
//                System.out.println("Changed To Select All");
//            }
        }

    }
}