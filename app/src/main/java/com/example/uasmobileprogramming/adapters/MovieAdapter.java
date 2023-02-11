package com.example.uasmobileprogramming.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uasmobileprogramming.R;
import com.example.uasmobileprogramming.activities.DetailActivity;
import com.example.uasmobileprogramming.models.Movie;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.viewHolder> {
    private ArrayList<Movie> movieList;
    private Context context;

    public MovieAdapter(ArrayList<Movie> movieList, Context context){
        this.movieList = movieList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Movie m = movieList.get(position);
        holder.tvName.setText(m.getTitle());
        holder.tvIMDBRating.setText(m.getImDbRating() + "/10");
        int runtimeMins = Integer.parseInt(m.getRuntimeMins());
        holder.tvDuration.setText((runtimeMins/60) + "h" + (runtimeMins % 60) + "m");
        holder.tvContentRating.setText(m.getContentRating());
        holder.tvCategory.setText(m.getGenres());
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(m.getImageUrl())
                .resize(225,300)
                .placeholder(R.drawable.load_image)
                .error(R.drawable.no_image_placeholder_svg)
                .into(holder.movieThumbnail);

        holder.detailBtn.setOnClickListener(v -> {
            Intent detailIntent = new Intent(context, DetailActivity.class);
            detailIntent.putExtra("movie_details", m);
            context.startActivity(detailIntent);
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void filterList(ArrayList<Movie> filteredList){
        movieList = filteredList;
        notifyDataSetChanged();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvIMDBRating, tvDuration, tvContentRating, tvCategory;
        ImageView movieThumbnail;
        Button detailBtn;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvIMDBRating = itemView.findViewById(R.id.tv_imdb_rating);
            tvDuration = itemView.findViewById(R.id.tv_duration);
            tvContentRating = itemView.findViewById(R.id.tv_content_rating);
            tvCategory = itemView.findViewById(R.id.tv_category);
            movieThumbnail = itemView.findViewById(R.id.iv_thumbnail);
            detailBtn = itemView.findViewById(R.id.detail_btn);
        }
    }
}
