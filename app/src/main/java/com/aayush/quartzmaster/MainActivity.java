package com.aayush.quartzmaster;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity {
    public static String[] prefkeys = {
            "" + R.id.marble,
            "" + R.id.starlight,
            "" + R.id.scenery,
            "" + R.id.granite,
            "" + R.id.sound,
            "" + R.id.leather,
            "" + R.id.mineral,
            "" + R.id.thickness_checkbox,
            "" + R.id.finish_checkbox,
            "" + R.id.qm_checkbox,
            "" + R.id.qm_checkbox2,
    };

    private FragmentManager fragmentManager;
    private Fragment fragment;
    private String currFragment = "list";
    private BottomNavigationViewEx.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationViewEx.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_list:
                    fragment = ListFragment.getInstance(MainActivity.this);
                    currFragment = "list";
                    invalidateOptionsMenu();
                    transaction.replace(R.id.content, fragment).commit();
                    getSupportActionBar().setDisplayShowHomeEnabled(false);
                    getSupportActionBar().setDisplayShowTitleEnabled(false);
                    LayoutInflater mInflater = LayoutInflater.from(MainActivity.this);
                    View mCustomView = mInflater.inflate(R.layout.main_actionbar, null);
                    getSupportActionBar().setCustomView(mCustomView);
                    getSupportActionBar().setDisplayShowCustomEnabled(true);

                    View v = getSupportActionBar().getCustomView();
                    ViewGroup.LayoutParams lp = v.getLayoutParams();
                    lp.width = ActionBar.LayoutParams.MATCH_PARENT;
                    v.setLayoutParams(lp);
                    break;
                case R.id.navigation_favorites:
                    fragment = FavoritesFragment.getInstance(MainActivity.this);
                    currFragment = "favorites";
                    invalidateOptionsMenu();
                    transaction.replace(R.id.content, fragment).commit();
                    getSupportActionBar().setDisplayShowCustomEnabled(false);
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                    getSupportActionBar().setTitle("Favorites");
                    break;
                case R.id.navigation_map:
                    fragment = MapFragment.getInstance(MainActivity.this);
                    currFragment = "map";
                    invalidateOptionsMenu();
                    transaction.replace(R.id.content, fragment).commit();
                    getSupportActionBar().setDisplayShowCustomEnabled(false);
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                    getSupportActionBar().setTitle("Locations");
                    break;
                case R.id.navigation_about:
                    fragment = AboutFragment.getInstance(MainActivity.this);
                    currFragment = "about";
                    invalidateOptionsMenu();
                    transaction.replace(R.id.content, fragment).commit();
                    getSupportActionBar().setDisplayShowCustomEnabled(false);
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                    getSupportActionBar().setTitle("About Us");
                    break;
                default:
                    return false;
            }

            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        fragment = ListFragment.getInstance(this);
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.content, fragment).commit();

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.main_actionbar, null);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

        View v = mActionBar.getCustomView();
        ViewGroup.LayoutParams lp = v.getLayoutParams();
        lp.width = ActionBar.LayoutParams.MATCH_PARENT;
        v.setLayoutParams(lp);

        BottomNavigationViewEx bnve = (BottomNavigationViewEx) findViewById(R.id.navigation);
        bnve.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);
        bnve.setTextVisibility(false);
        bnve.setIconSize(30f, 30f);
        bnve.setItemHeight(150);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            bnve.setItemHeight(90);
        }

        clearFilters(this);
    }

    @Override
    protected void onDestroy() {
        clearFilters(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        if (currFragment.equals("list")) {

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.options_menu, menu);

            final MenuItem searchItem = menu.findItem(R.id.search);
            searchItem.collapseActionView();

            if (currFragment.equals("about") || currFragment.equals("favorites")|| currFragment.equals("map"))searchItem.setVisible(false);
            else {
                searchItem.setVisible(true);
                ActionBar mActionBar = getSupportActionBar();
                mActionBar.setDisplayShowHomeEnabled(false);
                mActionBar.setDisplayShowTitleEnabled(false);
                LayoutInflater mInflater = LayoutInflater.from(this);
                View mCustomView = mInflater.inflate(R.layout.main_actionbar, null);
                mActionBar.setCustomView(mCustomView);
                mActionBar.setDisplayShowCustomEnabled(true);

                View v = mActionBar.getCustomView();
                ViewGroup.LayoutParams lp = v.getLayoutParams();
                lp.width = ActionBar.LayoutParams.MATCH_PARENT;
                v.setLayoutParams(lp);
            }

            SearchView searchView = (android.widget.SearchView) searchItem.getActionView();
            searchView.setMaxWidth(Integer.MAX_VALUE);

            // Detect SearchView icon clicks
            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currFragment.equals("list")) {
                        getSupportActionBar().setDisplayShowCustomEnabled(false);
                    } else {
                        getSupportActionBar().setDisplayShowTitleEnabled(false);
                    }
                }
            });
            // Detect SearchView close
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    if (currFragment.equals("list")) {
                        getSupportActionBar().setDisplayShowCustomEnabled(true);
                    } else {
                        getSupportActionBar().setDisplayShowTitleEnabled(true);
                    }
                    return false;
                }
            });

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    switch (currFragment) {
                        case "list":
                            getSupportActionBar().setDisplayShowCustomEnabled(true);
                            break;
                        case "map":
                            getSupportActionBar().setDisplayShowTitleEnabled(true);
                            break;
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    switch (currFragment) {
                        case "list":
                            getSupportActionBar().setDisplayShowCustomEnabled(false);
                            try {
                                ((TileAdapter) ((ListFragment) fragment).recyclerView.getAdapter()).search(s.toLowerCase());
                            } catch (Exception npe) {
                                //this is fine
                            }
                            break;
                    }
                    return false;
                }
            });
        }

        return super.onCreateOptionsMenu(menu);
    }

    public static void clearFilters(Context context) {
        for (String key : prefkeys) {
            SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preferences_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(key, true);
            editor.commit();
        }
    }

    public static String getFavoritesString(Context context) {
        String s = "";
        for (Tile tile : TileAdapter.tiles) {
            if (context.getSharedPreferences(context.getString(R.string.preferences_key),
                    Context.MODE_PRIVATE).getInt(tile.getID(), 0) == 1) {
                s += tile.getName() + "\n";
            }
        }
        if (s.equals("")) {
            return s;
        }
        return "My favorite tiles:\n" + s;
    }
}
