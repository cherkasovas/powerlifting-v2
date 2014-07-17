package com.powerlifting.calculator.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.powerlifting.calculator.Config;
import com.powerlifting.calculator.R;

public class SettingsFragment extends Fragment {

    private TextView yourWeight;
    private Spinner priorityFederation;
    private CheckBox isExtended;
    private Spinner genderSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, null);

        yourWeight = (TextView) view.findViewById(R.id.your_weight);
        yourWeight.setText(Float.toString(Config.getYourWeight()));

        priorityFederation = (Spinner) view.findViewById(R.id.priority_federation);
        String[] priorityFederationData = getResources().getStringArray(R.array.federations_names);
        ArrayAdapter<String> priorityFederationAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, priorityFederationData);
        priorityFederationAdapter.setDropDownViewResource(R.layout.spiner_item);
        priorityFederation.setAdapter(priorityFederationAdapter);
        priorityFederation.setSelection(Config.getYourFederation());
        priorityFederation.setOnItemSelectedListener(null);

        isExtended = (CheckBox) view.findViewById(R.id.is_extended);
        isExtended.setChecked(Config.getIsExtended());

        genderSpinner = (Spinner) view.findViewById(R.id.gender);
        String[] genderData = getResources().getStringArray(R.array.gender);
        ArrayAdapter<String> genderSpinnerAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, genderData);
        genderSpinnerAdapter.setDropDownViewResource(R.layout.spiner_item);
        genderSpinner.setAdapter(genderSpinnerAdapter);
        genderSpinner.setSelection(Config.getYourGender());
        genderSpinner.setOnItemSelectedListener(null);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Config.setYourWeight(Float.valueOf(String.valueOf(yourWeight.getText())));
        Config.setYourFederation((Integer) priorityFederation.getSelectedItemPosition());
        Config.setIsExpanded(isExtended.isChecked());
        Config.setYourGender((Integer) genderSpinner.getSelectedItemPosition());
    }
}
