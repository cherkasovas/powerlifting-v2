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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.powerlifting.calc.CategoryManager;
import com.powerlifting.calc.Config;
import com.powerlifting.calc.MainActivity;
import com.powerlifting.calc.OnFontChangeListener;
import com.powerlifting.calc.R;
import com.powerlifting.calc.Utils;
import com.powerlifting.calc.adapters.SpinnerAdapter;

import java.util.Arrays;
import java.util.List;

import static android.view.View.OnClickListener;

public class SettingsFragment extends Fragment {

    private EditText yourWeightText;
    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Utils.hideKeyBoard(getActivity(), yourWeightText);
            View view = ((ViewGroup) v).getChildAt(1);
            view.requestFocus();
            view.performClick();
        }
    };
    private Spinner priorityFederation;
    private Spinner customFont;
    private CheckBox isExtended;
    private ToggleButton gender;
    private ToggleButton measure;
    private OnFontChangeListener onFontChangeListener;
    private OnClickListener yourWeightListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO fix keyboard
            yourWeightText.requestFocus();
            yourWeightText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                    SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
            yourWeightText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                    SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
            yourWeightText.setSelection(yourWeightText.getText().length());
        }
    };

    public void setOnFontChangeListener(OnFontChangeListener onFontChangeListener) {
        this.onFontChangeListener = onFontChangeListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, null);

        //Weight setting
        yourWeightText = (EditText) view.findViewById(R.id.your_weight);
        yourWeightText.setText(Float.toString(Config.getYourWeight()));
        view.findViewById(R.id.your_weight_settings).setOnClickListener(yourWeightListener);

        //Federation setting
        priorityFederation = (Spinner) view.findViewById(R.id.priority_federation);
        List<String> priorityFederationData = Arrays.asList(getResources()
                .getStringArray(R.array.federations_names));
        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), R.layout.spiner_item, priorityFederationData, false);
        priorityFederation.setAdapter(adapter);
        priorityFederation.setSelection(Config.getYourFederation());
        priorityFederation.setOnItemSelectedListener(null);
        view.findViewById(R.id.priority_federation_settings).setOnClickListener(listener);

        //Font setting
        customFont = (Spinner) view.findViewById(R.id.custom_font);
        List<String> customFonts = Arrays.asList(getResources().getStringArray(R.array.fonts_names));
        SpinnerAdapter customFontsAdapter = new SpinnerAdapter(getActivity(), R.layout.spiner_item, customFonts, true);
        customFont.setAdapter(customFontsAdapter);
        customFont.setSelection(Config.getFontType());
        customFont.setOnItemSelectedListener(null);
        view.findViewById(R.id.custom_font_settings).setOnClickListener(listener);

        //Extended partition in calc setting
        isExtended = (CheckBox) view.findViewById(R.id.is_extended);
        isExtended.setChecked(Config.getIsExtended());
        view.findViewById(R.id.is_extended_settings).setOnClickListener(listener);

        //Gender setting
        gender = (ToggleButton) view.findViewById(R.id.gender);
        gender.setChecked(Config.getYourGender());
        view.findViewById(R.id.gender_settings).setOnClickListener(listener);

        //Measure setting
        measure = (ToggleButton) view.findViewById(R.id.measure);
        measure.setChecked(Config.getYourMeasure());
        view.findViewById(R.id.measure_settings).setOnClickListener(listener);

        //Reset setting
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

    public int determineWeightCategoryIndex(float weight) {
        //TODO alg: determine weight category
        int weightIndex = 0;
        float[] weightCategories = CategoryManager.getWeightCategories(getActivity());
        int normsSize = weightCategories.length - 1;

        for (int i = 0; i < normsSize; i++) {
            if (weightCategories[i] < weight && weight <= weightCategories[i + 1]) {
                weightIndex = i + 1;
            }
        }

        //Last category with "+"
        if (weightIndex == 0 && weight > weightCategories[normsSize]) {
            weightIndex = normsSize;
        }

        return weightIndex;
    }

    /**
     * Save settings after exit from settings
     * while navigation drawer is open
     */
    @Override
    public void onPause() {
        super.onPause();

        float yourWeight = Float.valueOf(yourWeightText.getText().toString());
        int federationType = priorityFederation.getSelectedItemPosition();
        int fontType = customFont.getSelectedItemPosition();

        Config.setYourWeight(yourWeight);
        Config.setYourFederation(federationType);
        Config.setFontType(fontType);
        Config.setIsExpanded(isExtended.isChecked());
        Config.setYourGender(gender.isChecked());
        Config.setYourMeasure(measure.isChecked());

//        int weightIndex = determineWeightCategoryIndex(yourWeight);

        Config.setYourWeightIndex(2);
        onFontChangeListener.onFontChange();
    }
}
