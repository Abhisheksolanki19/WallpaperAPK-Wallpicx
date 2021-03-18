package com.merchpandas.wallpicx.Database.Datasource;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.merchpandas.wallpicx.Database.Favourites;
import com.merchpandas.wallpicx.Database.LocalDatabase.FavouritesDAO;
import com.merchpandas.wallpicx.Database.LocalDatabase.LocalDatabase;

import java.util.List;

public class FavouritesRepository {
    private final FavouritesDAO mFavouriteDao;
    private final LiveData<List<Favourites>> mAllFavourites;

    public FavouritesRepository(Application application) {
        LocalDatabase database = LocalDatabase.getInstance(application);
        mFavouriteDao = database.favouritesDAO();
        mAllFavourites = mFavouriteDao.getAllFavourites();
    }

    public LiveData<List<Favourites>> getAllFavourites() {
        return mAllFavourites;
    }

    public void insertFavourites(Favourites... favourites) {
        new insertAsyncTask(mFavouriteDao).execute(favourites);

    }


    public void deleteFavourites(Favourites... favourites) {
        new deleteAsyncTask(mFavouriteDao).execute(favourites);

    }


    public void deleteAllFavourites() {
        mFavouriteDao.deleteAllFavourites();
    }

    private static class insertAsyncTask extends AsyncTask<Favourites, Void, Void> {
        final FavouritesDAO cFavouriteDao;

        public insertAsyncTask(FavouritesDAO favouriteDao) {
            cFavouriteDao = favouriteDao;
        }

        @Override
        protected Void doInBackground(final Favourites... favourites) {
            cFavouriteDao.insertFavourites(favourites[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Favourites, Void, Void> {
        final FavouritesDAO cFavouriteDao;

        public deleteAsyncTask(FavouritesDAO favouriteDao) {
            cFavouriteDao = favouriteDao;
        }

        @Override
        protected Void doInBackground(final Favourites... favourites) {
            cFavouriteDao.deleteFavourites(favourites[0]);
            return null;
        }
    }
}
