package com.powerlifting.calculator.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.powerlifting.calculator.Config;
import com.powerlifting.calculator.R;
import com.powerlifting.calculator.Utils;
import com.powerlifting.calculator.adapters.ViewPagerAdapter;

import static android.support.v4.view.ViewPager.OnPageChangeListener;
import static android.view.View.OnClickListener;


public class CalcFragment extends Fragment {

    private final static String[] SPINNER_DATA = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private Activity activity;
    private EditText enteredWeight;
    private Spinner spinner;
    private ViewPager viewPager;
    private int type = 0;
    private LinearLayout indicator;
    private TextView benchPressButton;

    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) indicator.getLayoutParams();
            params.leftMargin = (int) (indicator.getWidth() * positionOffset) + indicator.getWidth() * position;
            indicator.setLayoutParams(params);
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
            Config.getInstance(activity).setWeightAndRepsByType(weight, spinner.getSelectedItemPosition(), type);
            Utils.hideKeyBoard(activity, enteredWeight);
            viewPager.getAdapter().notifyDataSetChanged();
        }
    };

    private OnClickListener ratioGroupButtonListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bench_press_button:
                    viewPager.setCurrentItem(0, true);
                    break;
                case R.id.squat_button:
                    viewPager.setCurrentItem(1, true);
                    break;
                case R.id.deadlift_button:
                    viewPager.setCurrentItem(2, true);
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();
        View view = inflater.inflate(R.layout.calc_fragment, null);

        view.findViewById(R.id.calculate).setOnClickListener(onClickListener);
        view.findViewById(R.id.squat_button).setOnClickListener(ratioGroupButtonListener);
        view.findViewById(R.id.deadlift_button).setOnClickListener(ratioGroupButtonListener);

        indicator = (LinearLayout) view.findViewById(R.id.indicator);

        benchPressButton = (TextView) view.findViewById(R.id.bench_press_button);
        benchPressButton.setOnClickListener(ratioGroupButtonListener);
        benchPressButton.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                benchPressButton.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                indicator.getLayoutParams().width = benchPressButton.getWidth();
                benchPressButton.setBackgroundDrawable(null);
            }
        });

        String weight = Float.toString(Config.getWeightByType(0));
        enteredWeight = (EditText) view.findViewById(R.id.entered_weight);
        enteredWeight.setText(weight);
        enteredWeight.setSelection(weight.length());

        spinner = (Spinner) view.findViewById(R.id.entered_reps);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_spinner_item, SPINNER_DATA);
        spinnerAdapter.setDropDownViewResource(R.layout.spiner_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(Config.getRepsByType(0));

        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(activity);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOnPageChangeListener(onPageChangeListener);

        return view;
    }

}