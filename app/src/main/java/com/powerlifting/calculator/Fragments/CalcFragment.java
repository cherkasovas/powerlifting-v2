package com.powerlifting.calculator.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.powerlifting.calculator.Config;
import com.powerlifting.calculator.R;
import com.powerlifting.calculator.adapters.ViewPagerAdapter;

import static android.support.v4.view.ViewPager.OnPageChangeListener;
import static android.view.View.OnClickListener;
import static android.widget.AdapterView.OnItemSelectedListener;


public class CalcFragment extends Fragment {

    private final static String[] SPINNER_DATA = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private Activity activity;
    private Config config;
    private EditText enteredWeight;
    private Spinner spinner;
    private ViewPager viewPager;

    private int reps;
    private OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            reps = position;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private int type = 0;
    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int page) {
            type = page;
            String weight = Float.toString(Config.getWeightByType(type));
            enteredWeight.setText(weight);
            enteredWeight.setSelection(weight.length());
            spinner.setSelection(Config.getRepsByType(type));

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            float weight = Float.parseFloat(String.valueOf(enteredWeight.getText()));
            config.setWeightAndRepsByType(weight, reps, type);

            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(activity);
            viewPager.setAdapter(viewPagerAdapter);
            viewPager.setCurrentItem(type);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();
        config = Config.getInstance(activity);
        View view = inflater.inflate(R.layout.calc_fragment, null);

        ImageButton calculateButton = (ImageButton) view.findViewById(R.id.calculate);
        calculateButton.setOnClickListener(onClickListener);

        enteredWeight = (EditText) view.findViewById(R.id.entered_weight);
        String weight = Float.toString(Config.getWeightByType(0));
        enteredWeight.setText(weight);
        enteredWeight.setSelection(weight.length());

        spinner = (Spinner) view.findViewById(R.id.entered_reps);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_spinner_item, SPINNER_DATA);
        spinnerAdapter.setDropDownViewResource(R.layout.spiner_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(Config.getRepsByType(0));
        spinner.setOnItemSelectedListener(onItemSelectedListener);


        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setOnPageChangeListener(onPageChangeListener);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(activity);
        viewPager.setAdapter(viewPagerAdapter);

        return view;
    }

}