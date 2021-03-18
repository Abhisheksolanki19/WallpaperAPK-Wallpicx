package com.merchpandas.wallpicx.Database.LocalDatabase;

import androidx.lifecycle.LiveData;

import com.merchpandas.wallpicx.Database.Datasource.IFavouritesDataSource;
import com.merchpandas.wallpicx.Database.Favourites;

import java.util.List;

public class FavouritesDataSource implements IFavouritesDataSource {

    private static FavouritesDataSource instance;
    private FavouritesDAO favouritesDAO;

    public FavouritesDataSource(FavouritesDAO favouritesDAO) {
        this.favouritesDAO = favouritesDAO;
    }

    public static FavouritesDataSource getInstance(FavouritesDAO favouritesDAO) {
        if (instance == null)
            instance = new FavouritesDataSource(favouritesDAO);
        return instance;
    }

    @Override
    public LiveData<List<Favourites>> getAllFavourites() {
        return favouritesDAO.getAllFavourites();
    }

    @Override
    public int isFavorite(int id) {
        return favouritesDAO.isFavorite(id);
    }


    @Override
    public void insertFavourites(Favourites... favourites) {
        favouritesDAO.insertFavourites(favourites);
    }

    @Override
    public void updateFavourites(Favourites... favourites) {
        favouritesDAO.updateFavourites(favourites);
    }

    @Override
    public void deleteFavourites(Favourites... favourites) {
        favouritesDAO.deleteFavourites(favourites);
    }

    @Override
    public void deleteAllFavourites() {
        favouritesDAO.deleteAllFavourites();
    }
}
