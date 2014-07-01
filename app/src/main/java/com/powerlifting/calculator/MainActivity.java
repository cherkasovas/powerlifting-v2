package com.powerlifting.calculator;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.powerlifting.calculator.Fragments.CalcFragment;
import com.powerlifting.calculator.Fragments.MainFragment;
import com.powerlifting.calculator.adapters.NavigationDrawerAdapter;

public class MainActivity extends ActionBarActivity {
    private ListView mNavigationDrawerMenu;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle myDrawerToggle;

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

        String[] mNavigationDrawerMenuTitles = getResources().getStringArray(R.array.navigation_drawer_menu_titles);
        TypedArray mNavigationDrawerMenuIcons = getResources().obtainTypedArray(R.array.navigation_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationDrawerMenu = (ListView) findViewById(R.id.left_drawer);

        NavigationDrawerAdapter mNavigationDrawerAdapter = new NavigationDrawerAdapter(this, mNavigationDrawerMenuTitles, mNavigationDrawerMenuIcons);
        mNavigationDrawerMenu.setAdapter(mNavigationDrawerAdapter);
        mNavigationDrawerMenu.setOnItemClickListener(mMenuClickListener);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle("хуй");
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("hooe");
                supportInvalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(myDrawerToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (myDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        myDrawerToggle.syncState();
    }

    private void setFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment;

        if (position % 2 == 0) {
            fragment = new MainFragment();
        } else {
            fragment = new CalcFragment();
        }
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        mNavigationDrawerMenu.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mNavigationDrawerMenu);
    }

}
