package com.powerlifting.calc;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.powerlifting.calc.adapters.NavigationDrawerAdapter;
import com.powerlifting.calc.fragments.CalcFragment;
import com.powerlifting.calc.fragments.HelpFragment;
import com.powerlifting.calc.fragments.MainFragment;
import com.powerlifting.calc.fragments.NormsFragment;
import com.powerlifting.calc.fragments.SettingsFragment;

public class MainActivity extends ActionBarActivity {
    private ListView mNavigationDrawerMenu;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Boolean isDrawerLocked;
    private AdapterView.OnItemClickListener mMenuClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if (position != Config.getMenuItem()) {
                setFragment(position);
                NavigationDrawerAdapter adapter = (NavigationDrawerAdapter) mNavigationDrawerMenu.getAdapter();
                adapter.setChecked(position);
                adapter.notifyDataSetChanged();
            }

            if (isDrawerLocked) {
                return;
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mDrawerLayout.closeDrawer(mNavigationDrawerMenu);
                }
            }, 80);
        }

    };
    private OnFontChangeListener onFontChangeListener = new OnFontChangeListener() {
        @Override
        public void onFontChange() {
            setCustomFontTitleBar();
        }
    };

    @Override
    protected void onPostResume() {
        if (isDrawerLocked) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
            mDrawerLayout.setScrimColor(Color.TRANSPARENT);
            getSupportActionBar().setHomeButtonEnabled(false);
        } else {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            mDrawerLayout.setScrimColor(getResources().getColor(R.color.nav_dr_shadow));
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        super.onPostResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setIcon(R.drawable.dumbbell);
        Config.getInstance(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationDrawerMenu = (ListView) findViewById(R.id.left_drawer);

        isDrawerLocked = getResources().getBoolean(R.bool.tablet_land);

        setCustomFontTitleBar();

        //TEST
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            SystemBarTintManager tintManager = new SystemBarTintManager(this);
//            tintManager.setStatusBarTintEnabled(true);
//
//            int actionBarColor = Color.parseColor("#00DDDD");
//            tintManager.setStatusBarTintColor(actionBarColor);
//            Log.d("asd","asd");
        }

        String[] mNavigationDrawerMenuTitles = getResources().getStringArray(R.array.navigation_drawer_menu_titles);
        TypedArray mNavigationDrawerMenuIcons = getResources().obtainTypedArray(R.array.navigation_drawer_icons);
        TypedArray mNavigationDrawerMenuCheckedIcons = getResources()
                .obtainTypedArray(R.array.navigation_drawer_icons_checked);


        NavigationDrawerAdapter mNavigationDrawerAdapter = new NavigationDrawerAdapter(this,
                mNavigationDrawerMenuTitles, mNavigationDrawerMenuIcons, mNavigationDrawerMenuCheckedIcons);
        mNavigationDrawerAdapter.setChecked(Config.getMenuItem());
        mNavigationDrawerMenu.setAdapter(mNavigationDrawerAdapter);
        mNavigationDrawerMenu.setOnItemClickListener(mMenuClickListener);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {

            public void onDrawerClosed(View view) {
                setTitleByMenuItem(Config.getMenuItem());
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
                super.onDrawerOpened(drawerView);
            }
        };

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //crash protector
        findViewById(R.id.content_frame).setOnClickListener(null);

        Fragment fragment = getFragmentByMenuItem(Config.getMenuItem());
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onStop() {
        Config.getInstance(this).saveAll();
        super.onStop();
    }

    public void setCustomFontTitleBar() {
        int fontType = Config.getFontType();
        if (fontType != 0) {
            int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
            TextView titleBarTextView = (TextView) findViewById(titleId);
            String fontName = Integer.toString(fontType) + ".ttf";
            if (titleBarTextView != null) {
                titleBarTextView.setTypeface(TypeFaceProvider.getTypeFace(this, fontName));
            }
        }
    }

    private void setFragment(int position) {
        Config.setMenuItem(position);
        Fragment fragment = getFragmentByMenuItem(position);
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    private Fragment getFragmentByMenuItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MainFragment();
                break;
            case 1:
                fragment = new CalcFragment();
                break;
            case 2:
                fragment = new NormsFragment();
                break;
            case 3:
                fragment = new SettingsFragment();
                ((SettingsFragment) fragment).setOnFontChangeListener(onFontChangeListener);
                break;
            case 4:
                fragment = new HelpFragment();
                break;
        }
        setTitleByMenuItem(position);
        return fragment;
    }

    private void setTitleByMenuItem(int position) {
        switch (position) {
            case 0:
                getSupportActionBar().setTitle(getResources().getString(R.string.main));
                break;
            case 1:
                getSupportActionBar().setTitle(getResources().getString(R.string.calculator));
                break;
            case 2:
                getSupportActionBar().setTitle(getResources().getString(R.string.norms));
                break;
            case 3:
                getSupportActionBar().setTitle(getResources().getString(R.string.settings));
                break;
            case 4:
                getSupportActionBar().setTitle(getResources().getString(R.string.help));
                break;
        }
    }

}
