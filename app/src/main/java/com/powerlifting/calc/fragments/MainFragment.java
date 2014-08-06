package com.powerlifting.calc.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.powerlifting.calc.Config;
import com.powerlifting.calc.R;
import com.powerlifting.calc.Utils;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, null);

        float[] weights = Config.getMaxWeights();
        float[] needWeights = new float[3];
        float summMax = weights[0] + weights[1] + weights[2];

        ((TextView) view.findViewById(R.id.bench_press_max)).setText(Float.toString(weights[0]));
        ((TextView) view.findViewById(R.id.squat_max)).setText(Float.toString(weights[1]));
        ((TextView) view.findViewById(R.id.deadlift_max)).setText(Float.toString(weights[2]));
        ((TextView) view.findViewById(R.id.summ_max)).setText(Float.toString(Utils.round(summMax)));
        ((TextView) view.findViewById(R.id.your_weight_max)).setText(Float.toString(weights[3]));

        float weightDelta = Config.getYourWeightCategory() - weights[3];
        if (weightDelta >= 0 && weights[3] != 0) {
            ((TextView) view.findViewById(R.id.your_weight_need)).setText(Float.toString(Utils.round(weightDelta)));
        } else {
            ((TextView) view.findViewById(R.id.your_weight_need)).setText(Character.toString('∞'));
        }

        String norms[][] = Utils.getNormsByType(Config.getYourFederation(), getActivity());
        int weightIndex = Config.getYourWeightIndex();
        float weightCategory = Float.parseFloat(norms[weightIndex][norms[0].length - 1]);
        int categoryIndex = 0;

        for (int i = norms[0].length - 1; i > 1; i--) {
            if (Float.parseFloat(norms[weightIndex][i]) <= summMax && summMax < Float.parseFloat(norms[weightIndex][i - 1])) {
                weightCategory = Float.parseFloat(norms[weightIndex][i - 1]);
                categoryIndex = i;
            }
        }

        float needWeight = Utils.round(weightCategory - summMax);
        if (needWeight > 0 && summMax != 0 && weights[3] != 0) {
            for (int i = 0; i < 3; i++) {
                needWeights[i] = Utils.round(needWeight * (weights[i] / summMax));
            }

            needWeights[2] += Utils.round(needWeight - (needWeights[0] + needWeights[1] + needWeights[2]));
            ((TextView) view.findViewById(R.id.bench_press_need)).setText(Float.toString(needWeights[0]));
            ((TextView) view.findViewById(R.id.squat_need)).setText(Float.toString(needWeights[1]));
            ((TextView) view.findViewById(R.id.deadlift_need)).setText(Float.toString(needWeights[2]));
            ((TextView) view.findViewById(R.id.summ_need)).setText(Float.toString(needWeight));
        } else {
            String inf = Character.toString('∞');
            ((TextView) view.findViewById(R.id.bench_press_need)).setText(inf);
            ((TextView) view.findViewById(R.id.squat_need)).setText(inf);
            ((TextView) view.findViewById(R.id.deadlift_need)).setText(inf);
            ((TextView) view.findViewById(R.id.summ_need)).setText(inf);
        }

        if (Float.parseFloat(norms[weightIndex][1]) <= summMax) {
            categoryIndex = 1;
        }

        String[] categories = getResources().getStringArray(R.array.categories);
        String[] federations = getResources().getStringArray(R.array.federations_names);
        String currentCategory;
        if (categoryIndex != 0) {
            currentCategory = categories[categoryIndex] + " " + federations[Config.getYourFederation()];
        } else {
            currentCategory = getResources().getString(R.string.none_category);
        }
        ((TextView) view.findViewById(R.id.current_category)).setText(currentCategory);

        return view;
    }

}
