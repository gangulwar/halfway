package gangulwar.halfway;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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

        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .into(holder.icon);

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.nameOfPlace);
            type = itemView.findViewById(R.id.typeOfPlace);
            distance = itemView.findViewById(R.id.distanceFromMiddle);
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
}
