package uz.mold;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

public class UI {

    //----------------------------------------------------------------------------------------------

    public static void hideSoftKeyboard(@NonNull Activity activity) {
        View focus = activity.getCurrentFocus();
        if (focus == null) return;
        InputMethodManager ims = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (ims != null) ims.hideSoftInputFromWindow(focus.getWindowToken(), 0);
    }

    //----------------------------------------------------------------------------------------------

    @NonNull
    public static Snackbar makeSnackBar(@NonNull Activity activity, @NonNull CharSequence messages) {
        return Snackbar.make(Mold.cast(activity).getCoordinateLayout(), messages, Snackbar.LENGTH_SHORT);
    }

    @NonNull
    public static Snackbar makeSnackBar(@NonNull Activity activity, @StringRes int strResId) {
        return makeSnackBar(activity, activity.getString(strResId));
    }

    //----------------------------------------------------------------------------------------------

    @Nullable
    public static Drawable changeDrawableColor(@NonNull Context ctx, @DrawableRes int resId, @ColorRes int resColorId) {
        if (resId == 0 || resColorId == 0) return null;

        Resources res = ctx.getResources();
        Drawable drawable = res.getDrawable(resId);
        Drawable.ConstantState constantState = drawable.getConstantState();

        if (constantState == null) return null;

        drawable = constantState.newDrawable().mutate();
        int color = res.getColor(resColorId);
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        return drawable;
    }

    //----------------------------------------------------------------------------------------------

    public static void setMargins(@NonNull View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }


    //----------------------------------------------------------------------------------------------
}
