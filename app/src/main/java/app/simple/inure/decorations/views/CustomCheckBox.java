package app.simple.inure.decorations.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;

import com.google.android.material.checkbox.MaterialCheckBox;

import app.simple.inure.R;
import app.simple.inure.util.ColorUtils;

public class CustomCheckBox extends MaterialCheckBox {

    public CustomCheckBox(Context context) {
        super(context);
        init(context);
    }

    public CustomCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setBackgroundColor(Color.TRANSPARENT);
        setButtonTintList(ColorStateList.valueOf(ColorUtils.INSTANCE.resolveAttrColor(context, R.attr.colorAppAccent)));
    }
}
