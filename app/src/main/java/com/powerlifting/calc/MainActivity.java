package com.powerlifting.calc;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
    //    private AdView adView;
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

    @Override
    protected void onPostResume() {
        if (isDrawerLocked) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
            mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        } else {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            mDrawerLayout.setScrimColor(getResources().getColor(R.color.nav_dr_shadow));
        }
        super.onPostResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Crashlytics.start(this);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setIcon(R.drawable.dumbbell);
        Config.getInstance(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationDrawerMenu = (ListView) findViewById(R.id.left_drawer);

        isDrawerLocked = getResources().getBoolean(R.bool.tablet_land);


//        adView = new AdView(this);
//        adView.setAdUnitId(getResources().getString(R.string.admob_publisher_id));
//        adView.setAdSize(AdSize.BANNER);
//        LinearLayout layout = (LinearLayout) findViewById(R.id.ad);
//        layout.addView(adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);

//        AppRate appRate = AppRate.build()
//                .setInstallDays(0)
//                .setLaunchTimes(3)
//                .setRemindInterval(2)
//                .setShowNeutralButton(true)
//                .setDebug(false).monitor(this);
//
//        appRate.showRateDialogIfMeetsConditions(this);

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
        if (isDrawerLocked) {
            getSupportActionBar().setHomeButtonEnabled(false);
        } else {
            getSupportActionBar().setHomeButtonEnabled(true);
        }

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
    protected void onDestroy() {
//        adView.destroy();
        super.onDestroy();
    }


    @Override
    public void onStart() {
        super.onStart();
//        EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    public void onPause() {
//        adView.pause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
//        adView.resume();
    }

    @Override
    public void onStop() {
        Config.getInstance(this).saveAll();
        super.onStop();
//        EasyTracker.getInstance(this).activityStop(this);
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
