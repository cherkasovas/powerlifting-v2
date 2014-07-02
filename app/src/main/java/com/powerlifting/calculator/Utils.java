package com.powerlifting.calculator;


public class Utils {

    public static float round(float val) {
        float ans = Math.round(val * 10);
        ans /= 10;
        return ans;
    }

}
