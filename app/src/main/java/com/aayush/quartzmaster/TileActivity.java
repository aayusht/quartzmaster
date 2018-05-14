package com.aayush.quartzmaster;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import static com.aayush.quartzmaster.R.id.view;
import static com.aayush.quartzmaster.R.id.view_pager;

public class TileActivity extends AppCompatActivity {
    Tile tile;
    int[] images;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tile);
        getSupportActionBar().hide();
        verifyStoragePermissions(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            View view = w.getDecorView();
            int flags = view.getSystemUiVisibility();
            flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        }
        this.tile = (Tile) (getIntent().getSerializableExtra("tile"));

        if (this.getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE).getInt(tile.getID(), 0) == 1) {
            findViewById(R.id.favoriteStar).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.favoriteStar).setVisibility(View.GONE);
        }


        if (tile.drawable3 == 0) {
            images = new int[] {tile.drawable1, tile.drawable2};
        } else if (tile.drawable4 == 0) {
            images = new int[] {tile.drawable1, tile.drawable2, tile.drawable3};
        } else if (tile.drawable5 == 0) {
            images = new int[] {tile.drawable1, tile.drawable2, tile.drawable3, tile.drawable4};
        } else {
            images = new int[] {tile.drawable1, tile.drawable2, tile.drawable3, tile.drawable4, tile.drawable5};
        }
        final int[] images2 = images;
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ImagePagerAdapter adapter = new ImagePagerAdapter();
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);
        final Tile tile = this.tile;
        String type = "";
        switch(tile.collection) {
            case Tile.MARBLE:
                type = "MARBLE";
                break;
            case Tile.SCENERY:
                type = "SCENERY SERIES";
                break;
            case Tile.STARLIGHT:
                type = "STARLIGHT SERIES";
                break;
            case Tile.SOUND:
                type = "SOUND SERIES";
                break;
            case Tile.GRANITE:
                type = "GRANITE";
                break;
            case Tile.LEATHER:
                type = "LEATHER";
                break;
            case Tile.MINERAL:
                type = "MINERAL";
                break;
        }
        ((TextView) findViewById(R.id.collection)).setText(type);
        ((TextView) findViewById(R.id.name)).setText(tile.name);
        ((TextView) findViewById(R.id.size)).setText(tile.size);
        ((TextView) findViewById(R.id.thickness)).setText(tile.thickness);
        ((TextView) findViewById(R.id.finish)).setText("Polished");
        //TODO Activity should implement OnClickListener
        findViewById(R.id.favoriteStarOutline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("clicked", "clicked");
                int visibility = findViewById(R.id.favoriteStar).getVisibility();
                visibility = (visibility == View.GONE) ? View.VISIBLE : View.GONE;
                SharedPreferences sharedPref = TileActivity.this.getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                if (visibility == View.VISIBLE) {
                    editor.putInt(tile.getID(), 1);
                } else {
                    editor.putInt(tile.getID(), 0);
                }
                editor.apply();
                findViewById(R.id.favoriteStar).setVisibility(visibility);
            }
        });
        findViewById(R.id.download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    verifyStoragePermissions(TileActivity.this);
                    Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(images2[viewPager.getCurrentItem()])).getBitmap();
                    File fullCacheDir = new File(Environment.getExternalStorageDirectory().toURI());
                    String fileLocalName = tile.getID() + viewPager.getCurrentItem() + ".jpg";
                    File fileUri = new File(fullCacheDir, fileLocalName);
                    FileOutputStream outStream = null;
                    outStream = new FileOutputStream(fileUri);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 75, outStream);
                    outStream.flush();
                    Toast.makeText(TileActivity.this, "Success! File downloaded as " + tile.getID() + viewPager.getCurrentItem() + ".jpg", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(TileActivity.this, "Download failed. Please try again.", Toast.LENGTH_SHORT).show();
                    Log.e("oh no", e.getMessage());
                }
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog mBottomSheetDialog = new Dialog(TileActivity.this, R.style.MaterialDialogSheet);
                View layout = getLayoutInflater().inflate(R.layout.tile_dialog, null);
                layout.findViewById(R.id.call).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:8003339456"));
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                    }
                });
                layout.findViewById(R.id.email).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto", "info@quartzmasters.com", null));
                        emailIntent.putExtra(Intent.EXTRA_TEXT, MainActivity.getFavoritesString(TileActivity.this));
                        startActivity(Intent.createChooser(emailIntent, "Send email..."));
                    }
                });
                layout.findViewById(R.id.sample).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse("http://www.quartzmasters.com/order-samples/"));
                        startActivity(i);
                    }
                });
                mBottomSheetDialog.setContentView(layout); // your custom view.
                mBottomSheetDialog.setCancelable(true);
                mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
                mBottomSheetDialog.show();
            }
        });
    }

    private class ImagePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Context context = TileActivity.this;
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(images[position]);
            ((ViewPager) container).addView(imageView, 0);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.findViewById(R.id.view_pager).setSystemUiVisibility(0);
        }
    }
}
