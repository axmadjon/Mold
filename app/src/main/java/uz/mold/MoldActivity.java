package uz.mold;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import uz.mold.common.MoldFragmentActivityCreatedImpl;
import uz.mold.common.MoldFragmentDestroyImpl;
import uz.mold.common.MoldFragmentLowMemoryImpl;
import uz.mold.common.MoldFragmentStartImpl;
import uz.mold.common.MoldFragmentStopImpl;

public class MoldActivity extends AppCompatActivity {


    public static final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    private static final ArrayList<MoldFragmentActivityCreatedImpl> mOnFragmentActivityCreatedListener = new ArrayList<>();
    private static final ArrayList<MoldFragmentStartImpl> mOnFragmentStartListener = new ArrayList<>();
    private static final ArrayList<MoldFragmentStopImpl> mOnFragmentStopListener = new ArrayList<>();
    private static final ArrayList<MoldFragmentLowMemoryImpl> mOnFragmentLowMemoryListener = new ArrayList<>();
    private static final ArrayList<MoldFragmentDestroyImpl> mOnFragmentDestroyListener = new ArrayList<>();

    //----------------------------------------------------------------------------------------------

    public static void removeOnFragmentActivityCreated(MoldFragmentActivityCreatedImpl listener) {
        mOnFragmentActivityCreatedListener.remove(listener);
    }

    public static void removeOnFragmentStart(MoldFragmentStartImpl listener) {
        mOnFragmentStartListener.remove(listener);
    }

    public static void removeOnFragmentStop(MoldFragmentStopImpl listener) {
        mOnFragmentStopListener.remove(listener);
    }

    public static void removeOnFragmentLowMemory(MoldFragmentLowMemoryImpl listener) {
        mOnFragmentLowMemoryListener.remove(listener);
    }

    public static void removeOnFragmentDestroy(MoldFragmentDestroyImpl listener) {
        mOnFragmentDestroyListener.remove(listener);
    }

    //----------------------------------------------------------------------------------------------

    public static void addOnFragmentActivityCreated(MoldFragmentActivityCreatedImpl listener) {
        mOnFragmentActivityCreatedListener.add(listener);
    }

    public static void addOnFragmentStart(MoldFragmentStartImpl listener) {
        mOnFragmentStartListener.add(listener);
    }

    public static void addOnFragmentStop(MoldFragmentStopImpl listener) {
        mOnFragmentStopListener.add(listener);
    }

    public static void addOnFragmentLowMemory(MoldFragmentLowMemoryImpl listener) {
        mOnFragmentLowMemoryListener.add(listener);
    }

    public static void addOnFragmentDestroy(MoldFragmentDestroyImpl listener) {
        mOnFragmentDestroyListener.add(listener);
    }

    //----------------------------------------------------------------------------------------------

    public static void onActivityCreated(@NonNull MoldFragment fragment) {
        if (mOnFragmentActivityCreatedListener.size() == 1) {
            mOnFragmentActivityCreatedListener.get(0).onActivityCreated(fragment);

        } else if (mOnFragmentActivityCreatedListener.size() > 1) {
            mainThreadHandler.post(() -> {
                for (MoldFragmentActivityCreatedImpl listener : mOnFragmentActivityCreatedListener)
                    listener.onActivityCreated(fragment);
            });
        }
    }

    public static void onStart(@NonNull MoldFragment fragment) {
        if (mOnFragmentStartListener.size() == 1) {
            mOnFragmentStartListener.get(0).onStart(fragment);

        } else if (mOnFragmentStartListener.size() > 1) {
            mainThreadHandler.post(() -> {
                for (MoldFragmentStartImpl listener : mOnFragmentStartListener)
                    listener.onStart(fragment);
            });
        }
    }

    public static void onStop(@NonNull MoldFragment fragment) {
        if (mOnFragmentStopListener.size() == 1) {
            mOnFragmentStopListener.get(0).onStop(fragment);

        } else if (mOnFragmentStopListener.size() > 1) {
            mainThreadHandler.post(() -> {
                for (MoldFragmentStopImpl listener : mOnFragmentStopListener)
                    listener.onStop(fragment);
            });
        }
    }

    public static void onLowMemory(@NonNull MoldFragment fragment) {
        if (mOnFragmentLowMemoryListener.size() == 1) {
            mOnFragmentLowMemoryListener.get(0).onLowMemory(fragment);

        } else if (mOnFragmentLowMemoryListener.size() > 1) {
            mainThreadHandler.post(() -> {
                for (MoldFragmentLowMemoryImpl listener : mOnFragmentLowMemoryListener)
                    listener.onLowMemory(fragment);
            });
        }
    }

    public static void onDestroy(@NonNull MoldFragment fragment) {
        if (mOnFragmentDestroyListener.size() == 1) {
            mOnFragmentDestroyListener.get(0).onDestroy(fragment);

        } else if (mOnFragmentDestroyListener.size() > 1) {
            mainThreadHandler.post(() -> {
                for (MoldFragmentDestroyImpl listener : mOnFragmentDestroyListener)
                    listener.onDestroy(fragment);
            });
        }
    }

    //----------------------------------------------------------------------------------------------
    @Nullable
    Object contentResult;

    public boolean isActivityRestarted;

    @Nullable
    private Parcelable mData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mData = savedInstanceState.getParcelable(MoldUtil.ARG_DATA);
        }

        setContentView(R.layout.mold_activity);

        this.isActivityRestarted = savedInstanceState != null;

        if (!isActivityRestarted) {
            replaceContent((MoldFragment) MoldUtil.createFragment(this));
        }

        addOnFragmentStart(fragment -> {
            if (contentResult != null) {
                fragment.onAboveContentPopped(contentResult);
                contentResult = null;
            }
        });
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

    //----------------------------------------------------------------------------------------------

    private void openContent(MoldFragment contentFragment, boolean addToBackStack) {
        UI.hideSoftKeyboard(this);

        FragmentManager manager = getSupportFragmentManager();
        if (!addToBackStack && manager.getBackStackEntryCount() > 0) {
            manager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.fl_mold_content, contentFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

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

    //----------------------------------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        MoldFragment content = getContentFragment();
        if (content != null && content.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}
