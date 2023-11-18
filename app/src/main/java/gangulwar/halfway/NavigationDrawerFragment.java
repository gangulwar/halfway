package gangulwar.halfway;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


public class NavigationDrawerFragment extends Fragment {

    Context context;
    LinearLayout layout;

    public NavigationDrawerFragment(Context context, LinearLayout layout) {
        this.context = context;
        this.layout=layout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        LinearLayout dining = view.findViewById(R.id.nav_drawer_dining);
        LinearLayout landmarks = view.findViewById(R.id.nav_drawer_landmarks);
        LinearLayout entertainment = view.findViewById(R.id.nav_drawer_entertainment);


        dining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("Dining Click", "Dining item clicked");
                System.out.println("Clicked...");
                Toast.makeText(context, "Clicked Dining", Toast.LENGTH_LONG).show();

            }
        });

        landmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("Dining Click", "Dining item clicked");
                System.out.println("Clicked...");
                Toast.makeText(context, "Clicked Landmarks", Toast.LENGTH_LONG).show();

            }
        });

        entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("Dining Click", "Dining item clicked");
                System.out.println("Clicked...");
                Toast.makeText(context, "Clicked Entertainment", Toast.LENGTH_LONG).show();

            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(0,R.anim.slide_out_to_left,0,0);
                transaction.remove(NavigationDrawerFragment.this);
                transaction.commit();
                layout.setAlpha((float) 1);
            }
        });
        return view;
    }
}