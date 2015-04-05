package com.powerlifting.calc.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.powerlifting.calc.Config;
import com.powerlifting.calc.TypeFaceProvider;

public class CustomFontTextView extends TextView {

    public CustomFontTextView(Context context) {
        super(context);
        setCustomFont();
    }

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont();
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont();
    }

    public void setCustomFont() {
        int fontIndex = Config.getFontType();
        if (fontIndex == 0) {
            return;
        }
        String customFont = Integer.toString(fontIndex) + ".ttf";
        this.setTypeface(TypeFaceProvider.getTypeFace(getContext(), customFont));
    }

    public void setCustomFont(String fontName) {
        if (fontName.equals("0")) {
            return;
        }
        this.setTypeface(TypeFaceProvider.getTypeFace(getContext(), fontName+ ".ttf"));
    }

}
