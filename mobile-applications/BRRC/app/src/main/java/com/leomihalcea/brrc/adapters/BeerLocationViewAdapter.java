package com.leomihalcea.brrc.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.leomihalcea.brrc.EditBeerLocationActivity;
import com.leomihalcea.brrc.MainActivity;
import com.leomihalcea.brrc.R;
import com.leomihalcea.brrc.model.BeerLocation;

import java.util.ArrayList;

public class BeerLocationViewAdapter extends RecyclerView.Adapter<BeerLocationViewAdapter.ViewHolder> {

    private ArrayList<BeerLocation> beerLocations = new ArrayList<>();
    private Context context;

    public BeerLocationViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.beer_location_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.beerName.setText(beerLocations.get(position).getBeerName());
        holder.beerType.setText(beerLocations.get(position).getBeerType());
        holder.pubName.setText(beerLocations.get(position).getPubName());
        holder.price.setText(String.valueOf(beerLocations.get(position).getPrice()) + " Ron");

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditBeerLocationActivity.class);
                intent.putExtra("beerLocation", beerLocations.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return beerLocations.size();
    }

    public void setBeerLocations(ArrayList<BeerLocation> beerLocations) {
        this.beerLocations = beerLocations;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private TextView beerName;
        private TextView beerType;
        private TextView pubName;
        private TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            beerName = itemView.findViewById(R.id.beerLocationName);
            beerType = itemView.findViewById(R.id.beerType);
            pubName = itemView.findViewById(R.id.pubName);
            price = itemView.findViewById(R.id.beerPrice);
        }
    }
}
