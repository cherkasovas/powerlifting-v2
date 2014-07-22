package com.powerlifting.calc;

import android.content.res.TypedArray;
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
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.powerlifting.calc.adapters.NavigationDrawerAdapter;
import com.powerlifting.calc.fragments.CalcFragment;
import com.powerlifting.calc.fragments.MainFragment;
import com.powerlifting.calc.fragments.NormsFragment;
import com.powerlifting.calc.fragments.SettingsFragment;

import hotchemi.android.rate.AppRate;

public class MainActivity extends ActionBarActivity {
    private ListView mNavigationDrawerMenu;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private AdView adView;
    FragmentManager fragmentManager = getSupportFragmentManager();


    private AdapterView.OnItemClickListener mMenuClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if (position != Config.getMenuItem()) {
                setFragment(position);
                NavigationDrawerAdapter adapter = (NavigationDrawerAdapter) mNavigationDrawerMenu.getAdapter();
                adapter.setChecked(position);
                adapter.notifyDataSetChanged();
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setIcon(R.drawable.dumbbell);
        Config.getInstance(this);

        adView = new AdView(this);
        adView.setAdUnitId(getResources().getString(R.string.admob_publisher_id));
        adView.setAdSize(AdSize.BANNER);
        LinearLayout layout = (LinearLayout) findViewById(R.id.ad);
        layout.addView(adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        AppRate appRate = AppRate.build()
                .setInstallDays(0)
                .setLaunchTimes(3)
                .setRemindInterval(2)
                .setShowNeutralButton(true)
                .setDebug(false).monitor(this);

        appRate.showRateDialogIfMeetsConditions(this);

        String[] mNavigationDrawerMenuTitles = getResources().getStringArray(R.array.navigation_drawer_menu_titles);
        TypedArray mNavigationDrawerMenuIcons = getResources().obtainTypedArray(R.array.navigation_drawer_icons);
        TypedArray mNavigationDrawerMenuCheckedIcons = getResources()
                .obtainTypedArray(R.array.navigation_drawer_icons_checked);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationDrawerMenu = (ListView) findViewById(R.id.left_drawer);

        NavigationDrawerAdapter mNavigationDrawerAdapter = new NavigationDrawerAdapter(this,
                mNavigationDrawerMenuTitles, mNavigationDrawerMenuIcons, mNavigationDrawerMenuCheckedIcons);
        mNavigationDrawerMenu.setAdapter(mNavigationDrawerAdapter);
        mNavigationDrawerMenu.setOnItemClickListener(mMenuClickListener);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
                setTitleByMenuItem(Config.getMenuItem());
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
                supportInvalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
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
        adView.destroy();
        Config.getInstance(this).saveAll();
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    public void onPause() {
        adView.pause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        adView.resume();
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
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
        }
    }

}
