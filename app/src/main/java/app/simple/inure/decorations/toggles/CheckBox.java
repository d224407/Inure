package app.simple.inure.decorations.toggles;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.checkbox.MaterialCheckBox;

import app.simple.inure.preferences.AppearancePreferences;
import app.simple.inure.themes.interfaces.ThemeChangedListener;
import app.simple.inure.themes.manager.Accent;
import app.simple.inure.themes.manager.Theme;
import app.simple.inure.themes.manager.ThemeManager;

/** Material 3 checkbox with the legacy Inure callback API preserved. */
public class CheckBox extends MaterialCheckBox implements ThemeChangedListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private OnCheckedChangeListener callback;
    private Drawable legacyCheckedIcon;
    private int duration = 200;
    private float checkIconRatio = 0.8f;
    private float cornerRadius = 10f;

    public CheckBox(Context context) {
        super(context);
        init();
    }

    public CheckBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CheckBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CheckBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setCenterIfNoTextEnabled(true);
        setUseMaterialThemeColors(false);
        legacyCheckedIcon = ContextCompat.getDrawable(getContext(), app.simple.inure.R.drawable.ic_check);
        if (legacyCheckedIcon != null) {
            setButtonIconDrawable(legacyCheckedIcon);
            setButtonIconTintList(ColorStateList.valueOf(android.graphics.Color.WHITE));
        }
        applyMaterial3Colors();

        super.setOnCheckedChangeListener((buttonView, checked) -> {
            applyMaterial3Colors();
            if (callback != null) {
                callback.onCheckedChanged(checked);
            }
        });
    }

    private void applyMaterial3Colors() {
        if (isInEditMode()) {
            return;
        }

        int accent = AppearancePreferences.INSTANCE.getAccentColor();
        int off = ThemeManager.INSTANCE.getTheme().getSwitchViewTheme().getSwitchOffColor();
        int disabled = ThemeManager.INSTANCE.getTheme().getTextViewTheme().getQuaternaryTextColor();

        setButtonTintList(new ColorStateList(
                new int[][] {
                        new int[] {-android.R.attr.state_enabled},
                        new int[] {android.R.attr.state_checked},
                        new int[] {}
                },
                new int[] { disabled, accent, off }
        ));
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        applyMaterial3Colors();
    }

    public void setChecked(boolean checked, boolean animate) {
        // Material 3 owns the check-state animation.
        setChecked(checked);
    }

    public void toggle(boolean animate) {
        super.toggle();
    }

    public void animateToggle() {
        super.toggle();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getCheckIconRatio() {
        return checkIconRatio;
    }

    public void setCheckIconRatio(float ratio) {
        checkIconRatio = ratio;
    }

    public float getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public Drawable getCheckedIcon() {
        return legacyCheckedIcon;
    }

    public void setCheckedIcon(Drawable drawable) {
        legacyCheckedIcon = drawable;
        setButtonIconDrawable(drawable);
    }

    public void setCheckedIconColor(int color) {
        if (legacyCheckedIcon != null) {
            legacyCheckedIcon.setTint(color);
        }
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        callback = listener;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, @Nullable String key) {
        applyMaterial3Colors();
    }

    @Override
    public void onThemeChanged(@NonNull Theme theme, boolean animate) {
        ThemeChangedListener.super.onThemeChanged(theme, animate);
        applyMaterial3Colors();
        postInvalidateOnAnimation();
    }

    @Override
    public void onAccentChanged(@NonNull Accent accent) {
        ThemeChangedListener.super.onAccentChanged(accent);
        applyMaterial3Colors();
        postInvalidateOnAnimation();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ThemeManager.INSTANCE.addListener(this);
        if (!isInEditMode()) {
            app.simple.inure.preferences.SharedPreferences.INSTANCE.registerSharedPreferencesListener(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        ThemeManager.INSTANCE.removeListener(this);
        app.simple.inure.preferences.SharedPreferences.INSTANCE.unregisterSharedPreferenceChangeListener(this);
        super.onDetachedFromWindow();
    }
}
