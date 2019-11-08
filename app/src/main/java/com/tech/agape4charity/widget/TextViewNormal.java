package com.tech.agape4charity.widget;

import android.content.Context;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by Charitha Ratnayake on 6/5/2018.
 */

public class TextViewNormal extends AppCompatTextView {
    public TextViewNormal(Context context, AttributeSet attrs) {
        super(context, attrs);

        setFont(context);
    }

    public void setFont(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),
                "fonts/OpenSans-Regular.ttf");
        this.setTypeface(typeface);
    }
}
