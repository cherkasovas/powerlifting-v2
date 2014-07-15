package com.powerlifting.calculator;


import android.content.Context;
import android.content.SharedPreferences;

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
        float myStat = pref.getFloat(type, 0);
        return myStat;
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
}
