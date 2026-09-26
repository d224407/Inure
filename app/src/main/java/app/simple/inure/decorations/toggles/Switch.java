package app.simple.inure.decorations.toggles;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.materialswitch.MaterialSwitch;

import app.simple.inure.preferences.AppearancePreferences;
import app.simple.inure.preferences.BehaviourPreferences;
import app.simple.inure.themes.interfaces.ThemeChangedListener;
import app.simple.inure.themes.manager.Accent;
import app.simple.inure.themes.manager.Theme;
import app.simple.inure.themes.manager.ThemeManager;

/**
 * Material 3 switch used by the application.
 *
 * The old implementation drew the control manually with Canvas.  The public
 * compatibility API is kept so existing adapters, dialogs and preferences do
 * not need to be rewritten just to adopt Material 3.
 */
public class Switch extends MaterialSwitch implements SharedPreferences.OnSharedPreferenceChangeListener, ThemeChangedListener {

    private OnCheckedChangeListener switchCheckedChangeListener;
    private OnClickListener switchClickListener;
    private boolean dragEnabled = false;
    private String switchTag;

    public Switch(Context context) {
        super(context);
        init();
    }

    public Switch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Switch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setClickable(true);
        setFocusable(true);
        setContentDescription("Switch");
        applyMaterial3Colors();

        super.setOnCheckedChangeListener((buttonView, checked) -> {
            applyMaterial3Colors();
            if (switchCheckedChangeListener != null) {
                switchCheckedChangeListener.onCheckedChanged(checked);
            }
        });

        super.setOnClickListener(v -> {
            if (switchClickListener != null) {
                switchClickListener.onClick(v);
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

        setTrackTintList(new ColorStateList(
                new int[][] {
                        new int[] {-android.R.attr.state_enabled},
                        new int[] {android.R.attr.state_checked},
                        new int[] {}
                },
                new int[] { disabled, accent, off }
        ));

        setThumbTintList(new ColorStateList(
                new int[][] {
                        new int[] {-android.R.attr.state_enabled},
                        new int[] {android.R.attr.state_checked},
                        new int[] {}
                },
                new int[] { disabled, Color.WHITE, off }
        ));
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        applyMaterial3Colors();
    }

    public synchronized void setCheckedSafely(boolean checked) {
        if (getHandler() != null) {
            post(() -> setChecked(checked));
        } else {
            setChecked(checked);
        }
    }

    public void setChecked(boolean checked, boolean animate) {
        // Material 3 supplies the state animation itself.  The boolean is kept
        // for source compatibility with the previous custom control.
        setChecked(checked);
    }

    @Override
    public boolean isChecked() {
        return super.isChecked();
    }

    @Override
    public void toggle() {
        super.toggle();
    }

    public void check() {
        setChecked(true);
    }

    public void uncheck() {
        setChecked(false);
    }

    public void check(boolean animate) {
        setChecked(true, animate);
    }

    public void uncheck(boolean animate) {
        setChecked(false, animate);
    }

    public void updateSwitchState() {
        applyMaterial3Colors();
        invalidate();
    }

    public void setOnSwitchCheckedChangeListener(OnCheckedChangeListener listener) {
        this.switchCheckedChangeListener = listener;
    }

    public void setOnSwitchClickListener(OnClickListener listener) {
        this.switchClickListener = listener;
    }

    public boolean isDragEnabled() {
        return dragEnabled;
    }

    public void setDragEnabled(boolean enabled) {
        // Material 3 Switch does not expose the old custom drag toggle.  Keep
        // the API but leave gesture handling to the platform control.
        dragEnabled = enabled;
    }

    public void visible() {
        setVisibility(View.VISIBLE);
    }

    public void invisible() {
        setVisibility(View.INVISIBLE);
    }

    public void gone() {
        setVisibility(View.GONE);
    }

    public void blinkThumbTwoTimes() {
        // Preserve the feedback affordance using the Material switch animation.
        animate().scaleX(1.08f).scaleY(1.08f).setDuration(100)
                .withEndAction(() -> animate().scaleX(1f).scaleY(1f).setDuration(100).start())
                .start();
    }

    public String getSwitchTag() {
        return switchTag;
    }

    public void setSwitchTag(String tag) {
        switchTag = tag;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, @Nullable String key) {
        if (BehaviourPreferences.COLORED_SHADOWS.equals(key)) {
            applyMaterial3Colors();
        }
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
