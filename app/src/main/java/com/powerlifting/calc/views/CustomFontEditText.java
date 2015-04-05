package com.powerlifting.calc.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.powerlifting.calc.Config;
import com.powerlifting.calc.TypeFaceProvider;

public class CustomFontEditText extends EditText {
    public CustomFontEditText(Context context) {
        super(context);
        setCustomFont();
    }

    public CustomFontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont();
    }

    public CustomFontEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont();
    }

    private void setCustomFont() {
        int fontIndex = Config.getFontType();
        if (fontIndex == 0) {
            return;
        }
        String customFont = Integer.toString(fontIndex) + ".ttf";
        this.setTypeface(TypeFaceProvider.getTypeFace(getContext(), customFont));
    }
}
