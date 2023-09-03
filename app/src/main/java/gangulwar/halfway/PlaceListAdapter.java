package gangulwar.halfway;

import android.content.Context;
import android.content.Intent;
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

        String imageUrl = arrayList.get(position).iconLink;
        String nameOfPlace = arrayList.get(position).name;
        String typeOfPlace = arrayList.get(position).type;
        String distanceFromMiddle = arrayList.get(position).distanceFromMiddle;

        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.entertainment)
                .error(R.drawable.icon)
                .into(holder.icon);

        holder.name.setText(nameOfPlace);
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

    public static void shortIt(String txt, ViewHolder holder) {

        if (txt.length() > 14) {

        }

    }
}
