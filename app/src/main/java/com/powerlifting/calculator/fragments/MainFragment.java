package com.powerlifting.calculator.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.powerlifting.calculator.Config;
import com.powerlifting.calculator.R;
import com.powerlifting.calculator.Utils;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, null);

        float[] weights = Config.getMaxWeights();
        float summMax = weights[0] + weights[1] + weights[2];

        ((TextView) view.findViewById(R.id.bench_press_max)).setText(Float.toString(weights[0]));
        ((TextView) view.findViewById(R.id.deadlift_max)).setText(Float.toString(weights[2]));
        ((TextView) view.findViewById(R.id.squat_max)).setText(Float.toString(weights[1]));
        ((TextView) view.findViewById(R.id.summ_max)).setText(Float.toString(Utils.round(summMax)));
        ((TextView) view.findViewById(R.id.your_weight_max)).setText(Float.toString(weights[3]));

        return view;
    }

}
