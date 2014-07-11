package com.powerlifting.calculator;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class Utils {

    public static float round(float val) {
        float ans = Math.round(val * 10);
        ans /= 10;
        return ans;
    }

    public static int dpToPx(float dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
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
        float myStat = pref.getFloat(type, 0);
        return myStat;
    }

    public static float pxToDp(int px) {
        return (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static float[][] calculateWeights(float weight, int reps, int type) {
        float[][] weights = new float[2][10];

        float maxWeight = Utils.round(Config.COEFFICIENTS[type][reps] * weight);
        float percent = maxWeight / 100;

        for (int i = 9; i >= 0; i--) {
            weights[0][i] = Utils.round(maxWeight / Config.COEFFICIENTS[type][i]);
            weights[1][i] = Utils.round(weights[0][i] / percent);
        }
        return weights;
    }

}
