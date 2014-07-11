package com.powerlifting.calculator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.powerlifting.calculator.Config;
import com.powerlifting.calculator.R;
import com.powerlifting.calculator.Utils;

public class ListVewAdapter extends BaseAdapter {

    private float data[][];
    private Context context;
    private final LayoutInflater inflater;

    public ListVewAdapter(Context context, int type) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        init(type);
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
        View item = inflater.inflate(R.layout.list_view_item, null);
        TextView col1 = (TextView) item.findViewById(R.id.col1);
        TextView col2 = (TextView) item.findViewById(R.id.col2);
        TextView col3 = (TextView) item.findViewById(R.id.col3);

        col1.setText(Integer.toString(position + 1));
        col2.setText(Float.toString(data[0][position]));
        col3.setText(Float.toString(data[1][position]));

        if (position % 2 == 0) {
            col1.setBackgroundColor(context.getResources().getColor(R.color.table_row_color_odd));
            col2.setBackgroundColor(context.getResources().getColor(R.color.table_row_color_odd));
            col3.setBackgroundColor(context.getResources().getColor(R.color.table_row_color_odd));
        }

        return item;
    }

    public void updateWith(float weight, int enteredReps, int type) {
        data = Utils.calculateWeights(weight, enteredReps, type);
        this.notifyDataSetChanged();
        this.notifyDataSetInvalidated();
    }

    private void init(int type) {
        switch (type) {
            case 0:
                data = Utils.calculateWeights(Config.getPressWeight(), Config.getPressReps(), type);
                break;
            case 1:
                data = Utils.calculateWeights(Config.getSquatWeight(), Config.getSquatReps(), type);
                break;
            case 2:
                data = Utils.calculateWeights(Config.getDeadliftWeight(), Config.getDeadliftReps(), type);
                break;
        }
    }


}
