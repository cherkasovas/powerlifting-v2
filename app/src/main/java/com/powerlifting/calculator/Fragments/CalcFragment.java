package com.powerlifting.calculator.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.powerlifting.calculator.Config;
import com.powerlifting.calculator.R;
import com.powerlifting.calculator.adapters.ViewPagerAdapter;


public class CalcFragment extends Fragment {

    private static String[] data = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private int enteredReps;
    private Activity activity;
    private int selectedType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calc_fragment, null);
        activity = getActivity();
        Button calculate = (Button) view.findViewById(R.id.calculate);
        final EditText enteredWeight = (EditText) view.findViewById(R.id.entered_weight);
        final Spinner spinner = (Spinner) view.findViewById(R.id.entered_reps);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int type) {
                selectedType = type;
                enteredWeight.setText(String.valueOf(Config.getWeightByType(type)));
                spinner.setSelection(Config.getRepsByType(type));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(activity);
        viewPager.setAdapter(viewPagerAdapter);
        ArrayAdapter<String> SpinnerAdapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_spinner_item, data);
        SpinnerAdapter.setDropDownViewResource(R.layout.spiner_item);


        spinner.setAdapter(SpinnerAdapter);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                enteredReps = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float weight = Float.parseFloat(String.valueOf(enteredWeight.getText()));
                viewPagerAdapter.updateTable(weight, enteredReps, selectedType);
                Config config = Config.getInstance(activity);
                switch (selectedType) {
                    case 0:
                        config.setPressWeight(weight);
                        config.setPressReps(enteredReps);
                        break;
                    case 1:
                        config.setSquatWeight(weight);
                        config.setSquatReps(enteredReps);
                        break;
                    case 2:
                        config.setDeadliftWeight(weight);
                        config.setDeadliftReps(enteredReps);
                        break;
                }
            }
        });

        return view;
    }

}