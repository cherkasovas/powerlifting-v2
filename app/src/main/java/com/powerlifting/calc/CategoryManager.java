package com.powerlifting.calc;

import android.content.Context;
import android.util.Log;

import java.nio.channels.FileLock;

public class CategoryManager {

    private static final String inf = Character.toString('∞');
    private Context context;

    public CategoryManager(Context context) {
        this.context = context;
    }

    public static String[][] getNormsString(Context context) {
        String federations[] = context.getResources().getStringArray(R.array.federations);
        String resourceName = federations[Config.getYourFederation()] + "_norms_";
        if (!Config.getYourGender()) {
            resourceName += "male";
        } else {
            resourceName += "female";
        }

        int id = context.getResources().getIdentifier(resourceName, "array", context.getPackageName());
        String[] data = context.getResources().getStringArray(id);
        String[][] norms = new String[data.length][data.length];
        for (int i = 0; i < data.length; i++) {
            norms[i] = data[i].split(" ");
        }
        return norms;
    }

    public static Float[][] getNormsFloat(Context context) {
        String[][] array = getNormsString(context);
        Float[][] convertedArray = new Float[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                convertedArray[i][j] = Float.parseFloat(array[i][j]);
            }
        }
        return convertedArray;
    }

    public static float[] getWeightCategories(Context context) {
        String[][] array = getNormsString(context);
        float[] weightCategories = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            weightCategories[i] = Float.parseFloat(array[i][0]);
        }

        return weightCategories;
    }

    public static String[] getSportsCategories(Context context) {
        String sportsCategory[] = context.getResources().getStringArray(R.array.categories);
        return sportsCategory;
    }

    public static float determineSportCategory(Context context, float maxWeight) {
        String[][] norms = getNormsString(context);
        int indexWC = Config.getYourWeightIndex();
        int categoryIndex = 0;
        float categoryWeight = 0;
        for (int i = norms[0].length-1; i > 0; i--) {
            if (Float.parseFloat(norms[indexWC][i]) < maxWeight
                    && maxWeight <= Float.parseFloat(norms[indexWC][i - 1])) {
                categoryIndex = i - 1;
                categoryWeight = Float.parseFloat(norms[indexWC][categoryIndex]);
            }
        }

        Config.setYourCategoryIndex(categoryIndex);
        return categoryWeight;
    }


    public static String[] getNeedWeights(Context context, float[] maxWeights) {
        String[] needWeights = {inf, inf, inf, inf, inf};

        //Free powerlifter weight(need)
        float[] weightCategories = CategoryManager.getWeightCategories(context);
        needWeights[4] = Float.toString(Utils.round(weightCategories[Config.getYourWeightIndex()]) - maxWeights[3]);


        float pressWeight = Config.getPressWeight();
        float squatWeight = Config.getSquatWeight();
        float deadliftWeight = Config.getDeadliftWeight();
        float sumWeight = pressWeight + squatWeight + deadliftWeight;

        float sumWeightNeed =  determineSportCategory(context, sumWeight);


        if (pressWeight == 0 || squatWeight == 0 || deadliftWeight == 0 || sumWeightNeed == 0) {
            return needWeights;
        }
        float diff = sumWeightNeed - sumWeight;
        float pressWeightNeed = Utils.round(diff * pressWeight / sumWeight);
        float squatWeightNeed = Utils.round(diff * squatWeight / sumWeight);
        float deadliftWeightNeed = Utils.round(diff - pressWeightNeed - squatWeightNeed);

        needWeights[0] = pressWeightNeed + "";
        needWeights[1] = squatWeightNeed + "";
        needWeights[2] = deadliftWeightNeed + "";
        needWeights[3] = (pressWeightNeed + squatWeightNeed + deadliftWeightNeed) + " ";

        return needWeights;
    }

}
