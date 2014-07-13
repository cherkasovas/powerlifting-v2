package com.powerlifting.calculator.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.powerlifting.calculator.Config;
import com.powerlifting.calculator.R;
import com.powerlifting.calculator.Utils;

import uk.co.androidalliance.edgeeffectoverride.EdgeEffectListView;

public class ViewPagerAdapter extends PagerAdapter {

    private final Context context;
    private final LayoutInflater inflater;
    private Float weight;
    private Integer reps;

    public ViewPagerAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int type) {
        View page = inflater.inflate(R.layout.view_pager_item, null);
        EdgeEffectListView listView = (EdgeEffectListView) page.findViewById(R.id.list_view);

        float[][] data = Utils.calculateWeights(Config.getWeightByType(type), Config.getRepsByType(type), type);
        ListVewAdapter listVewAdapter = new ListVewAdapter(context, data);

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

}
