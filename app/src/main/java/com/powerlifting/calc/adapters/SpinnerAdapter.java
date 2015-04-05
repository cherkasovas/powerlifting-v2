package com.powerlifting.calc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.powerlifting.calc.R;
import com.powerlifting.calc.views.CustomFontTextView;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<String> {
    private List<String> items;
    private LayoutInflater inflater;
    private boolean isFont;

    public SpinnerAdapter(Context context, int resource, List<String> items, boolean isFont) {
        super(context, resource, items);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = items;
        this.isFont = isFont;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.spiner_item, null);
        CustomFontTextView textView = (CustomFontTextView) view.findViewById(R.id.text1);
        textView.setText(items.get(position));
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.spiner_item, null);
        CustomFontTextView textView = (CustomFontTextView) view.findViewById(R.id.text1);
        textView.setText(items.get(position));
        if (isFont) {
            textView.setCustomFont(Integer.toString(position));
        }
        return view;
    }
}
