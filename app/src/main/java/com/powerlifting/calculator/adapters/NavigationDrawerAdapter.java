package com.powerlifting.calculator.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerlifting.calculator.R;

public class NavigationDrawerAdapter extends BaseAdapter {
    private final String[] titles;
    private final TypedArray icons;
    private LayoutInflater inflater;

    public NavigationDrawerAdapter(Context context, String[] titles, TypedArray icons) {
        this.titles = titles;
        this.icons = icons;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View menuItem = inflater.inflate(R.layout.navigation_drawer_menu_item, null);
        ImageView menuIcon = (ImageView) menuItem.findViewById(R.id.menu_icon);
        TextView menuTitle = (TextView) menuItem.findViewById(R.id.menu_title);
        menuIcon.setBackgroundResource(icons.getResourceId(position, R.id.none));
        menuTitle.setText(titles[position]);
        return menuItem;
    }
}
