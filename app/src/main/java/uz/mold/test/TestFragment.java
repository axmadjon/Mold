package uz.mold.test;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import uz.mold.Mold;
import uz.mold.MoldContentFragment;
import uz.mold.R;
import uz.mold.UI;

public class TestFragment extends MoldContentFragment {

    public static void open(Activity activity) {
        Mold.openContent(activity, TestFragment.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentView(R.layout.z_test_content);

        setSearchMenu(s -> System.out.println(s));
        addMenu(R.drawable.ic_search_white_24dp, "Search", () -> UI.makeSnackBar(getActivity(), "Hello").show());

        addSubMenu("RecyclerView", () -> TestRecyclerSwipeFragment.open(getActivity()));
        addSubMenu("PageView", () -> TestPageFragment.open(getActivity()));
        addSubMenu("Index", () -> TestIndexFragment.open(getActivity()));

        UI.setTitle(this, "Mold Frame Title");
        UI.setSubtitle(this, "This is subtitle");

        UI.setElevation(UI.setAppBarExpand(this), 5f);

        findViewById(R.id.tv_message).setOnClickListener(view -> Mold.addContent(getActivity(), new TestSecondFragment()));
    }
}
