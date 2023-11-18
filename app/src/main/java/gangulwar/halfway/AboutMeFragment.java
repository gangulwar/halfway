package gangulwar.halfway;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AboutMeFragment extends Fragment {
    ImageView github;
    ImageView linkedin;
    ImageView instagram;
    View layout;

    public AboutMeFragment(View layout) {
        this.layout = layout;
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_me, container, false);
        github = view.findViewById(R.id.github);
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uriString = "https://github.com/gangulwar";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(uriString));
                startActivity(intent);
            }
        });

        linkedin = view.findViewById(R.id.linkedin);
        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uriString = "https://www.linkedin.com/in/aarsh-gangulwar-76ba3928b/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(uriString));
                startActivity(intent);
            }
        });

        instagram = view.findViewById(R.id.instagram);
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uriString = "https://www.instagram.com/aarsh_23/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(uriString));
                startActivity(intent);
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.INVISIBLE);
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_from_up, R.anim.slide_down)
                        .remove(AboutMeFragment.this)
                        .commit();
            }
        });
        return view;
    }
}