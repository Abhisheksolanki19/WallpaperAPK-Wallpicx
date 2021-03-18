package com.merchpandas.wallpicx.Fragments;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.merchpandas.wallpicx.Common.Common;
import com.merchpandas.wallpicx.Database.Datasource.FavouritesRepository;
import com.merchpandas.wallpicx.Database.Favourites;
import com.merchpandas.wallpicx.Database.LocalDatabase.FavouritesDAO;
import com.merchpandas.wallpicx.Database.LocalDatabase.LocalDatabase;
import com.merchpandas.wallpicx.Interface.ExploreItemClickListener;
import com.merchpandas.wallpicx.Model.ExploreItem;
import com.merchpandas.wallpicx.R;
import com.merchpandas.wallpicx.ThemeActivity;
import com.merchpandas.wallpicx.ViewHolder.ExploreViewHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ExploreFragment extends Fragment {


    //Room Database
    CompositeDisposable compositeDisposable;
    int flag = 0;
    String mid, title, overView, date, rating;
    FavouritesRepository favouritesRepository;
    FavouritesDAO favouriteDao;
    private int dimensions = 500;
    private Application application;
    private ProgressBar progressBar;
    private FirebaseDatabase database;
    private DatabaseReference exploreBackground;
    private FirebaseRecyclerOptions<ExploreItem> options;
    private FirebaseRecyclerAdapter<ExploreItem, ExploreViewHolder> adapter;
    private RecyclerView recyclerView;
    private Favourites[] favourites;
    private List<Favourites> allFavouriteList;


    public ExploreFragment() {
        database = FirebaseDatabase.getInstance();
        exploreBackground = database.getReference(Common.STR_EXPLORE_BACKGROUND);

        options = new FirebaseRecyclerOptions.Builder<ExploreItem>()
                .setQuery(exploreBackground, ExploreItem.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<ExploreItem, ExploreViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final ExploreViewHolder exploreViewHolder, int position, @NonNull final ExploreItem exploreItem) {
                Picasso.with(getActivity())
                        .load(exploreItem.getImageLink()).fit()
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(exploreViewHolder.wallpaper, new Callback() {
                            @Override
                            public void onSuccess() {
                                Picasso.with(getActivity())
                                        .load(exploreItem.getImageLink())
                                        .resize(dimensions, dimensions)
                                        .centerCrop()
                                        .into(new Target() {
                                            @Override
                                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                                assert exploreViewHolder.wallpaper != null;
                                                exploreViewHolder.wallpaper.setImageBitmap(bitmap);
                                                Palette.from(bitmap)
                                                        .generate(new Palette.PaletteAsyncListener() {
                                                            @Override
                                                            public void onGenerated(@Nullable Palette palette) {
                                                                Palette.Swatch textSwatch = palette.getVibrantSwatch();
                                                                int backgroundColor = ContextCompat.getColor(getContext(),
                                                                        R.color.colorPrimary);

                                                                if (textSwatch != null) {
                                                                    backgroundColor = textSwatch.getRgb();
                                                                }
                                                                exploreViewHolder.wall_bar.setBackgroundColor(backgroundColor);
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
                                //Try again online if cache failed
                                Picasso.with(getActivity())
                                        .load(exploreItem.getImageLink())
                                        .error(R.drawable.ic_terrain_black_24dp)
                                        .into(exploreViewHolder.wallpaper, new Callback() {
                                            @Override
                                            public void onSuccess() {
                                                Picasso.with(getActivity())
                                                        .load(exploreItem.getImageLink())
                                                        .resize(dimensions, dimensions)
                                                        .centerCrop()
                                                        .into(new Target() {
                                                            @Override
                                                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                                                assert exploreViewHolder.wallpaper != null;
                                                                exploreViewHolder.wallpaper.setImageBitmap(bitmap);
                                                                Palette.from(bitmap)
                                                                        .generate(new Palette.PaletteAsyncListener() {
                                                                            @Override
                                                                            public void onGenerated(@Nullable Palette palette) {
                                                                                Palette.Swatch textSwatch = palette.getVibrantSwatch();
                                                                                int backgroundColor = ContextCompat.getColor(getContext(),
                                                                                        R.color.colorPrimary);

                                                                                if (textSwatch != null) {
                                                                                    backgroundColor = textSwatch.getRgb();
                                                                                }
                                                                                exploreViewHolder.wall_bar.setBackgroundColor(backgroundColor);
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

                exploreViewHolder.wall_title.setText(exploreItem.getName());
                progressBar.setVisibility(View.GONE);

                exploreViewHolder.setExploreItemClickListener(new ExploreItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), ThemeActivity.class);
                        Common.select_explore_background = exploreItem;
                        Common.EXPLORE_NAME_SELECTED = exploreItem.getName();
                        startActivity(intent);

                    }
                });

            }



            @NonNull
            @Override
            public ExploreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_exploreitem, parent, false);
                return new ExploreViewHolder(itemView);
            }
        };

    }


    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_explore, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        progressBar = v.findViewById(R.id.progressbar);

        //Init RoomDatabase
        compositeDisposable = new CompositeDisposable();
        LocalDatabase database = LocalDatabase.getInstance(getContext());
        favouritesRepository = new FavouritesRepository(application);


        Context ctx = getContext();
        if (isNetworkAvailable(ctx)) {
            Toast.makeText(ctx, "Wallpapers Loading...", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(getContext(), "Check your Network connection", Toast.LENGTH_LONG).show();
        }

        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        if (adapter != null) {
            adapter.startListening();
        }

        setExplore();

        return v;


    }


    private void setExplore() {
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null)
            adapter.startListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null)
            adapter.startListening();
    }

    @Override
    public void onStop() {
        if (adapter != null)
            adapter.stopListening();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (adapter != null)
            adapter.stopListening();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
