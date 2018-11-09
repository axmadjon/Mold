package uz.mold;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;

public class MoldPageTabFragment extends MoldPageFragment {

    @Override
    protected int getContentResourceId() {
        return R.layout.mold_content_page_tab;
    }

    protected TabLayout mTabLayout;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTabLayout = rFindViewById(R.id.tab_layout);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);

        int count = adapter.getCount();
        if (adapter instanceof MyPageAdapter) {
            MyPageAdapter myAdapter = (MyPageAdapter) adapter;
            if (myAdapter.listener != null) {
                for (int i = 0; i < count; i++) {
                    TabLayout.Tab tabAt = mTabLayout.getTabAt(i);
                    if (tabAt != null) {
                        Drawable icon = myAdapter.listener.getIcon(i);
                        CharSequence pageTitle = myAdapter.listener.getPageTitle(i);
                        if (icon != null) tabAt.setIcon(icon);
                        if (pageTitle != null) tabAt.setText(pageTitle);
                    }
                }
            }
        }

    }

    public void setTabTextColors(@ColorRes int resNormalId, @ColorRes int resSelectId) {
        mTabLayout.setTabTextColors(getColor(resNormalId), getColor(resSelectId));
    }
}
