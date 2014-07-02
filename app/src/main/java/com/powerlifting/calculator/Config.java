package com.powerlifting.calculator;

public class Config {
    public static float pressWeight;
    public static int pressReps;
    public static float deadliftWeight;
    public static int deadliftReps;
    public static float squatWeight;
    public static int squatReps;

    public static final int PRESS_TYPE = 0;
    public static final int DEADLIFT_TYPE = 1;
    public static final int SQUAT_TYPE = 2;

    public static final float[][] COEFFICIENTS = {
            {1.0f, 1.035f, 1.08f, 1.115f, 1.15f, 1.18f, 1.22f, 1.255f, 1.29f, 1.325f},
            {1.0f, 1.0475f, 1.13f, 1.1575f, 1.2f, 1.242f, 1.284f, 1.326f, 1.386f, 1.41f},
            {1.0f, 1.065f, 1.13f, 1.147f, 1.164f, 1.181f, 1.198f, 1.232f, 1.236f, 1.24f}
    };

}
