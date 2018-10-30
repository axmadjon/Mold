package uz.mold.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uz.mold.Mold;
import uz.mold.MoldContentFragment;
import uz.mold.R;
import uz.mold.UI;

public class TestSecondFragment extends MoldContentFragment {

    public static void open(Activity activity, TestArgMessage arg) {
        Bundle bundle = Mold.parcelableArgument(arg);
        Mold.openContent(activity, TestSecondFragment.class, bundle);
    }

    public TestArgMessage getTestArgMessage() {
        return Mold.parcelableArgument(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (view != null) view.setBackgroundResource(R.color.colorPrimary);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentView(R.layout.z_test_content_second);
        AppBarLayout appBarLayout = UI.setAppBarExpand(this);

        if (appBarLayout != null) {
            appBarLayout.setBackgroundColor(getColor(R.color.color_transparent));
            UI.setElevation(appBarLayout, 0f);
        }

        UI.setTitle(this, "Mold Second Frame Title");
        UI.setSubtitle(this, "This is second subtitle");

        TestArgMessage testArgMessage = getTestArgMessage();

        ((TextView) findViewById(R.id.tv_message)).setText(testArgMessage.message);
    }
}
