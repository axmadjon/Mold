package uz.mold;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import uz.mold.common.Command;
import uz.mold.common.MoldAction;
import uz.mold.common.MoldSearchQuery;

public class MoldContentFragment extends MoldFragment {

    protected static final int MENU_SEARCH = 100;

    protected TextWatcher mSearchTextWatcher = null;
    protected MoldSearchQuery searchQuery;
    protected List<MoldAction> menuActions;
    int searchQueryIcon = -1;

    private int menuIdSeq;
    private int mToolbar = R.layout.mold_toolbar_default;
    private View mToolbarView = null;
    private boolean hasToolbar = true;

    public void setHasToolbar(boolean hasToolbar) {
        this.hasToolbar = hasToolbar;
    }

    public boolean isHasToolbar() {
        return hasToolbar;
    }

    public boolean hasMoldActionMenus() {
        return searchQuery != null || (menuActions != null && !menuActions.isEmpty());
    }

    public void setToolbarView(@NonNull View view) {
        this.mToolbarView = view;
    }

    public void setToolbarView(@LayoutRes int layoutId) {
        this.mToolbar = layoutId;
    }

    @Override
    protected int getContentResourceId() {
        return R.layout.mold_content;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onInitToolbar();
    }

    protected void onInitToolbar() {
        if (!isAdded() || !hasToolbar) return;

        View rootToolbar = findViewById(R.id.app_bar_layout);
        if (rootToolbar != null) rootToolbar.setVisibility(hasToolbar ? View.VISIBLE : View.GONE);

        View view = findViewById(R.id.fl_toolbar);
        if (view instanceof ViewGroup) {
            if (mToolbarView == null) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                mToolbarView = inflater.inflate(mToolbar, null);
            }
            ((ViewGroup) view).addView(mToolbarView);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);

        if (hasToolbar && toolbar != null) {
            Mold.cast(Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        }

        View mSearchCancel = findViewById(R.id.iv_cancel);
        if (mSearchCancel != null) {
            mSearchCancel.setOnClickListener(v -> circleReveal(R.id.ll_search, 1, true, false));
        }

        View mClearView = findViewById(R.id.iv_clear);
        if (mClearView != null) {
            EditText mSearch = getActivity().findViewById(R.id.auto_complete);
            mClearView.setOnClickListener(v -> {
                if (mSearch != null) mSearch.setText("");
            });
        }

        if (hasToolbar) {
            MoldActivity activity = Mold.cast(getActivity());
            ActionBar actionBar = activity.getSupportActionBar();

            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeButtonEnabled(true);

                if (toolbar != null) {
                    toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
                }
            }
        }

        View mRootSearchView = getActivity().findViewById(R.id.ll_search);
        if (mRootSearchView != null && mRootSearchView.getVisibility() == View.VISIBLE) {
            circleReveal(R.id.ll_search, 1, true, false);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (((menuActions != null && !menuActions.isEmpty()) || searchQuery != null))
            setHasOptionsMenu(true);
    }

    @Nullable
    protected AppCompatAutoCompleteTextView getAutoCompleteTextView() {
        View searchView = getActivity().findViewById(R.id.auto_complete);
        if (searchView instanceof AppCompatAutoCompleteTextView) {
            return (AppCompatAutoCompleteTextView) searchView;
        }
        return null;
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

        if (isAdded()) {
            View mRootSearchView = getActivity().findViewById(R.id.ll_search);
            if (mRootSearchView != null && mRootSearchView.getVisibility() == View.VISIBLE) {
                circleReveal(R.id.ll_search, 1, true, false);
            }
        }

        menu.clear();

        if (!hasMoldActionMenus()) return;

        if (searchQuery != null) {
            MenuItem menuItem = menu.add(Menu.NONE, MENU_SEARCH, Menu.NONE, getString(R.string.search));
            menuItem.setIcon(UI.changeDrawableColor(getActivity(), searchQueryIcon, R.color.toolbar_icon_color_silver_dark));
            menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

            EditText mSearch = getActivity().findViewById(R.id.auto_complete);

            if (mSearch != null) {
                if (mSearchTextWatcher != null) {
                    mSearch.removeTextChangedListener(mSearchTextWatcher);
                    mSearchTextWatcher = null;
                }

                mSearch.setText("");

                if (mSearchTextWatcher == null) {
                    mSearchTextWatcher = new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            if (searchQuery != null) searchQuery.onQueryText(editable.toString());
                        }
                    };
                }
                mSearch.addTextChangedListener(mSearchTextWatcher);
            }
        }

        if (menuActions != null) {
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (searchQueryIcon != -1 && id == MENU_SEARCH) {
            circleReveal(R.id.ll_search, 1, true, true);
            return true;
        }

        if (menuActions != null) {
            for (MoldAction m : menuActions) {
                if (m.id == id && m.command != null) {
                    m.command.apply();
                    return true;
                }
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void circleReveal(int viewID, int posFromRight, boolean containsOverflow, final boolean isShow) {
        final View myView = getActivity().findViewById(viewID);

        int width = myView.getWidth();

        if (posFromRight > 0)
            width -= (posFromRight * getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material)) - (getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) / 2);

        if (containsOverflow)
            width -= getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material);

        int cx = width;
        int cy = myView.getHeight() / 2;

        Animator anim;
        if (isShow)
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, (float) width);
        else
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, (float) width, 0);

        anim.setDuration((long) 200);

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (isShow) {
                    EditText mSearch = getActivity().findViewById(R.id.auto_complete);
                    if (isAdded() && mSearch != null) {
                        mSearch.requestFocus();

                        InputMethodManager keyboard = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (keyboard != null) {
                            mSearch.postDelayed(() -> keyboard.showSoftInput(mSearch, 0), 100);
                        }
                    }
                } else {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.INVISIBLE);
                }
            }
        });

        if (isShow)
            myView.setVisibility(View.VISIBLE);

        anim.start();
    }
}
