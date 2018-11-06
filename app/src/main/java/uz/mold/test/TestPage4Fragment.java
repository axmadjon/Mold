package uz.mold.test;

import android.os.Bundle;
import android.support.annotation.Nullable;

import uz.mold.MoldContentFragment;
import uz.mold.R;
import uz.mold.UI;

public class TestPage4Fragment extends MoldContentFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentView(R.layout.z_test_content);

        addMenu(R.drawable.ic_search_white_24dp, "Search", () -> UI.makeSnackBar(getActivity(), "Hello").show());
        addMenu(R.drawable.ic_search_white_24dp, "Search", () -> UI.makeSnackBar(getActivity(), "Hello").show());
        addMenu(R.drawable.ic_search_white_24dp, "Search", () -> UI.makeSnackBar(getActivity(), "Hello").show());
        addMenu(R.drawable.ic_search_white_24dp, "Search", () -> UI.makeSnackBar(getActivity(), "Hello").show());

    }
}
