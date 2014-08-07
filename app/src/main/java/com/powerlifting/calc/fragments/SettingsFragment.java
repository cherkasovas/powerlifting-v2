package com.powerlifting.calc.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.powerlifting.calc.Config;
import com.powerlifting.calc.MainActivity;
import com.powerlifting.calc.R;
import com.powerlifting.calc.Utils;

import static android.view.View.OnClickListener;

public class SettingsFragment extends Fragment {

    private EditText yourWeightText;
    private Spinner priorityFederation;
    private CheckBox isExtended;

    private OnClickListener yourWeightListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            yourWeightText.requestFocus();
            yourWeightText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
            yourWeightText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
            yourWeightText.setSelection(yourWeightText.getText().length());
        }
    };

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Utils.hideKeyBoard(getActivity(), yourWeightText);
            View view = ((ViewGroup) v).getChildAt(1);
            view.requestFocus();
            view.performClick();
        }
    };
    private ToggleButton gender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, null);

        yourWeightText = (EditText) view.findViewById(R.id.your_weight);
        yourWeightText.setText(Float.toString(Config.getYourWeight()));

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
        view.findViewById(R.id.reset).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.reset(getActivity());
                restartApplication(300);
            }
        });

        return view;
    }

    private void restartApplication(int millis) {
        Intent mStartActivity = new Intent(getActivity(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 123456,
                mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + millis, pendingIntent);
        System.exit(0);
    }

    @Override
    public void onPause() {
        super.onPause();
        int type = priorityFederation.getSelectedItemPosition();
        float yourWeight = Float.valueOf(yourWeightText.getText().toString());

        String norms[][] = Utils.getNormsByType(type, getActivity());
        int weightIndex = 1;
        float weightCategory = Float.parseFloat(norms[1][0]);

        for (int i = 1; i < norms.length - 2; i++) {
            if (Float.parseFloat(norms[i][0]) < yourWeight && yourWeight <= Float.parseFloat(norms[i + 1][0])) {
                weightCategory = Float.parseFloat(norms[i + 1][0]);
                weightIndex = i + 1;
            }
        }

        Config.setYourWeightIndex(weightIndex);
        Config.setYourWeightCategory(weightCategory);
        Config.setYourWeight(yourWeight);
        Config.setYourFederation(type);
        Config.setIsExpanded(isExtended.isChecked());
        Config.setYourGender(gender.isChecked());
    }
}
