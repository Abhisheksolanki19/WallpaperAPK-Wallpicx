package com.merchpandas.wallpicx.Common;


import com.merchpandas.wallpicx.Database.Datasource.FavouritesRepository;
import com.merchpandas.wallpicx.Database.LocalDatabase.LocalDatabase;
import com.merchpandas.wallpicx.Model.ExploreItem;

public class Common {
    public static final String STR_COLLECTION_BACKGROUND = "CollectionBackground";
    public static final String STR_WALLPAPER = "Wallpapers";
    public static final int PERMISSION_REQUEST_CODE = 1000;
    public static final String STR_EXPLORE_BACKGROUND = "ExploreWallpaper";
    public static String COLLECTION_SELECTED;
    public static String COLLECTION_ID_SELECTED;
    //    public static ExploreItem select_background = new WallpaperItem();
    public static String EXPLORE_ID_SELECTED;
    public static LocalDatabase localDatabase;
    public static FavouritesRepository favouritesRepository;
    public static ExploreItem select_explore_background = new ExploreItem();
    public static String EXPLORE_NAME_SELECTED;


}
