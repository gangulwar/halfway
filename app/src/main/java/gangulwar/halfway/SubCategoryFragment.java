package gangulwar.halfway;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SubCategoryFragment extends Fragment {

    View view;
    Button bar;
    Button bakery;
    Button cafe;
    Button coffeeShop;
    Button teaRoom;
    Button cafeteria;
    Button dessertShop;


    public SubCategoryFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();

        int subCategory = args.getInt("key");
        if (subCategory == 1) {
            view = inflater.inflate(R.layout.fragment_sub_category, container, false);
            bar=view.findViewById(R.id.button_bar);
            bar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!bar.isSelected()){
                        bar.setBackgroundResource(R.drawable.fragment_button_after);
                        bar.setSelected(true);
                    }else{
                        bar.setBackgroundResource(R.drawable.fragment_button_before);
                        bar.setSelected(false);
                    }

                }
            });
            bakery=view.findViewById(R.id.button_bakery);
            cafe=view.findViewById(R.id.button_cafe);
            coffeeShop=view.findViewById(R.id.button_coffee_shop);
            teaRoom=view.findViewById(R.id.button_tea_room);
            cafeteria=view.findViewById(R.id.button_cafeteria);
            dessertShop=view.findViewById(R.id.button_dessert);

        } else if (subCategory == 2) {
            view = inflater.inflate(R.layout.fragment_landmarks_and_outdoors, container, false);
        } else {
            view = inflater.inflate(R.layout.fragment_entertainment, container, false);
        }

        return view;
    }

}