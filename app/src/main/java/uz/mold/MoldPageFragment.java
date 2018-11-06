package uz.mold;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

@SuppressWarnings("unused")
public abstract class MoldPageFragment<E> extends MoldContentHeaderFooterFragment {

    protected ViewPager mViewPager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentView(R.layout.mold_view_page);

        mViewPager = rFindViewById(R.id.view_page);

        setHasOptionsMenu(true);
    }

    //----------------------------------------------------------------------------------------------

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        int currentItem = mViewPager.getCurrentItem();

        PagerAdapter adapter = mViewPager.getAdapter();
        if (adapter instanceof FragmentStatePagerAdapter) {
            Fragment fragment = ((FragmentStatePagerAdapter) adapter).getItem(currentItem);
            if (isAdded() && fragment instanceof MoldContentFragment && ((MoldContentFragment) fragment).hasMoldActionMenus()) {
                fragment.onCreateOptionsMenu(menu, inflater);
            }

        } else if (adapter instanceof FragmentPagerAdapter) {
            Fragment fragment = ((FragmentPagerAdapter) adapter).getItem(currentItem);
            if (isAdded() && fragment instanceof MoldContentFragment && ((MoldContentFragment) fragment).hasMoldActionMenus()) {
                fragment.onCreateOptionsMenu(menu, inflater);
            }
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int currentItem = mViewPager.getCurrentItem();

        PagerAdapter adapter = mViewPager.getAdapter();
        if (adapter instanceof FragmentStatePagerAdapter) {
            Fragment fragment = ((FragmentStatePagerAdapter) adapter).getItem(currentItem);
            if (isAdded() && fragment.hasOptionsMenu()) {
                return fragment.onOptionsItemSelected(item);
            }

        } else if (adapter instanceof FragmentPagerAdapter) {
            Fragment fragment = ((FragmentPagerAdapter) adapter).getItem(currentItem);
            if (isAdded() && fragment.hasOptionsMenu()) {
                return fragment.onOptionsItemSelected(item);
            }
        }
        return super.onOptionsItemSelected(item);
    }


    //----------------------------------------------------------------------------------------------

    public void setPageListener(PageListener pageListener) {
        PageAdapter adapter = new PageAdapter(getChildFragmentManager(), pageListener);
        pageListener.adapter = adapter;
        setAdapter(adapter);
    }

    public void setAdapter(PagerAdapter adapter) {
        mViewPager.setAdapter(adapter);
    }

    //----------------------------------------------------------------------------------------------

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mViewPager.addOnPageChangeListener(onPageChangeListener);
    }

    public void addOnAdapterChangeListener(ViewPager.OnAdapterChangeListener onAdapterChangeListener) {
        mViewPager.addOnAdapterChangeListener(onAdapterChangeListener);
    }

    //----------------------------------------------------------------------------------------------

    class PageAdapter extends FragmentStatePagerAdapter {

        private final PageListener listener;

        public PageAdapter(FragmentManager fm, PageListener listener) {
            super(fm);
            this.listener = listener;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return listener.getPageTitle(position);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = listener.getItem(position);
            if (fragment instanceof MoldContentFragment) {
                ((MoldContentFragment) fragment).setHasToolbar(false);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return listener.getCount();
        }
    }

    public static abstract class PageListener {

        public abstract Fragment getItem(int position);

        public abstract int getCount();

        protected PagerAdapter adapter;

        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}
