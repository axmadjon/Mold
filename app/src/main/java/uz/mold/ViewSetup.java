package uz.mold;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class ViewSetup {

    @NonNull
    public final View view;

    private SparseArray<View> cachedViews = new SparseArray<>();

    public ViewSetup(@NonNull View parent) {
        this.view = parent;
    }

    public ViewSetup(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, int layoutId) {
        this(inflater.inflate(layoutId, container, false));
    }

    public ViewSetup(@NonNull LayoutInflater inflater, int layoutId) {
        this(inflater.inflate(layoutId, null, false));
    }

    public ViewSetup(@NonNull Context context, @LayoutRes int layoutId) {
        this(LayoutInflater.from(context), null, layoutId);
    }

    public ViewSetup(@NonNull Fragment fragment, @LayoutRes int layoutId) {
        this(fragment.getContext(), layoutId);
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public <T extends View> T id(@IdRes int resId) {
        View v = cachedViews.get(resId);
        if (v == null) {
            v = view.findViewById(resId);
            if (v == null) {
                throw new NullPointerException("view not found, is null, check resource id");
            }
            cachedViews.put(resId, v);
        }
        return (T) v;
    }

    @NonNull
    public TextView textView(@IdRes int resId) {
        return id(resId);
    }

    @NonNull
    public ImageView imageView(@IdRes int resId) {
        return id(resId);
    }

    @NonNull
    public EditText editText(@IdRes int resId) {
        return id(resId);
    }

    @NonNull
    public RatingBar ratingBar(@IdRes int resId) {
        return id(resId);
    }

    @NonNull
    public Button button(@IdRes int resId) {
        return id(resId);
    }

    @NonNull
    public ImageButton imageButton(@IdRes int resId) {
        return id(resId);
    }

    @NonNull
    public <T extends CompoundButton> T compoundButton(@IdRes int resId) {
        return id(resId);
    }

    @NonNull
    public CheckBox checkBox(@IdRes int resId) {
        return id(resId);
    }

    @NonNull
    public Spinner spinner(@IdRes int resId) {
        return id(resId);
    }

    @NonNull
    public <T extends ViewGroup> T viewGroup(@IdRes int resId) {
        return id(resId);
    }

    @NonNull
    public RadioButton radioButton(@IdRes int resId) {
        return id(resId);
    }

    @NonNull
    public LinearLayout linearLayout(@IdRes int resId) {
        return id(resId);
    }

    @NonNull
    public RecyclerView recyclerView(@IdRes int resId) {
        return id(resId);
    }

    public void visible(@IdRes int resId, boolean visible) {
        id(resId).setVisibility(visible ? View.VISIBLE : View.GONE);
    }

}
