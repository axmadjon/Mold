package uz.mold.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import uz.mold.Mold;
import uz.mold.MoldTabFragment;

public class TestPageFragment extends MoldTabFragment {

    public static void open(Activity activity) {
        Mold.openContent(activity, TestPageFragment.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setPageListener(new MyTestPageListener());

//        setTabIcons(R.drawable.ic_search_white_24dp,
//                R.drawable.ic_search_white_24dp,
//                R.drawable.ic_search_white_24dp,
//                R.drawable.ic_search_white_24dp);
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
                    return new TestRecyclerFragment();
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