package com.powerlifting.calculator.Fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.powerlifting.calculator.Config;
import com.powerlifting.calculator.R;
import com.powerlifting.calculator.Utils;

import java.util.ArrayList;
import java.util.List;


public class CalcFragment extends Fragment {

    private static String[] data = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private byte selected;
    private Activity activity;
    private LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calc_fragment, null);
        activity = getActivity();
        Button calculate = (Button) view.findViewById(R.id.calculate);
        EditText enteredWeight = (EditText) view.findViewById(R.id.entered_weight);
        Spinner enteredReps = (Spinner) view.findViewById(R.id.entered_reps);
        TableLayout weightBreakdown = (TableLayout) view.findViewById(R.id.weight_breakdown);

        ArrayAdapter<String> SpinnerAdapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_spinner_item, data);
        SpinnerAdapter.setDropDownViewResource(R.layout.spiner_item);


        enteredReps.setAdapter(SpinnerAdapter);
        enteredReps.setSelection(0);

        enteredReps.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = (byte) position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });
        ;
        RenderTable(weightBreakdown, calculateWeights(77.5f, 5, 0), false);
        return view;
    }

    private float[][] calculateWeights(float weight, int reps, int type) {
        float[][] weights = new float[2][10];

        float maxWeight = Utils.round(Config.COEFFICIENTS[type][reps - 1] * weight);
        float percent = maxWeight / 100;

        for (int i = 9; i >= 0; i--) {
            weights[0][i] = Utils.round(maxWeight / Config.COEFFICIENTS[type][i]);
            weights[1][i] = Utils.round(weights[0][i] / percent);
        }
        return weights;
    }

    private void RenderTable(TableLayout tableLayout, float[][] data, Boolean advancedPreview) {
        TableRow header = (TableRow) tableLayout.getChildAt(0);
        header.setVisibility(View.VISIBLE);

        TableRow.LayoutParams lp = new TableRow.LayoutParams(0, Utils.dpToPx(50, activity));
        lp.weight = 1;
        lp.setMargins(0, 0, Utils.dpToPx(0.5f, activity), Utils.dpToPx(0.5f, activity));
        TableRow.LayoutParams lp1 = new TableRow.LayoutParams(0, Utils.dpToPx(50, activity));
        lp1.weight = 1;
        lp1.setMargins(Utils.dpToPx(0.5f, activity), 0, Utils.dpToPx(0.5f, activity), Utils.dpToPx(0.5f, activity));

        for (int i = 0; i < 10; i++) {
            TableRow tableRow = new TableRow(activity);
            List<TextView> cells = new ArrayList<TextView>(3);
            for (int j = 0; j < 3; j++) {
                TextView textView = new TextView(activity);
                if (i % 2 != 0) {
                    textView.setBackgroundColor(Color.parseColor("#ffffff"));
                } else {
                    textView.setBackgroundColor(Color.parseColor("#dddddd"));
                }

                textView.setTextSize(18);
                cells.add(textView);
            }

            cells.get(0).setText(Integer.toString(i + 1));
            cells.get(1).setText(Float.toString(data[0][i]));
            cells.get(2).setText(Float.toString(data[1][i]));


            tableRow.addView(cells.get(0), lp1);
            for (int j = 1; j < 3; j++) {
                tableRow.addView(cells.get(j), lp);
            }
            tableLayout.addView(tableRow);
        }

    }
}