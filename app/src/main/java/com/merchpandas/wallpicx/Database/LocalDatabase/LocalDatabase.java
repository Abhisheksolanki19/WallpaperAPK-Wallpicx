package com.merchpandas.wallpicx.Database.LocalDatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.merchpandas.wallpicx.Database.Favourites;

@Database(entities = Favourites.class, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "WallpicxWallpaper";
    private static LocalDatabase instance;
    public static RoomDatabase.Callback PopulateDb = new RoomDatabase.Callback() {

        //Called when database is created for the first time
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            new PopulateDbAsyncTask(instance).execute();
            super.onCreate(db);
        }
    };

    public static synchronized LocalDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    LocalDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration() //To prevent crash while incrementing version.
                    .build();
        }
        return instance;
    }

    public abstract FavouritesDAO favouritesDAO();

    public static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private final FavouritesDAO favouriteDao;

        public PopulateDbAsyncTask(LocalDatabase db) {
            this.favouriteDao = db.favouritesDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            favouriteDao.insertFavourites(new Favourites("ImageLink 1", "Name 1", 1, "1"));
            favouriteDao.insertFavourites(new Favourites("ImageLink 2", "Name 2", 2, "2"));
            favouriteDao.insertFavourites(new Favourites("ImageLink 3", "Name 3", 3, "3"));

            return null;
        }
    }


}
