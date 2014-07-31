package com.powerlifting.calc;


import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class Utils {

    public static float round(float val) {
        float ans = Math.round(val * 10);
        ans /= 10;
        return ans;
    }

    public static void saveVal(String type, float val, Context context) {
        SharedPreferences pref;
        pref = context.getSharedPreferences("settings", 0);
        SharedPreferences.Editor ed = pref.edit();
        ed.putFloat(type, val);
        ed.commit();
    }

    public static float loadVal(String type, Context context) {
        SharedPreferences pref;
        pref = context.getSharedPreferences("settings", 0);
        return pref.getFloat(type, 0);
    }

    public static float[][] calculateWeights(float weight, int reps, int type) {
        float[][] weights = new float[2][10];

        float maxWeight = getMaxWeightByType(weight, reps, type);
        float percent = maxWeight / 100;

        for (int i = 9; i >= 0; i--) {
            weights[0][i] = Utils.round(maxWeight / Config.COEFFICIENTS[type][i]);
            weights[1][i] = Utils.round(weights[0][i] / percent);
        }
        return weights;
    }

    public static float getMaxWeightByType(float weight, int reps, int type) {
        return Utils.round(Config.COEFFICIENTS[type][reps] * weight);
    }

    public static void hideKeyBoard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        editText.clearFocus();
    }

    public static String[][] getNormsByType(int type, Context context) {
        String[] data = null;
        if (!Config.getYourGender()) {
            switch (type) {
                case 0:
                    data = context.getResources().getStringArray(R.array.ipf_norms_male);
                    break;
                case 1:
                    data = context.getResources().getStringArray(R.array.wpc_norms_male);
                    break;
                case 2:
                    data = context.getResources().getStringArray(R.array.awpc_norms_male);
                    break;
            }
        } else {
            switch (type) {
                case 0:
                    data = context.getResources().getStringArray(R.array.ipf_norms_female);
                    break;
                case 1:
                    data = context.getResources().getStringArray(R.array.wpc_norms_female);
                    break;
                case 2:
                    data = context.getResources().getStringArray(R.array.awpc_norms_female);
                    break;
            }
        }

        assert data != null;
        String[][] normsData = new String[data.length][data.length];
        for (int i = 0; i < data.length; i++) {
            normsData[i] = data[i].split(" ");
        }

        return normsData;
    }

    public static void reset(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("settings", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

}
