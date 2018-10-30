package uz.mold.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import uz.mold.Mold;
import uz.mold.MoldContentFragment;
import uz.mold.R;

public class TestSecondFragment extends MoldContentFragment {

    public static void open(Activity activity, TestArgMessage arg) {
        Bundle bundle = Mold.parcelableArgument(arg);
        Mold.openContent(activity, TestSecondFragment.class, bundle);
    }

    public TestArgMessage getTestArgMessage() {
        return Mold.parcelableArgument(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentView(R.layout.z_test_content_second);

        TestArgMessage testArgMessage = getTestArgMessage();

        ((TextView) findViewById(R.id.tv_message)).setText(testArgMessage.message);
    }
}
