package com.merchpandas.wallpicx.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.merchpandas.wallpicx.Common.Common;
import com.merchpandas.wallpicx.Database.Favourites;
import com.merchpandas.wallpicx.Interface.ExploreItemClickListener;
import com.merchpandas.wallpicx.Model.ExploreItem;
import com.merchpandas.wallpicx.R;
import com.merchpandas.wallpicx.ThemeActivity;
import com.merchpandas.wallpicx.ViewHolder.ExploreViewHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<ExploreViewHolder> {

    private Context context;
    private List<Favourites> favourites;
    private int dimensions = 500;

    public FavouriteAdapter(Context context, List<Favourites> favourites) {
        this.context = context;
        this.favourites = favourites;
    }

    public FavouriteAdapter() {
    }

    @NonNull
    @Override
    public ExploreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_exploreitem, parent, false);

        return new ExploreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ExploreViewHolder holder, final int position) {
        Picasso.with(context).load(favourites.get(position).getImageLink())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.wallpaper, new Callback() {
                    @Override
                    public void onSuccess() {
                        Picasso.with(context)
                                .load(favourites.get(position).getImageLink())
                                .resize(dimensions, dimensions)
                                .centerCrop()
                                .into(new Target() {
                                    @Override
                                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                        assert holder.wallpaper != null;
                                        holder.wallpaper.setImageBitmap(bitmap);
                                        Palette.from(bitmap)
                                                .generate(new Palette.PaletteAsyncListener() {
                                                    @Override
                                                    public void onGenerated(@Nullable Palette palette) {
                                                        Palette.Swatch textSwatch = palette.getVibrantSwatch();
                                                        int backgroundColor = ContextCompat.getColor(context,
                                                                R.color.colorPrimary);

                                                        if (textSwatch != null) {
                                                            backgroundColor = textSwatch.getRgb();
                                                        }
                                                        holder.wall_bar.setBackgroundColor(backgroundColor);
                                                    }
                                                });
                                    }

                                    @Override
                                    public void onBitmapFailed(Drawable errorDrawable) {

                                    }

                                    @Override
                                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                                    }
                                });

                    }

                    @Override
                    public void onError() {
                        Picasso.with(context).load(favourites.get(position).getImageLink())
                                .error(R.drawable.ic_terrain_black_24dp)
                                .into(holder.wallpaper, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        Picasso.with(context)
                                                .load(favourites.get(position).getImageLink())
                                                .resize(dimensions, dimensions)
                                                .centerCrop()
                                                .into(new Target() {
                                                    @Override
                                                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                                        assert holder.wallpaper != null;
                                                        holder.wallpaper.setImageBitmap(bitmap);
                                                        Palette.from(bitmap)
                                                                .generate(new Palette.PaletteAsyncListener() {
                                                                    @Override
                                                                    public void onGenerated(@Nullable Palette palette) {
                                                                        Palette.Swatch textSwatch = palette.getVibrantSwatch();
                                                                        int backgroundColor = ContextCompat.getColor(context,
                                                                                R.color.colorPrimary);

                                                                        if (textSwatch != null) {
                                                                            backgroundColor = textSwatch.getRgb();
                                                                        }
                                                                        holder.wall_bar.setBackgroundColor(backgroundColor);
                                                                    }
                                                                });
                                                    }

                                                    @Override
                                                    public void onBitmapFailed(Drawable errorDrawable) {

                                                    }

                                                    @Override
                                                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                                                    }
                                                });


                                    }

                                    @Override
                                    public void onError() {
                                        Log.e("ERROR_WALLPICX", "Couldn't fetch image");

                                    }
                                });

                    }
                });

        holder.wall_title.setText(favourites.get(position).getName());

        holder.setExploreItemClickListener(new ExploreItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(context, ThemeActivity.class);
                ExploreItem exploreItem = new ExploreItem();
                exploreItem.setName(favourites.get(position).getName());
                exploreItem.setCollectionId(favourites.get(position).getCategoryId());
                exploreItem.setImageLink(favourites.get(position).getImageLink());
                Common.select_explore_background = exploreItem;
                context.startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return favourites.size();
    }

    public void updateEventList(List<Favourites> eventPojoArrayList) {
        favourites = eventPojoArrayList;
        notifyDataSetChanged();
    }

}
