package com.powerlifting.calc;

import android.content.Context;

public class CategoryManager {

    private Context context;
    private Float yourWeight;
    private Integer yourFederation;
    private Float[][] norms;

    public CategoryManager(Context context) {
        this.context = context;
        this.yourWeight = Config.getYourWeight();
        this.yourFederation = Config.getYourFederation();
        this.norms = getNorms();
    }

    public Integer getYourWeightCategory() {
        if (yourWeight == 0) {
            return null;
        }

        Float weightCategory = norms[0][0];
        Integer weightCategoryIndex = 0;
        for (int i = 0; i < norms.length - 1; i++) {
            if (norms[i][0] < yourWeight && yourWeight <= norms[i + 1][0]) {
                weightCategory = norms[i + 1][0];
                weightCategoryIndex = i + 1;
            }
        }

//        if (yourWeight > norms[norms.length - 1][0]) {
//            weightCategory = norms[norms.length - 1][0];
//        }

        return weightCategoryIndex;
    }

    private Float[][] getNorms() {
        String federations[] = context.getResources().getStringArray(R.array.federations);
        String resourceName = federations[yourFederation] + "_norms_";
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

        return arrayConvert(norms);
    }

    private Float[][] arrayConvert(String[][] array) {
        Float[][] convertedArray = new Float[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                convertedArray[i][j] = Float.parseFloat(array[i][j]);
            }
        }
        return convertedArray;
    }


//    public void asd() {
//        String[] categories = context.getResources().getStringArray(R.array.categories);
//        String[] federations = context.getResources().getStringArray(R.array.federations_names);
//
//        data = context.getResources().getStringArray(R.array.ipf_norms_male);
//
//        for (int i = 0; i < data.length; i++) {
//            normsData[i] = data[i].split(" ");
//        }
//
//    }

}
