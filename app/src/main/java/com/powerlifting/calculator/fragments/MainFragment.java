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

        TextView benchPressMax = (TextView) view.findViewById(R.id.bench_press_max);
        TextView deadliftMax = (TextView) view.findViewById(R.id.deadlift_max);
        TextView squatMax = (TextView) view.findViewById(R.id.squat_max);
        TextView summMax = (TextView) view.findViewById(R.id.summ_max);
        TextView yourWeightMax = (TextView) view.findViewById(R.id.your_weight_max);

        float[] weights = Config.getMaxWeights();
        benchPressMax.setText(Float.toString(weights[0]));
        deadliftMax.setText(Float.toString(weights[2]));
        squatMax.setText(Float.toString(weights[1]));
        summMax.setText(Float.toString(Utils.round(weights[0] + weights[1] + weights[2])));
        yourWeightMax.setText(Float.toString(Config.getYourWeight()));

        return view;
    }

}
