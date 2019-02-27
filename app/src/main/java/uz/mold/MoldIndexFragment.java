package uz.mold;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.Objects;

import uz.mold.common.MoldFragmentStartImpl;

public class MoldIndexFragment extends MoldFragment implements MoldFragmentStartImpl {

    @Nullable
    protected DrawerLayout mDrawerLayout;
    @Nullable
    protected ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected int getContentResourceId() {
        return R.layout.mold_content_index;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MoldActivity.addOnFragmentStart(this);
    }

    @Override
    public void onStart(@NonNull MoldFragment fragment) {
        MoldFragment contentFragment = getContentFragment();
        if (contentFragment == fragment) {
            View view = findViewById(R.id.toolbar);
            if (view instanceof Toolbar) {
                initDrawer((Toolbar) view);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        MoldActivity.addOnFragmentStart(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        MoldActivity.removeOnFragmentStart(this);
    }

    //----------------------------------------------------------------------------------------------

    private void openContent(MoldFragment contentFragment, boolean addToBackStack) {
        closeIndexDrawer();

        if (!isAdded()) return;

        UI.hideSoftKeyboard(Objects.requireNonNull(getActivity()));

        FragmentManager manager = getChildFragmentManager();

        if (!addToBackStack && manager.getBackStackEntryCount() > 0) {
            manager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.fl_index_content, contentFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    @Nullable
    public MoldFragment getContentFragment() {
        return (MoldFragment) getChildFragmentManager().findFragmentById(R.id.fl_index_content);
    }

    public void replaceContent(@NonNull MoldFragment content) {
        openContent(content, false);
    }

    public void addContent(@NonNull MoldFragment content) {
        openContent(content, true);
    }

    public void popContent(@Nullable Object result) {
        if (!isAdded()) return;

        getChildFragmentManager().popBackStack();
        Mold.cast(Objects.requireNonNull(getActivity())).contentResult = result;
    }

    //----------------------------------------------------------------------------------------------

    public void openDrawer(int gravity) {
        switch (gravity) {
            case GravityCompat.START:
                closeDrawer(GravityCompat.END);
                break;
            case GravityCompat.END:
                closeDrawer(GravityCompat.START);
                break;
        }
        if (mDrawerLayout != null) mDrawerLayout.openDrawer(gravity);
    }

    public void closeDrawer(int gravity) {
        if (mDrawerLayout != null && mDrawerLayout.isDrawerVisible(gravity)) {
            mDrawerLayout.closeDrawer(gravity);
        }
    }

    public void closeDrawers() {
        if (mDrawerLayout != null) mDrawerLayout.closeDrawers();
    }

    public void toggleDrawer(int gravity) {
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(gravity)) {
            closeDrawer(gravity);
        } else {
            openDrawer(gravity);
        }
    }

    public void openIndexDrawer() {
        this.openDrawer(GravityCompat.START);
    }

    public void closeIndexDrawer() {
        this.closeDrawer(GravityCompat.START);
    }

    public void toggleIndexDrawer() {
        this.toggleDrawer(GravityCompat.START);
    }

    //----------------------------------------------------------------------------------------------

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle != null && mDrawerToggle.onOptionsItemSelected(item)) {
            closeDrawer(GravityCompat.END);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onDrawerOpened() {

    }

    public void onDrawerClosed() {

    }

    //----------------------------------------------------------------------------------------------

    private void initDrawer(Toolbar toolbar) {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        if (mDrawerLayout != null) {
            mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.open, R.string.close) {

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    if ("start".equals(drawerView.getTag())) {
                        MoldIndexFragment.this.onDrawerClosed();
                    }
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    if ("start".equals(drawerView.getTag())) {
                        MoldIndexFragment.this.onDrawerOpened();
                    }
                }
            };
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            mDrawerLayout.setDrawerListener(mDrawerToggle);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END);
        }

        toolbar.setNavigationIcon(UI.changeDrawableColor(Objects.requireNonNull(getActivity()), R.drawable.ic_menu_white_24dp,
                R.color.toolbar_icon_color_silver_dark));
        toolbar.setNavigationOnClickListener(v -> openIndexDrawer());
    }

    //----------------------------------------------------------------------------------------------


    @Override
    public boolean onBackPressed() {
        if (mDrawerLayout != null) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START) || mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                closeDrawers();
                return true;
            }
        }
        return super.onBackPressed();
    }
}
