package gangulwar.halfway;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.ViewHolder> {
    Context context;

    ArrayList<PlaceModal> arrayList;

    public PlaceListAdapter(Context context, ArrayList<PlaceModal> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.place_info, parent, false);
        PlaceListAdapter.ViewHolder viewHolder = new PlaceListAdapter.ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull PlaceListAdapter.ViewHolder holder, int position) {

        String imageUrl = arrayList.get(position).iconUrl;
        String nameOfPlace = arrayList.get(position).nameOfPlace;
        String typeOfPlace = arrayList.get(position).typeOfPlace;
        String distanceFromMiddle = arrayList.get(position).distance + " meters";

        if (Objects.equals(imageUrl, "Error")) {
            Glide.with(context)
                    .asGif()
                    .load(R.drawable.cringe)
                    .into(holder.icon);
        } else if (Objects.equals(nameOfPlace, "Error")) {
            Glide.with(context)
                    .asGif()
                    .load(imageUrl)
                    .into(holder.icon);
        } else {

            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.loading)
                    .into(holder.icon);
        }

//        if (nameOfPlace.length() > 15) {
//            holder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
//        } else {
//            holder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
//        }
        changeTextSize(holder.name, nameOfPlace, 1);
        holder.name.setText(nameOfPlace);
        changeTextSize(holder.type, typeOfPlace, 2);
//        if (typeOfPlace.length() > 15) {
//            holder.type.setTextSize(TypedValue.COMPLEX_UNIT_SP,18 );
//        } else {
//            holder.type.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
//        }

        holder.type.setText(typeOfPlace);
        holder.distance.setText(distanceFromMiddle);
        TextView textView = holder.viewOnMap;
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("MAP STARTING...");
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + arrayList.get(position).lat + "," + arrayList.get(position).lon);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                mapIntent.setPackage("com.google.android.apps.maps");


                if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(mapIntent);
                }
            }
        });

        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(arrayList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView name;
        TextView type;
        TextView distance;
        TextView viewOnMap;
        ImageView shareButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.nameOfPlace);
            type = itemView.findViewById(R.id.typeOfPlace);
            distance = itemView.findViewById(R.id.distanceFromMiddle);
            viewOnMap = itemView.findViewById(R.id.viewMap);
            shareButton = itemView.findViewById(R.id.share_button);
        }

    }

    public static void changeTextSize(TextView textView, String text, int flag) {
        if (flag == 1) {
            if (text.length() > 15) {
                textView.setTextSize(25);
            }
        } else {
            if (text.length() > 15) {
                textView.setTextSize(18);
            }
        }
    }

    public void share(PlaceModal placeModal) {

        String nameOfPlace = placeModal.nameOfPlace;
        String address = placeModal.address.replace(" ", "+");
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        placeModal.lat;
//        placeModal.lon;
        //String link = "https://www.google.com/maps?saddr&daddr=" + placeModal.lat + "," + placeModal.lon;
        String locationLink = "https://www.google.com/maps/dir/?api=1&destination=" + address;

        String message = "Hey! Lets meet here today \nPlace Name = " + nameOfPlace + "\nAddress = " + address.replace("+", " ") + "\nView on Google Maps = " + locationLink + "\nSearched on Halfway!\n\t ~By Aarsh Gangulwar";
        shareIntent.setType("text/plain");
        String textToShare = "Hello, this is the text I want to share!";
        shareIntent.putExtra(Intent.EXTRA_TEXT, message);

        shareIntent.putExtra(Intent.EXTRA_SUBJECT, nameOfPlace);

        context.startActivity(Intent.createChooser(shareIntent, "Share via"));

    }
}
