package uz.mold.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import uz.mold.Mold;
import uz.mold.MoldIndexFragment;
import uz.mold.R;

public class TestIndexFragment extends MoldIndexFragment {

    public static void open(Activity activity) {
        Mold.openContent(activity, TestIndexFragment.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentView(R.layout.z_test_index);

        rFindViewById(R.id.tv_second).setOnClickListener(v -> addContent(new TestFragment()));
        rFindViewById(R.id.tv_recycler).setOnClickListener(v -> addContent(new TestRecyclerSwipeFragment()));
        rFindViewById(R.id.tv_page).setOnClickListener(v -> addContent(new TestPageFragment()));


        rFindViewById(R.id.tv_page).callOnClick();
    }
}
