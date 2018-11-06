package uz.mold;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public class MoldContentHeaderFooterFragment extends MoldContentFragment {

    @Override
    protected int getContentResourceId() {
        return R.layout.mold_content_header_footer;
    }

    //----------------------------------------------------------------------------------------------

    public void setHeader(@NonNull View view) {
        if (!isAdded()) return;
        FrameLayout fl = viewGroup(R.id.header);
        fl.removeAllViews();
        fl.addView(view);
        fl.setVisibility(View.VISIBLE);
        fl.requestLayout();
    }

    public void setHeader(@NonNull ViewSetup view) {
        if (!isAdded()) return;
        FrameLayout fl = viewGroup(R.id.header);
        fl.removeAllViews();
        fl.addView(view.view);
        fl.setVisibility(View.VISIBLE);
        fl.requestLayout();
    }

    @Nullable
    public ViewSetup setHeader(@LayoutRes int layoutId) {
        if (!isAdded()) return null;
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(layoutId, null);
        setHeader(view);
        return new ViewSetup(view);
    }

    @Nullable
    public View getHeader() {
        if (!isAdded()) return null;
        FrameLayout fl = viewGroup(R.id.header);
        if (fl.getChildCount() > 0) {
            return fl.getChildAt(0);
        }
        return null;
    }

    //----------------------------------------------------------------------------------------------

    public void setFooter(@NonNull View view) {
        if (!isAdded()) return;
        FrameLayout fl = viewGroup(R.id.footer);
        fl.removeAllViews();
        fl.addView(view);
        fl.setVisibility(View.VISIBLE);
        fl.requestLayout();
    }

    public void setFooter(@NonNull ViewSetup view) {
        if (!isAdded()) return;
        FrameLayout fl = viewGroup(R.id.footer);
        fl.removeAllViews();
        fl.addView(view.view);
        fl.setVisibility(View.VISIBLE);
        fl.requestLayout();
    }

    @Nullable
    public ViewSetup setFooter(@LayoutRes int layoutId) {
        if (!isAdded()) return null;
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(layoutId, null);
        setFooter(view);
        return new ViewSetup(view);
    }

    @Nullable
    public View getFooter() {
        if (!isAdded()) return null;
        FrameLayout fl = viewGroup(R.id.footer);
        if (fl.getChildCount() > 0) {
            return fl.getChildAt(0);
        }
        return null;
    }

    //----------------------------------------------------------------------------------------------
}
