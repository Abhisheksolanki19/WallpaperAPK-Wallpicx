package com.merchpandas.wallpicx.Database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.merchpandas.wallpicx.Database.Datasource.FavouritesRepository;

import java.util.List;

public class FavouriteViewModel extends AndroidViewModel {

    private final FavouritesRepository mFavouriteRepository;
    private final LiveData<List<Favourites>> mAllFavourites;

    public FavouriteViewModel(@NonNull Application application) {
        super(application);
        mFavouriteRepository = new FavouritesRepository(application);
        mAllFavourites = mFavouriteRepository.getAllFavourites();
    }

    public void insertFavourites(Favourites favourites) {
        mFavouriteRepository.insertFavourites(favourites);
    }

    public void deleteFavourites(Favourites favourites) {
        mFavouriteRepository.deleteFavourites(favourites);
    }

    public LiveData<List<Favourites>> getAllFavourites() {
        return mAllFavourites;
    }

    public void deleteAllFavourites() {
        mFavouriteRepository.deleteAllFavourites();
    }
}
