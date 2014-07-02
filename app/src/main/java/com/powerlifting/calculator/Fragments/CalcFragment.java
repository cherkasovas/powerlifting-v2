package com.powerlifting.calculator.Fragments;

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


public class CalcFragment extends Fragment {

    private static String[] data = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private byte selected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calc_fragment, null);
        Button calculate = (Button) view.findViewById(R.id.calculate);
        EditText enteredWeight = (EditText) view.findViewById(R.id.entered_weight);
        Spinner enteredReps = (Spinner) view.findViewById(R.id.entered_reps);
        TableLayout weightBreakdown = (TableLayout) view.findViewById(R.id.weight_breakdown);

        ArrayAdapter<String> SpinnerAdapter = new ArrayAdapter<String>(getActivity(),
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
        calculateWeights(77.5f, 5, 0);
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

    private void RenderTable(TableLayout tableLayout, float[][] data) {
        TableRow tableRow = new TableRow(getActivity());
        TextView textView = new TextView(getActivity());

    }
}