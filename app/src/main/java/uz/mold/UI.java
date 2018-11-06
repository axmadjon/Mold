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
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import java.util.Objects;

public class UI {

    //----------------------------------------------------------------------------------------------

    @Nullable
    public static Toolbar getToolbar(MoldContentFragment content) {
        return (Toolbar) content.findViewById(R.id.toolbar);
    }

    public static void setTitle(Activity activity, CharSequence title) {
        MoldActivity moldActivity = Mold.cast(activity);
        Objects.requireNonNull(moldActivity.getSupportActionBar()).setTitle(title);
    }

    public static void setTitle(MoldContentFragment content, CharSequence title) {
        setTitle(content.getActivity(), title);
    }

    public static void setSubtitle(Activity activity, CharSequence subtitle) {
        MoldActivity moldActivity = Mold.cast(activity);
        Objects.requireNonNull(moldActivity.getSupportActionBar()).setSubtitle(subtitle);
    }

    public static void setSubtitle(MoldContentFragment content, CharSequence subtitle) {
        setSubtitle(content.getActivity(), subtitle);
    }


    //----------------------------------------------------------------------------------------------

    @Nullable
    public static AppBarLayout setAppBarExpanded(@NonNull MoldContentFragment content, boolean expanded, boolean animate) {
        AppBarLayout appBarLayout = content.findViewById(R.id.app_bar_layout);
        if (appBarLayout != null) appBarLayout.setExpanded(expanded, animate);
        return appBarLayout;
    }

    @Nullable
    public static AppBarLayout setAppBarExpanded(@NonNull MoldContentFragment content, boolean expanded) {
        return setAppBarExpanded(content, expanded, true);
    }

    @Nullable
    public static AppBarLayout setAppBarExpand(@NonNull MoldContentFragment content) {
        return setAppBarExpanded(content, true);
    }

    @Nullable
    public static AppBarLayout setAppBarCollapse(@NonNull MoldContentFragment content) {
        return setAppBarExpanded(content, false);
    }


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
        View rootView = activity.findViewById(R.id.mold_coordinate_layout);
        return Snackbar.make(rootView, messages, Snackbar.LENGTH_SHORT);
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

    public static void setElevation(@Nullable View view, float elevation) {
        if (view != null) {
            ViewCompat.setElevation(view, elevation);
            ViewCompat.setTranslationZ(view, elevation);
        }
    }

    //----------------------------------------------------------------------------------------------
}
