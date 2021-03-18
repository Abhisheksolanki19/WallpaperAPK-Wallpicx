package com.merchpandas.wallpicx.Database.LocalDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.merchpandas.wallpicx.Database.Favourites;

import java.util.List;

@Dao
public interface FavouritesDAO {
    @Query("SELECT EXISTS (SELECT 1 FROM favourites WHERE categoryId=:id)")
    int isFavorite(int id);

    @Query("SELECT * FROM favourites ORDER BY saveTime DESC LIMIT 10")
    LiveData<List<Favourites>> getAllFavourites();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFavourites(Favourites... favourites);

    @Update
    void updateFavourites(Favourites... favourites);

    @Delete
    void deleteFavourites(Favourites... favourites);

    @Query("DELETE FROM favourites")
    void deleteAllFavourites();
}
