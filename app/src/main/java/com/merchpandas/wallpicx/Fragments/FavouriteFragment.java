package com.merchpandas.wallpicx.Fragments;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.merchpandas.wallpicx.Adapters.FavouriteAdapter;
import com.merchpandas.wallpicx.Common.Common;
import com.merchpandas.wallpicx.Database.Datasource.FavouritesRepository;
import com.merchpandas.wallpicx.Database.FavouriteViewModel;
import com.merchpandas.wallpicx.Database.Favourites;
import com.merchpandas.wallpicx.Database.LocalDatabase.LocalDatabase;
import com.merchpandas.wallpicx.R;

import java.util.ArrayList;
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

public class FavouriteFragment extends Fragment {


    private static final String LOG_VALUE = FavouriteFragment.class.getSimpleName();
    @SuppressLint("StaticFieldLeak")
    private static FavouriteFragment INSTANCE = null;
    //Init
    public ImageView recentImage;
    public TextView recentTxt;
    //RoomDatabase
    CompositeDisposable compositeDisposable;
    FavouritesRepository favouritesRepository;
    LocalDatabase database;
    private FavouriteViewModel favouriteViewModel;
    private Application application;
    private Context context;
    private List<Favourites> favouritesList;
    private FavouriteAdapter adapter;

    public FavouriteFragment(Context context) {
        this.context = context;

        //Init RoomDatabase
        compositeDisposable = new CompositeDisposable();
        database = LocalDatabase.getInstance(context);
        favouritesRepository = new FavouritesRepository(application);
    }

    public static FavouriteFragment getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new FavouriteFragment(context);
        return INSTANCE;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favourite, container, false);

        favouriteViewModel = new FavouriteViewModel(application);

        RecyclerView recyclerView = v.findViewById(R.id.recyclerView_favourite);
        recentImage = v.findViewById(R.id.recent_image);
        recentTxt = v.findViewById(R.id.recent_txt);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        favouritesList = new ArrayList<>();
        adapter = new FavouriteAdapter(context, favouritesList);

        recyclerView.setAdapter(adapter);

        addfavourite();


        return v;
    }


    private void deleteAllFavourites() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) {
                Favourites favourites = new Favourites(
                        Common.select_explore_background.getImageLink(),
                        Common.select_explore_background.getName(),
                        Common.select_explore_background.getCollectionId(),
                        String.valueOf(System.currentTimeMillis())
                );
                favouritesRepository.deleteAllFavourites();
                e.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e("ERROR", Objects.requireNonNull(throwable.getMessage()));
                    }
                }, new Action() {
                    @Override
                    public void run() {

                    }
                });
        compositeDisposable.add(disposable);
    }


    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        deleteAllFavourites();
        super.onDestroy();
    }

    private void addfavourite() {

        favouriteViewModel = ViewModelProviders.of(this).get(FavouriteViewModel.class);
        favouriteViewModel.getAllFavourites().observe(getViewLifecycleOwner(), new Observer<List<Favourites>>() {
            @Override
            public void onChanged(List<Favourites> favourites) {
                adapter.updateEventList(favourites);
                Log.i(LOG_VALUE, "RecyclerView Updated");

            }

        });

    }
}
