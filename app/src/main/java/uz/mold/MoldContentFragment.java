package uz.mold;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MoldContentFragment extends MoldFragment {

    @Override
    protected int getContentResourceId() {
        return R.layout.mold_content;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        Mold.cast(getActivity()).setSupportActionBar(toolbar);
    }

    protected void setContentView(@LayoutRes int resId) {
        setContentView(LayoutInflater.from(getActivity()).inflate(resId, null));
    }

    protected void setContentView(@NonNull View view) {
        ViewGroup content = findViewById(R.id.fl_mold_fragment_content);
        content.removeAllViews();
        content.addView(view);
    }
}
