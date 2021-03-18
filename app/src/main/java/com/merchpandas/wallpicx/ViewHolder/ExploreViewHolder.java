package com.merchpandas.wallpicx.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.merchpandas.wallpicx.Interface.ExploreItemClickListener;
import com.merchpandas.wallpicx.R;

public class ExploreViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView wallpaper, wall_bar;
    public ImageView fav_on, fav_off;
    public TextView wall_title;

    private ExploreItemClickListener exploreItemClickListener;


    public ExploreViewHolder(@NonNull View itemView) {
        super(itemView);
        wallpaper = itemView.findViewById(R.id.wallpaperImg);
        wall_bar = itemView.findViewById(R.id.wall_bar);
        wall_title = itemView.findViewById(R.id.wall_title);
        fav_on = itemView.findViewById(R.id.fav_on);
        fav_off = itemView.findViewById(R.id.fav_off);
//        progressBar = itemView.findViewById(R.id.progressbar);

        itemView.setOnClickListener(this);
    }

    public void setExploreItemClickListener(ExploreItemClickListener exploreItemClickListener) {
        this.exploreItemClickListener = exploreItemClickListener;
    }

    @Override
    public void onClick(View v) {
        exploreItemClickListener.onClick(v, getAdapterPosition());
    }

}
