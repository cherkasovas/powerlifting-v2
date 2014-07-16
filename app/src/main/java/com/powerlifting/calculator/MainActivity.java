package com.powerlifting.calculator;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.powerlifting.calculator.adapters.NavigationDrawerAdapter;
import com.powerlifting.calculator.fragments.CalcFragment;
import com.powerlifting.calculator.fragments.MainFragment;
import com.powerlifting.calculator.fragments.NormsFragment;
import com.powerlifting.calculator.fragments.SettingsFragment;

public class MainActivity extends ActionBarActivity {
    private ListView mNavigationDrawerMenu;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    FragmentManager fragmentManager = getSupportFragmentManager();


    private AdapterView.OnItemClickListener mMenuClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            setFragment(position);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Config.getInstance(this);

        String[] mNavigationDrawerMenuTitles = getResources().getStringArray(R.array.navigation_drawer_menu_titles);
        TypedArray mNavigationDrawerMenuIcons = getResources().obtainTypedArray(R.array.navigation_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationDrawerMenu = (ListView) findViewById(R.id.left_drawer);

        NavigationDrawerAdapter mNavigationDrawerAdapter = new NavigationDrawerAdapter(this, mNavigationDrawerMenuTitles, mNavigationDrawerMenuIcons);
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
        super.onDestroy();
        Config.getInstance(this).saveAll();
    }

    private void setFragment(int position) {
        Config.setMenuItem(position);
        Fragment fragment = getFragmentByMenuItem(position);
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        mNavigationDrawerMenu.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mNavigationDrawerMenu);
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
