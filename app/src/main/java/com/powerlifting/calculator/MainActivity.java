package com.powerlifting.calculator;


import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.powerlifting.calculator.Fragments.CalcFragment;
import com.powerlifting.calculator.Fragments.MainFragment;
import com.powerlifting.calculator.adapters.NavigationDrawerAdapter;

public class MainActivity extends FragmentActivity {
    private ListView mNavigationDrawerMenu;
    private DrawerLayout mDrawerLayout;

    private AdapterView.OnItemClickListener mMenuClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            setFragment(position);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] mNavigationDrawerMenuTitles = getResources().getStringArray(R.array.navigation_drawer_menu_titles);
        TypedArray mNavigationDrawerMenuIcons = getResources().obtainTypedArray(R.array.navigation_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mNavigationDrawerMenu = (ListView) findViewById(R.id.left_drawer);

        NavigationDrawerAdapter mNavigationDrawerAdapter = new NavigationDrawerAdapter(this, mNavigationDrawerMenuTitles, mNavigationDrawerMenuIcons);
        mNavigationDrawerMenu.setAdapter(mNavigationDrawerAdapter);
        mNavigationDrawerMenu.setOnItemClickListener(mMenuClickListener);

//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setHomeButtonEnabled(true);
    }

    private void setFragment(int position) {
        Fragment mainFragment = new MainFragment();
        Fragment calcFragment = new CalcFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (position % 2 == 0) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, mainFragment).commit();
        } else {
            fragmentManager.beginTransaction().replace(R.id.content_frame, calcFragment).commit();
        }

        mNavigationDrawerMenu.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mNavigationDrawerMenu);
    }

}
