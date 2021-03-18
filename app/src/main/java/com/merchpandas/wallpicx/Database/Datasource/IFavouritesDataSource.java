package com.merchpandas.wallpicx.Database.Datasource;

import androidx.lifecycle.LiveData;

import com.merchpandas.wallpicx.Database.Favourites;

import java.util.List;

public interface IFavouritesDataSource {
    LiveData<List<Favourites>> getAllFavourites();

    int isFavorite(int id);

    void insertFavourites(Favourites... favourites);

    void updateFavourites(Favourites... favourites);

    void deleteFavourites(Favourites... favourites);

    void deleteAllFavourites();
}
