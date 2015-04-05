package com.powerlifting.calc.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;
import com.powerlifting.calc.R;
import com.powerlifting.calc.Utils;

public class NormsTableAdapter extends BaseTableAdapter {

    private final Context context;
    private final String[][] data;
    private final String[] categoryNames;
    private LayoutInflater inflater;

    public NormsTableAdapter(Context context, int type) {
        this.context = context;
        data = Utils.getNormsByType(type, context);
        categoryNames = Utils.getCategoryNames(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getRowCount() {
        return data.length - 1;
    }

    @Override
    public int getColumnCount() {
        return data[0].length - 2;
    }

    @Override
    public View getView(int row, int column, View convertView, ViewGroup parent) {
        View item = inflater.inflate(R.layout.norms_table_item, null);
        TextView textView = (TextView) item.findViewById(R.id.weight_text);

        if (!data[row + 1][column + 1].equals("0")) {
            textView.setText(data[row + 1][column + 1]);
        }

        if (row == -1) {
            textView.setText(categoryNames[column + 1]);
        }

        if (row == (getRowCount() - 1) && column == -1) {
            textView.setText(textView.getText() + "+");
        }

        if (row == -1) {
            textView.setTextColor(Color.WHITE);
            textView.setBackgroundColor(context.getResources().getColor(R.color.orange_status_bar));
        } else if (column == -1) {
            textView.setTextColor(Color.WHITE);
            textView.setBackgroundColor(context.getResources().getColor(R.color.orange_status_bar));
        } else if (row % 2 != 0) {
            textView.setBackgroundColor(context.getResources().getColor(R.color.table_row_color_odd));
        }
        return item;
    }

    @Override
    public int getWidth(int column) {
        return context.getResources().getDimensionPixelSize(R.dimen.table_row_width_norms_1);
    }

    @Override
    public int getHeight(int row) {
        return context.getResources().getDimensionPixelSize(R.dimen.table_row_height_1);
    }

    @Override
    public int getItemViewType(int row, int column) {
        if (row < 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
}
