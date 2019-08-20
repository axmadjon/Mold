package uz.mold.test;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import uz.mold.Mold;
import uz.mold.MoldPageTabFragment;

public class TestPageFragment extends MoldPageTabFragment {

    public static void open(Activity activity) {
        Mold.openContent(activity, TestPageFragment.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setPageListener(new MyTestPageListener());
    }

    public class MyTestPageListener extends PageListener {

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Page 0";
                case 1:
                    return "Page 1";
                case 2:
                    return "Page 2";
                case 3:
                    return "Recycler";
                default:
                    throw new UnsupportedOperationException();
            }
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new TestPage1Fragment();
                case 1:
                    return new TestPage2Fragment();
                case 2:
                    return new TestPage3Fragment();
                case 3:
                    return new TestRecyclerSwipeFragment();
                default:
                    throw new UnsupportedOperationException();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
