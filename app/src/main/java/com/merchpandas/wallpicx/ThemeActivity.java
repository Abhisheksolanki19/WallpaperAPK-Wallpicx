package com.merchpandas.wallpicx;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.palette.graphics.Palette;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.merchpandas.wallpicx.Adapters.FavouriteAdapter;
import com.merchpandas.wallpicx.Common.Common;
import com.merchpandas.wallpicx.Database.Datasource.FavouritesRepository;
import com.merchpandas.wallpicx.Database.Favourites;
import com.merchpandas.wallpicx.Database.LocalDatabase.LocalDatabase;
import com.merchpandas.wallpicx.Helper.SaveImageHelper;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import dmax.dialog.SpotsDialog;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ThemeActivity extends AppCompatActivity {


    CompositeDisposable compositeDisposable;
    FavouritesRepository favouritesRepository;

    BottomSheetDialog popUpDialog;
    int flag = 0;
    FrameLayout main_frame;
    ImageView bg_Image;
    int dimensions = 500;
    NestedScrollView nestedScroll;
    LinearLayout save_btn, set_btn,  titlebar_back, snackbar_view;
    CollapsingToolbarLayout collapsing_toolbar;
    TextView wall_title;

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());

            try {
                wallpaperManager.setBitmap(bitmap);
                Snackbar.make(main_frame, "Home screen Wallpaper applied successfully", Snackbar.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };
    private Target target2 = new Target() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            WallpaperManager wp = WallpaperManager.getInstance(getApplicationContext());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    wp.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };
    private Target target3 = new Target() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());

            try {
                wallpaperManager.setBitmap(bitmap);
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Common.PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    AlertDialog dialog = new SpotsDialog.Builder().setContext(ThemeActivity.this).build();
                    dialog.show();
                    dialog.setMessage("Please wait...");

                    String filename = UUID.randomUUID().toString() + ".png";
                    Picasso.with(getBaseContext())
                            .load(Common.select_explore_background.getImageLink())
                            .into(new SaveImageHelper(getBaseContext(), dialog,
                                    getApplicationContext().getContentResolver(), filename,
                                    "Wallpicx Live Wallpaper Image"));
                } else
                    Toast.makeText(this, "You need to accept this permission to download Wallpaper", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        popUpDialog = new BottomSheetDialog(this);
        bg_Image = findViewById(R.id.bg_image);
        set_btn = findViewById(R.id.btn_apply);
        save_btn = findViewById(R.id.btn_save);
        main_frame = findViewById(R.id.main_frame);
        wall_title = findViewById(R.id.wall_title);
        snackbar_view = findViewById(R.id.snackbar_view);
        nestedScroll = findViewById(R.id.nestedScroll);
        titlebar_back = findViewById(R.id.titlebar_back);


        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        collapsing_toolbar = findViewById(R.id.collapsing_toolbar);


        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        //Init RoomDatabase
        compositeDisposable = new CompositeDisposable();
        LocalDatabase database = LocalDatabase.getInstance(getApplicationContext());
        favouritesRepository = new FavouritesRepository(getApplication());


        Picasso.with(this)
                .load(Common.select_explore_background.getImageLink())
                .into(bg_Image);
        wall_title.setText(Common.EXPLORE_NAME_SELECTED);

        Picasso.with(this)
                .load(Common.select_explore_background.getImageLink()).resize(dimensions, dimensions)
                .centerCrop()
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        assert bg_Image != null;
                        bg_Image.setImageBitmap(bitmap);
                        Palette.from(bitmap)
                                .generate(new Palette.PaletteAsyncListener() {
                                    @Override
                                    public void onGenerated(@Nullable Palette palette) {
                                        Palette.Swatch textSwatch = palette.getVibrantSwatch();
                                        Palette.Swatch textSwatch2 = palette.getDarkVibrantSwatch();
                                        int backgroundColor = ContextCompat.getColor(getApplicationContext(),
                                                R.color.colorPrimary);
                                        int backgroundColor2 = ContextCompat.getColor(getApplicationContext(),
                                                R.color.colorPrimaryDark);

                                        if (textSwatch != null) {
                                            backgroundColor = textSwatch.getRgb();
                                        }
                                        if (textSwatch2 != null) {
                                            backgroundColor2 = textSwatch2.getRgb();
                                        }
                                        ValueAnimator anim = ValueAnimator.ofInt(R.color.colorPrimary, backgroundColor);
                                        anim.setEvaluator(new ArgbEvaluator());
                                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                            @Override
                                            public void onAnimationUpdate(ValueAnimator animation) {
                                                nestedScroll.setBackgroundColor((Integer) animation.getAnimatedValue());
                                            }
                                        });
                                        anim.setDuration(1000);
                                        anim.start();

                                        ValueAnimator anim2 = ValueAnimator.ofInt(R.color.colorPrimaryDark, backgroundColor2);
                                        anim2.setEvaluator(new ArgbEvaluator());
                                        anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                            @Override
                                            public void onAnimationUpdate(ValueAnimator animation) {
                                                titlebar_back.setBackgroundColor((Integer) animation.getAnimatedValue());
                                            }
                                        });
                                        anim2.setDuration(1000);
                                        anim2.start();
                                    }
                                });
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });


        set_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(ThemeActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Common.PERMISSION_REQUEST_CODE);
                } else {
                    AlertDialog dialog = new SpotsDialog.Builder().setContext(ThemeActivity.this).build();
                    dialog.setMessage("Please wait...");
                    dialog.show();

                    String filename = UUID.randomUUID().toString() + ".png";
                    Picasso.with(getBaseContext())
                            .load(Common.select_explore_background.getImageLink())
                            .into(new SaveImageHelper(getBaseContext(),
                                    dialog,
                                    getApplicationContext().getContentResolver(), filename,
                                    "Wallpicx Live Wallpaper Image"));

                }
                Toast.makeText(getApplicationContext(), "Download succeed", Toast.LENGTH_SHORT).show();

            }
        });

        addtoFavourites();

    }

    public void showPopup(View view) {
        Button setOnHome, setOnLockScreen, setOnBoth;
        popUpDialog.setContentView(R.layout.setwallpaperpopup);
        popUpDialog.setCanceledOnTouchOutside(true);
        setOnHome = popUpDialog.findViewById(R.id.setOnHome);
        setOnLockScreen = popUpDialog.findViewById(R.id.setOnLockScreen);
        setOnBoth = popUpDialog.findViewById(R.id.setOnBoth);
        popUpDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;


        assert setOnHome != null;
        setOnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(getBaseContext())
                        .load(Common.select_explore_background.getImageLink())
                        .into(target);

                Toast.makeText(ThemeActivity.this, "Home screen wallpaper applied", Toast.LENGTH_SHORT).show();


            }
        });


        setOnLockScreen.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Picasso.with(getBaseContext())
                        .load(Common.select_explore_background.getImageLink())
                        .into(target2);

                Toast.makeText(ThemeActivity.this, "Lock screen wallpaper applied", Toast.LENGTH_SHORT).show();
            }
        });
        setOnBoth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(getBaseContext())
                        .load(Common.select_explore_background.getImageLink())
                        .into(target3);

                Toast.makeText(ThemeActivity.this, "Wallpaper applied", Toast.LENGTH_SHORT).show();
            }
        });
        popUpDialog.show();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish(); //close Activity when click back button
        return super.onOptionsItemSelected(item);
    }

    private void addtoFavourites() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                Favourites favourites = new Favourites(
                        Common.select_explore_background.getImageLink(),
                        Common.select_explore_background.getName(),
                        Common.select_explore_background.getCollectionId(),
                        String.valueOf(System.currentTimeMillis())
                );
                favouritesRepository.insertFavourites(favourites);
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


}
