package com.aayush.quartzmaster;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
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

import static com.aayush.quartzmaster.R.id.view;

public class TileActivity extends AppCompatActivity {
    Tile tile;
    int[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tile);
        getSupportActionBar().hide();
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
        } else {
            images = new int[] {tile.drawable1, tile.drawable2, tile.drawable3};
        }
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ImagePagerAdapter adapter = new ImagePagerAdapter();
        viewPager.setAdapter(adapter);
        final Tile tile = this.tile;
        String type = "";
        switch(tile.collection) {
            case "The Marble Collection":
                type = "MARBLE";
                break;
            case "Scenery Series":
                type = "SCENERY SERIES";
                break;
            case "Starlight Series":
                type = "STARLIGHT SERIES";
                break;
            case "Sound Series":
                type = "SOUND SERIES";
                break;
            case "GRANITE LOOK":
                type = "GRANITE";
                break;
            case "The Mineral Collection":
                type = "MINERAL";
                break;
        }
        ((TextView) findViewById(R.id.collection)).setText(type);
        ((TextView) findViewById(R.id.name)).setText(tile.name);
        ((TextView) findViewById(R.id.size)).setText(tile.size);
        ((TextView) findViewById(R.id.thickness)).setText(tile.thickness);
        ((TextView) findViewById(R.id.finish)).setText("Polished");
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
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog mBottomSheetDialog = new Dialog(TileActivity.this, R.style.MaterialDialogSheet);
                View layout = getLayoutInflater().inflate(R.layout.contact_dialog, null);
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
                        startActivity(Intent.createChooser(emailIntent, "Send email..."));
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
