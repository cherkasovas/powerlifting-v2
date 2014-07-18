package com.powerlifting.calculator.fragments;

import android.app.Service;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.powerlifting.calculator.Config;
import com.powerlifting.calculator.R;
import com.powerlifting.calculator.Utils;

import static android.view.View.OnClickListener;

public class SettingsFragment extends Fragment {

    private EditText yourWeight;
    private Spinner priorityFederation;
    private CheckBox isExtended;

    private OnClickListener yourWeightListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            yourWeight.requestFocus();
            yourWeight.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
            yourWeight.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
            yourWeight.setSelection(yourWeight.getText().length());
        }
    };

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Utils.hideKeyBoard(getActivity(), yourWeight);
            View view = ((ViewGroup) v).getChildAt(1);
            view.requestFocus();
            view.performClick();
        }
    };
    private ToggleButton gender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, null);

        yourWeight = (EditText) view.findViewById(R.id.your_weight);
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

        gender = (ToggleButton) view.findViewById(R.id.gender);
        gender.setChecked(Config.getYourGender());


        view.findViewById(R.id.your_weight_settings).setOnClickListener(yourWeightListener);
        view.findViewById(R.id.priority_federation_settings).setOnClickListener(listener);
        view.findViewById(R.id.is_extended_settings).setOnClickListener(listener);
        view.findViewById(R.id.gender_settings).setOnClickListener(listener);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Config.setYourWeight(Float.valueOf(String.valueOf(yourWeight.getText())));
        Config.setYourFederation(priorityFederation.getSelectedItemPosition());
        Config.setIsExpanded(isExtended.isChecked());
        Config.setYourGender(gender.isChecked());
    }
}
