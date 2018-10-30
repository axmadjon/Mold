package uz.mold.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import uz.mold.Mold;
import uz.mold.MoldContentFragment;
import uz.mold.R;

public class TestFragment extends MoldContentFragment {

    public static void open(Activity activity) {
        Mold.openContent(activity, TestFragment.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentView(R.layout.z_test_content);

        findViewById(R.id.tv_message).setOnClickListener(view ->
                TestSecondFragment.open(getActivity(), new TestArgMessage("Helllllooooo Woooorrrrllllddd!!!!")));
    }
}
