package uz.mold;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

public class MoldTabFragment extends MoldPageFragment {

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

    public void setTabIcons(@DrawableRes int... iconIds) {
        for (int i = 0; i < iconIds.length; i++) {
            TabLayout.Tab tabAt = mTabLayout.getTabAt(i);
            if (tabAt != null) {
                tabAt.setIcon(iconIds[i]);
                tabAt.setText("");
            }
        }
    }

    protected TabLayout getTabLayout() {
        return mTabLayout;
    }

    public void setTabTextColors(@ColorRes int resNormalId, @ColorRes int resSelectId) {
        mTabLayout.setTabTextColors(getColor(resNormalId), getColor(resSelectId));
    }
}
