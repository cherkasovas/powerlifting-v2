package com.powerlifting.calculator.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.powerlifting.calculator.R;

import uk.co.androidalliance.edgeeffectoverride.EdgeEffectListView;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private ListVewAdapter listVewAdapter;

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View page = inflater.inflate(R.layout.view_pager_item, null);
        EdgeEffectListView listView = (EdgeEffectListView) page.findViewById(R.id.list_view);
        listVewAdapter = new ListVewAdapter(context, position);
        listView.setAdapter(listVewAdapter);

        collection.addView(page, 0);
        return page;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v == obj;
    }

    public void updateTable(float weight, int enteredReps, int type) {
        listVewAdapter.updateWith(weight, enteredReps, type);
        notifyDataSetChanged();

    }
}
