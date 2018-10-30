package uz.mold;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import uz.mold.common.Command;
import uz.mold.common.MoldAction;
import uz.mold.common.MoldSearchQuery;

public class MoldContentFragment extends MoldFragment {

    protected MoldSearchQuery searchQuery;
    protected List<MoldAction> menuActions;
    int searchQueryIcon;
    private int menuIdSeq;

    @Override
    protected int getContentResourceId() {
        return R.layout.mold_content;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        Mold.cast(getActivity()).setSupportActionBar(toolbar);
    }

    protected void setContentView(@LayoutRes int resId) {
        setContentView(LayoutInflater.from(getActivity()).inflate(resId, null));
    }

    protected void setContentView(@NonNull View view) {
        ViewGroup content = findViewById(R.id.fl_mold_fragment_content);
        content.removeAllViews();
        content.addView(view);
    }

    public int getColor(@ColorRes int resId) {
        return getResources().getColor(resId);
    }

    //----------------------------------------------------------------------------------------------

    public void addMenu(int iconId, int resTitleId, Command command) {
        addMenu(iconId, getString(resTitleId), command);
    }

    public void addMenu(int iconId, CharSequence title, Command command) {
        getMenus().add(new MoldAction(iconId, title, command, false, null));
    }

    public void addMenu(CharSequence title, View view) {
        getMenus().add(new MoldAction(0, title, null, false, view));
    }

    public void addSubMenu(CharSequence title, Command command) {
        getMenus().add(new MoldAction(0, title, command, true, null));
    }

    List<MoldAction> getMenus() {
        if (menuActions == null) {
            menuActions = new ArrayList<>();
            setHasOptionsMenu(true);
        }
        return menuActions;
    }

    public void setSearchMenu(int iconResId, MoldSearchQuery searchQuery) {
        this.searchQueryIcon = iconResId;
        this.searchQuery = searchQuery;
        if (iconResId <= 0 || searchQuery == null) {
            throw new RuntimeException("iconRes <= 0 or searchQuery is null");
        }
    }

    public void setSearchMenu(MoldSearchQuery searchQuery) {
        setSearchMenu(R.drawable.ic_search_white_24dp, searchQuery);
    }

    //----------------------------------------------------------------------------------------------


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();

        for (MoldAction m : menuActions) {
            if (m.id == 0) {
                m.id = menuIdSeq++;
            }
            if (m.submenu) {
                menu.addSubMenu(Menu.NONE, m.id, Menu.NONE, m.title);
                continue;
            }

            MenuItem menuItem = menu.add(Menu.NONE, m.id, Menu.NONE, m.title);
            if (m.view != null) {
                menuItem.setActionView(m.view);
            } else {
                menuItem.setIcon(UI.changeDrawableColor(getActivity(), m.iconResId, R.color.toolbar_icon_color_silver_dark));
            }
            menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        for (MoldAction m : menuActions) {
            if (m.id == id && m.command != null) {
                m.command.apply();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
