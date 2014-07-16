package com.powerlifting.calculator.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.powerlifting.calculator.R;

public class ListVewAdapter extends BaseAdapter {

    private final Context context;
    private final LayoutInflater inflater;
    private float data[][];

    public ListVewAdapter(Context context, float data[][]) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data[0].length;
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
        View item;
        if ((position + 1) == getCount()) {
            item = inflater.inflate(R.layout.list_view_last_item, null);
        } else {
            item = inflater.inflate(R.layout.list_view_item, null);
        }
        TextView col1 = (TextView) item.findViewById(R.id.col1);
        TextView col2 = (TextView) item.findViewById(R.id.col2);
        TextView col3 = (TextView) item.findViewById(R.id.col3);

        col1.setText(Integer.toString(position + 1));
        col2.setText(Float.toString(data[0][position]));
        col3.setText(Float.toString(data[1][position]));

        if (position % 2 != 0) {
            col1.setBackgroundColor(context.getResources().getColor(R.color.table_row_color_odd));
            col2.setBackgroundColor(context.getResources().getColor(R.color.table_row_color_odd));
            col3.setBackgroundColor(context.getResources().getColor(R.color.table_row_color_odd));
        }

        return item;
    }

}
