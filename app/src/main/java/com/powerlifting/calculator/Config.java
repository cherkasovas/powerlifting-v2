package com.powerlifting.calculator;

import android.content.Context;

public class Config {
    private static float pressWeight;
    private static int pressReps;
    private static float deadliftWeight;
    private static int deadliftReps;
    private static float squatWeight;
    private static int squatReps;
    private static Config instance;
    private Context context;

    private final static String BENCH_PRESS = "bench_press";
    private final static String SQUAT = "squat";
    private final static String DEADLIFT = "deadlift";

    private final static String BENCH_PRESS_REPS = "bench_press_reps";
    private final static String SQUAT_REPS = "squat_reps";
    private final static String DEADLIFT_REPS = "deadlift_reps";

    public static final float[][] COEFFICIENTS = {
            {1.0f, 1.035f, 1.08f, 1.115f, 1.15f, 1.18f, 1.22f, 1.255f, 1.29f, 1.325f},
            {1.0f, 1.0475f, 1.13f, 1.1575f, 1.2f, 1.242f, 1.284f, 1.326f, 1.386f, 1.41f},
            {1.0f, 1.065f, 1.13f, 1.147f, 1.164f, 1.181f, 1.198f, 1.232f, 1.236f, 1.24f}
    };

    public Config(Context context) {
        this.context = context;
        this.init();
    }

    public static Config getInstance(Context context) {
        if (instance == null) {
            instance = new Config(context);
        }
        return instance;
    }

    private void init() {
        pressWeight = Utils.loadVal(BENCH_PRESS, context);
        squatWeight = Utils.loadVal(SQUAT, context);
        deadliftWeight = Utils.loadVal(DEADLIFT, context);

        pressReps = (int) Utils.loadVal(BENCH_PRESS_REPS, context);
        squatReps = (int) Utils.loadVal(SQUAT_REPS, context);
        deadliftReps = (int) Utils.loadVal(DEADLIFT_REPS, context);
    }

    public void saveAll() {
        Utils.saveVal(BENCH_PRESS, pressWeight, context);
        Utils.saveVal(SQUAT, squatWeight, context);
        Utils.saveVal(DEADLIFT, deadliftWeight, context);

        Utils.saveVal(BENCH_PRESS_REPS, pressReps, context);
        Utils.saveVal(SQUAT_REPS, squatReps, context);
        Utils.saveVal(DEADLIFT_REPS, deadliftReps, context);
    }

    public static float getWeightByType(int type) {
        switch (type) {
            case 0:
                return getPressWeight();
            case 1:
                return  getSquatWeight();
            case 2:
                return getDeadliftWeight();
            default:
                return 0;
        }
    }

    public static int getRepsByType(int type) {
        switch (type) {
            case 0:
                return getPressReps();
            case 1:
                return  getSquatReps();
            case 2:
                return getDeadliftReps();
            default:
                return 0;
        }
    }

    public static float getPressWeight() {
        return pressWeight;
    }

    public static int getPressReps() {
        return pressReps;
    }

    public static float getDeadliftWeight() {
        return deadliftWeight;
    }

    public static int getDeadliftReps() {
        return deadliftReps;
    }

    public static float getSquatWeight() {
        return squatWeight;
    }

    public static int getSquatReps() {
        return squatReps;
    }

    public static void setPressWeight(float pressWeight) {
        Config.pressWeight = pressWeight;
    }

    public static void setPressReps(int pressReps) {
        Config.pressReps = pressReps;
    }

    public static void setDeadliftWeight(float deadliftWeight) {
        Config.deadliftWeight = deadliftWeight;
    }

    public static void setDeadliftReps(int deadliftReps) {
        Config.deadliftReps = deadliftReps;
    }

    public static void setSquatWeight(float squatWeight) {
        Config.squatWeight = squatWeight;
    }

    public static void setSquatReps(int squatReps) {
        Config.squatReps = squatReps;
    }

}
