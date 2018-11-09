package uz.mold;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class MoldPageNavigationFragment extends MoldPageFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbarView(R.layout.mold_toolbar_default);
    }

    @Override
    protected int getContentResourceId() {
        return R.layout.mold_content_page_navigation;
    }

    protected BottomNavigationView mBottomNavigationView;
    private int showingItem = -1;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBottomNavigationView = rFindViewById(R.id.bottom_navigation);

        addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                showingItem = position;
                mBottomNavigationView.setSelectedItemId(position);
            }
        });

        mBottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            int itemId = menuItem.getItemId();
            mViewPager.setCurrentItem(itemId, true);
            return true;
        });
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);

        int count = adapter.getCount();

        Menu menu = mBottomNavigationView.getMenu();
        menu.clear();

        for (int i = 0; i < count; i++) {
            MenuItem mItem = menu.add(Menu.NONE, i, Menu.NONE, adapter.getPageTitle(i));
            if (adapter instanceof MyPageAdapter) {
                PageListener listener = ((MyPageAdapter) adapter).listener;
                Drawable icon = listener.getIcon(i);
                if (icon != null) mItem.setIcon(icon);
            }


            if (showingItem != -1 && i == showingItem) {
                mItem.setChecked(true);
            } else {
                mItem.setCheckable(false);
            }
        }
    }
}
