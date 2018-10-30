package uz.mold;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

public class MoldActivity extends AppCompatActivity {

    @Nullable
    private static MoldActivity activity;

    @Nullable
    public static MoldActivity getActivity() {
        return activity;
    }

    @Nullable
    MoldFragmentListener mMoldFragmentListener;

    @Nullable
    private Object contentResult;

    @Nullable
    private Parcelable mData;

    @Nullable
    public boolean isActivityRestarted;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mold_activity);

        activity = this;

        this.isActivityRestarted = savedInstanceState != null;

        if (savedInstanceState != null) {
            mData = savedInstanceState.getParcelable(MoldUtil.ARG_DATA);
        }

        if (!isActivityRestarted) {
            replaceContent((MoldFragment) MoldUtil.createFragment(this));
        }
    }

    public FrameLayout getContentLayout() {
        return findViewById(R.id.fl_mold_content);
    }

    public CoordinatorLayout getCoordinateLayout() {
        return findViewById(R.id.mold_coordinate_layout);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean ret = false;
        try {
            View v = getCurrentFocus();
            ret = super.dispatchTouchEvent(event);

            if (v instanceof EditText) {
                View w = getCurrentFocus();
                if (w == null) {
                    return ret;
                }
                int scrcoords[] = new int[2];
                w.getLocationOnScreen(scrcoords);
                float x = event.getRawX() + w.getLeft() - scrcoords[0];
                float y = event.getRawY() + w.getTop() - scrcoords[1];

                if (event.getAction() == MotionEvent.ACTION_UP &&
                        (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom())) {

                    UI.hideSoftKeyboard(this);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mData != null) {
            outState.putParcelable(MoldUtil.ARG_DATA, mData);
        }
        super.onSaveInstanceState(outState);
    }

    public <P extends Parcelable> P getData() {
        return (P) mData;
    }

    public void setData(Parcelable data) {
        this.mData = data;
    }


    private void openContent(MoldFragment contentFragment, boolean addToBackStack) {
        UI.hideSoftKeyboard(this);

        FragmentManager manager = getSupportFragmentManager();
        if (!addToBackStack && manager.getBackStackEntryCount() > 0) {
            manager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.fl_mold_content, contentFragment);

        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }


    public void replaceContent(@NonNull MoldFragment content) {
        openContent(content, false);
    }

    public void addContent(@NonNull MoldFragment content) {
        openContent(content, true);
    }

    public void popContent(@Nullable Object result) {
        getSupportFragmentManager().popBackStack();
        this.contentResult = result;
    }

    @Nullable
    public MoldFragment getContentFragment() {
        return (MoldFragment) getSupportFragmentManager().findFragmentById(R.id.fl_mold_content);
    }

    @Override
    public void onBackPressed() {
        MoldFragment content = getContentFragment();
        if (content != null && content.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}
