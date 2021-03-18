package com.merchpandas.wallpicx.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.merchpandas.wallpicx.Model.ExploreItem;

import java.util.ArrayList;
import java.util.List;

class WallpicxWidgetService extends RemoteViewsService {
    private List<ExploreItem> WallpaperList = new ArrayList<>();
    private Context mContext;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WallpaperRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    class WallpaperRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        public WallpaperRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            WallpaperList.clear();


        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            return null;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
