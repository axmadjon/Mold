package uz.mold.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import uz.mold.Mold;
import uz.mold.MoldContentFragment;
import uz.mold.R;
import uz.mold.UI;

public class TestSecondFragment extends MoldContentFragment {

    public static void open(Activity activity, TestArgMessage arg) {
        Bundle bundle = Mold.parcelableArgument(arg);
        Mold.openContent(activity, TestSecondFragment.class, bundle);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentView(R.layout.z_test_content_second);

        setSearchMenu(s -> Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show());

        UI.setTitle(this, "Mold Second Frame Title");
        UI.setSubtitle(this, "This is second subtitle");
    }
}
