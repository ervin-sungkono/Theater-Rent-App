package com.example.uasmobileprogramming.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uasmobileprogramming.R;
import com.example.uasmobileprogramming.models.Theater;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TheaterAdapter extends RecyclerView.Adapter<TheaterAdapter.viewHolder> {
    private ArrayList<Theater> theaterList;
    private Context context;

    public TheaterAdapter(ArrayList<Theater> theaterList, Context context) {
        this.theaterList = theaterList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.theater_item_layout, parent, false);
        return new TheaterAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Theater t = theaterList.get(position);
        holder.tvTitle.setText(t.getCinema().replace("Cinema ", "") + " - " + t.getTheaterInfo().getName());
        holder.tvMovieName.setText(t.getMovie().getTitle());
        holder.tvDuration.setText("Duration: " + t.getRentDuration());
        holder.tvPrice.setText("Price: " + String.format("%,d", t.getPrice()));
        holder.tvDate.setText(t.getDate());
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(t.getTheaterInfo().getImageUrl())
                .resize(225,300)
                .placeholder(R.drawable.load_image)
                .error(R.drawable.no_image_placeholder_svg)
                .into(holder.ivThumbnail);
    }

    @Override
    public int getItemCount() {
        return theaterList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle, tvMovieName, tvDuration, tvDate, tvPrice;
        ImageView ivThumbnail;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_theater_name);
            tvMovieName = itemView.findViewById(R.id.tv_movie_title);
            tvDuration = itemView.findViewById(R.id.tv_duration);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvPrice = itemView.findViewById(R.id.tv_price);
            ivThumbnail = itemView.findViewById(R.id.iv_thumbnail);
        }
    }
}
