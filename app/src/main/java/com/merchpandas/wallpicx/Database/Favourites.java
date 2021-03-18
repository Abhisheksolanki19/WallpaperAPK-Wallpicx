package com.merchpandas.wallpicx.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourites")
public class Favourites {


    @ColumnInfo(name = "name")
    @NonNull
    public String name;
    @ColumnInfo(name = "imageLink")
    @NonNull
    private String imageLink;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "categoryId")
    @NonNull
    private int categoryId;

    @ColumnInfo(name = "saveTime")
    private String saveTime;

    public Favourites(@NonNull String imageLink, @NonNull String name, int categoryId, String saveTime) {
        this.imageLink = imageLink;
        this.name = name;
        this.categoryId = categoryId;
        this.saveTime = saveTime;
    }

    public Favourites() {
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(@NonNull String imageLink) {
        this.imageLink = imageLink;
    }

    @NonNull
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(@NonNull int categoryId) {
        this.categoryId = categoryId;
    }

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }
}
