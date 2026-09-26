package app.simple.inure.decorations.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.radiobutton.MaterialRadioButton;

import app.simple.inure.preferences.AppearancePreferences;
import app.simple.inure.themes.interfaces.ThemeChangedListener;
import app.simple.inure.themes.manager.Accent;
import app.simple.inure.themes.manager.Theme;
import app.simple.inure.themes.manager.ThemeManager;
import app.simple.inure.util.TypeFace;

public class InureRadioButton extends MaterialRadioButton implements ThemeChangedListener {

    public InureRadioButton(Context context) {
        super(context);
        init();
    }

    public InureRadioButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InureRadioButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setTextColor(ThemeManager.INSTANCE.getTheme().getTextViewTheme().getPrimaryTextColor());
        if (!isInEditMode()) {
            setTypeface(TypeFace.INSTANCE.getBoldTypeFace(getContext()));
        }
        applyTint();
    }

    private void applyTint() {
        if (isInEditMode()) return;
        int accent = AppearancePreferences.INSTANCE.getAccentColor();
        int off = ThemeManager.INSTANCE.getTheme().getSwitchViewTheme().getSwitchOffColor();
        setButtonTintList(new ColorStateList(
                new int[][] {
                        new int[] {-android.R.attr.state_enabled},
                        new int[] {android.R.attr.state_checked},
                        new int[] {}
                },
                new int[] {
                        ThemeManager.INSTANCE.getTheme().getTextViewTheme().getQuaternaryTextColor(),
                        accent,
                        off
                }
        ));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ThemeManager.INSTANCE.addListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ThemeManager.INSTANCE.removeListener(this);
    }

    @Override
    public void onThemeChanged(@NonNull Theme theme, boolean animate) {
        ThemeChangedListener.super.onThemeChanged(theme, animate);
        init();
    }

    @Override
    public void onAccentChanged(@NonNull Accent accent) {
        ThemeChangedListener.super.onAccentChanged(accent);
        applyTint();
    }
}
