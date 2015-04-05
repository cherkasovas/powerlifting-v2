package com.powerlifting.calc.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.powerlifting.calc.CategoryManager;
import com.powerlifting.calc.Config;
import com.powerlifting.calc.R;
import com.powerlifting.calc.Utils;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, null);
        CategoryManager categoryManager = new CategoryManager(getActivity());

        float[] maxWeights = Config.getMaxWeights();
//        String[] needWeights = categoryManager.getNeedWeights(getActivity(), maxWeights);
        float maxSum = maxWeights[0] + maxWeights[1] + maxWeights[2];

        ((TextView) view.findViewById(R.id.bench_press_max)).setText(Float.toString(maxWeights[0]));
        ((TextView) view.findViewById(R.id.squat_max)).setText(Float.toString(maxWeights[1]));
        ((TextView) view.findViewById(R.id.deadlift_max)).setText(Float.toString(maxWeights[2]));
        ((TextView) view.findViewById(R.id.summ_max)).setText(Float.toString(Utils.round(maxSum)));
        ((TextView) view.findViewById(R.id.your_weight_max)).setText(Float.toString(maxWeights[3]));

//        ((TextView) view.findViewById(R.id.bench_press_need)).setText(needWeights[0]);
//        ((TextView) view.findViewById(R.id.squat_need)).setText(needWeights[1]);
//        ((TextView) view.findViewById(R.id.deadlift_need)).setText(needWeights[2]);
//        ((TextView) view.findViewById(R.id.summ_need)).setText(needWeights[3]);
//        ((TextView) view.findViewById(R.id.your_weight_need)).setText(needWeights[4]);

        return view;
    }

}
