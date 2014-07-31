package com.powerlifting.calc;

import android.content.Context;
import android.content.SharedPreferences;

public class Config {
    public static final float[][] COEFFICIENTS = {
            {1.0f, 1.035f, 1.08f, 1.115f, 1.15f, 1.18f, 1.22f, 1.255f, 1.29f, 1.325f},
            {1.0f, 1.0475f, 1.13f, 1.1575f, 1.2f, 1.242f, 1.284f, 1.326f, 1.386f, 1.41f},
            {1.0f, 1.065f, 1.13f, 1.147f, 1.164f, 1.181f, 1.198f, 1.232f, 1.236f, 1.24f}
    };
    private final static String BENCH_PRESS = "bench_press";
    private final static String SQUAT = "squat";
    private final static String DEADLIFT = "deadlift";
    private final static String BENCH_PRESS_REPS = "bench_press_reps";
    private final static String SQUAT_REPS = "squat_reps";
    private final static String DEADLIFT_REPS = "deadlift_reps";
    private final static String YOUR_WEIGHT = "your_weight";
    private final static String YOUR_GENDER = "your_gender";
    private final static String YOUR_FEDERATION = "your_federation";
    private final static String IS_EXPAND = "is_expand";
    private final static String YOUR_WEIGHT_CATEGORY = "your_weight_category";
    private static final String YOUR_WEIGHT_INDEX = "your_weight_index";
    private static final String MENU_ITEM = "menu_item";

    private static float pressWeight;
    private static float deadliftWeight;
    private static float squatWeight;
    private static float yourWeight;

    private static int pressReps;
    private static int deadliftReps;
    private static int squatReps;

    private static boolean isExpanded;
    private static int yourFederation;
    private static boolean yourGender;

    private static Config instance;
    private Context context;
    private static int menuItem;
    private static float yourWeightCategory;
    private static int yourWeightIndex;

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

    public static float getWeightByType(int type) {
        switch (type) {
            case 0:
                return getPressWeight();
            case 1:
                return getSquatWeight();
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
                return getSquatReps();
            case 2:
                return getDeadliftReps();
            default:
                return 0;
        }
    }

    public static float[] getMaxWeights() {
        float weights[] = new float[4];
        weights[0] = Utils.getMaxWeightByType(pressWeight, pressReps, 0);
        weights[1] = Utils.getMaxWeightByType(squatWeight, squatReps, 1);
        weights[2] = Utils.getMaxWeightByType(deadliftWeight, deadliftReps, 2);
        weights[3] = yourWeight;
        return weights;
    }

    public static float getYourWeight() {
        return yourWeight;
    }

    public static void setYourWeight(float yourWeight) {
        Config.yourWeight = yourWeight;
    }

    public static float getPressWeight() {
        return pressWeight;
    }

    public static void setPressWeight(float pressWeight) {
        Config.pressWeight = pressWeight;
    }

    public static int getPressReps() {
        return pressReps;
    }

    public static void setPressReps(int pressReps) {
        Config.pressReps = pressReps;
    }

    public static float getDeadliftWeight() {
        return deadliftWeight;
    }

    public static void setDeadliftWeight(float deadliftWeight) {
        Config.deadliftWeight = deadliftWeight;
    }

    public static int getDeadliftReps() {
        return deadliftReps;
    }

    public static void setDeadliftReps(int deadliftReps) {
        Config.deadliftReps = deadliftReps;
    }

    public static float getSquatWeight() {
        return squatWeight;
    }

    public static void setSquatWeight(float squatWeight) {
        Config.squatWeight = squatWeight;
    }

    public static int getSquatReps() {
        return squatReps;
    }

    public static void setSquatReps(int squatReps) {
        Config.squatReps = squatReps;
    }

    public static int getMenuItem() {
        return menuItem;
    }

    public static void setMenuItem(int menuItem) {
        Config.menuItem = menuItem;
    }

    public static boolean getYourGender() {
        return yourGender;
    }

    public static void setYourGender(boolean yourGender) {
        Config.yourGender = yourGender;
    }

    public static int getYourFederation() {
        return yourFederation;
    }

    public static void setYourFederation(int yourFederation) {
        Config.yourFederation = yourFederation;
    }

    public static Boolean getIsExtended() {
        return isExpanded;
    }

    public static void setIsExpanded(Boolean isExpanded) {
        Config.isExpanded = isExpanded;
    }

    public static void setYourWeightCategory(float yourWeightCategory) {
        Config.yourWeightCategory = yourWeightCategory;
    }

    public static float getYourWeightCategory() {
        return yourWeightCategory;
    }

    public static void setYourWeightIndex(int yourWeightIndex) {
        Config.yourWeightIndex = yourWeightIndex;
    }

    public static int getYourWeightIndex() {
        if (yourWeightIndex != 0) {
            return yourWeightIndex;
        } else {
            return 1;
        }
    }

    public void init() {
        pressWeight = Utils.loadVal(BENCH_PRESS, context);
        squatWeight = Utils.loadVal(SQUAT, context);
        deadliftWeight = Utils.loadVal(DEADLIFT, context);
        yourWeight = Utils.loadVal(YOUR_WEIGHT, context);

        pressReps = (int) Utils.loadVal(BENCH_PRESS_REPS, context);
        squatReps = (int) Utils.loadVal(SQUAT_REPS, context);
        deadliftReps = (int) Utils.loadVal(DEADLIFT_REPS, context);

        isExpanded = getBoolean(Utils.loadVal(IS_EXPAND, context));
        yourFederation = (int) Utils.loadVal(YOUR_FEDERATION, context);
        yourGender = getBoolean(Utils.loadVal(YOUR_GENDER, context));
        yourWeightCategory = Utils.loadVal(YOUR_WEIGHT_CATEGORY, context);
        yourWeightIndex = (int) Utils.loadVal(YOUR_WEIGHT_INDEX, context);
        menuItem = (int) Utils.loadVal(MENU_ITEM, context);
    }

    private boolean getBoolean(float value) {
        return value != 0;
    }

    private int getInt(Boolean b) {
        if (b) {
            return 1;
        } else {
            return 0;
        }
    }

    public void saveAll() {
        Utils.saveVal(BENCH_PRESS, pressWeight, context);
        Utils.saveVal(SQUAT, squatWeight, context);
        Utils.saveVal(DEADLIFT, deadliftWeight, context);
        Utils.saveVal(YOUR_WEIGHT, yourWeight, context);

        Utils.saveVal(BENCH_PRESS_REPS, pressReps, context);
        Utils.saveVal(SQUAT_REPS, squatReps, context);
        Utils.saveVal(DEADLIFT_REPS, deadliftReps, context);

        Utils.saveVal(IS_EXPAND, getInt(isExpanded), context);
        Utils.saveVal(YOUR_FEDERATION, yourFederation, context);
        Utils.saveVal(YOUR_GENDER, getInt(yourGender), context);
        Utils.saveVal(YOUR_WEIGHT_CATEGORY, yourWeightCategory, context);
        Utils.saveVal(YOUR_WEIGHT_INDEX, yourWeightIndex, context);
        Utils.saveVal(MENU_ITEM, menuItem, context);
    }

    public void setWeightAndRepsByType(float weight, int reps, int type) {
        switch (type) {
            case 0:
                setPressWeight(weight);
                setPressReps(reps);
                break;
            case 1:
                setSquatWeight(weight);
                setSquatReps(reps);
                break;
            case 2:
                setDeadliftWeight(weight);
                setDeadliftReps(reps);
                break;
        }
    }

}
