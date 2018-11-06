package uz.mold;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import uz.mold.adapter.RecyclerAdapter;
import uz.mold.adapter.RecyclerViewHolder;
import uz.mold.collection.MyArray;
import uz.mold.collection.MyPredicate;
import uz.mold.common.MoldSearchQuery;
import uz.mold.common.Setter;

@SuppressWarnings("unused")
public abstract class MoldRecyclerFragment<E> extends MoldContentFragment {

    @Nullable
    protected RecyclerView mRecyclerView;
    @Nullable
    protected ContentRecyclerAdapter adapter;

    private boolean enableLongClick;

    @Override
    protected int getContentResourceId() {
        return R.layout.mold_content_recycler;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentView(R.layout.mold_recycler);


        adapter = null;

        mRecyclerView = rFindViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (isAdded() && RecyclerView.SCROLL_STATE_DRAGGING == newState) {
                    UI.hideSoftKeyboard(Objects.requireNonNull(getActivity()));
                }
            }
        });
    }

    //----------------------------------------------------------------------------------------------

    public void setHeader(@NonNull View view) {
        if (!isAdded()) return;
        FrameLayout fl = viewGroup(R.id.header);
        fl.removeAllViews();
        fl.addView(view);
        fl.setVisibility(View.VISIBLE);
        fl.requestLayout();
    }

    public void setHeader(@NonNull ViewSetup view) {
        if (!isAdded()) return;
        FrameLayout fl = viewGroup(R.id.header);
        fl.removeAllViews();
        fl.addView(view.view);
        fl.setVisibility(View.VISIBLE);
        fl.requestLayout();
    }

    @Nullable
    public ViewSetup setHeader(@LayoutRes int layoutId) {
        if (!isAdded()) return null;
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(layoutId, null);
        setHeader(view);
        return new ViewSetup(view);
    }

    @Nullable
    public View getHeader() {
        if (!isAdded()) return null;
        FrameLayout fl = viewGroup(R.id.header);
        if (fl.getChildCount() > 0) {
            return fl.getChildAt(0);
        }
        return null;
    }

    //----------------------------------------------------------------------------------------------

    public void setFooter(@NonNull View view) {
        if (!isAdded()) return;
        FrameLayout fl = viewGroup(R.id.footer);
        fl.removeAllViews();
        fl.addView(view);
        fl.setVisibility(View.VISIBLE);
        fl.requestLayout();
    }

    public void setFooter(@NonNull ViewSetup view) {
        if (!isAdded()) return;
        FrameLayout fl = viewGroup(R.id.footer);
        fl.removeAllViews();
        fl.addView(view.view);
        fl.setVisibility(View.VISIBLE);
        fl.requestLayout();
    }

    @Nullable
    public ViewSetup setFooter(@LayoutRes int layoutId) {
        if (!isAdded()) return null;
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(layoutId, null);
        setFooter(view);
        return new ViewSetup(view);
    }

    @Nullable
    public View getFooter() {
        if (!isAdded()) return null;
        FrameLayout fl = viewGroup(R.id.footer);
        if (fl.getChildCount() > 0) {
            return fl.getChildAt(0);
        }
        return null;
    }

    //----------------------------------------------------------------------------------------------

    protected void setProgressView(@NonNull View view) {
        if (!isAdded()) return;

        ViewGroup vg = viewGroup(R.id.fl_progress);
        vg.removeAllViewsInLayout();
        vg.addView(view);
    }


    protected void setProgressView(@LayoutRes int layoutId) {
        if (!isAdded()) return;

        ViewGroup vg = viewGroup(R.id.fl_progress);
        vg.removeAllViewsInLayout();

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        vg.addView(inflater.inflate(layoutId, null));
    }

    protected void setProgressText(@NonNull CharSequence text) {
        if (!isAdded()) return;

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View progressView = inflater.inflate(R.layout.mold_recycler_progress, null);

        TextView progressText = progressView.findViewById(R.id.tv_progress);
        progressText.setText(text);

        setProgressView(progressView);
    }

    protected void setProgressText(@StringRes int textId) {
        if (!isAdded()) return;

        FragmentActivity activity = getActivity();
        assert activity != null;

        setProgressText(activity.getString(textId));
    }

    //----------------------------------------------------------------------------------------------

    protected void setEmptyView(@NonNull View view) {
        if (!isAdded()) return;

        ViewGroup vg = viewGroup(R.id.empty_layout);
        vg.removeAllViewsInLayout();

        vg.addView(view);
    }

    protected void setEmptyView(@LayoutRes int layoutId) {
        if (!isAdded()) return;

        ViewGroup vg = viewGroup(R.id.empty_layout);
        vg.removeAllViewsInLayout();

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        vg.addView(inflater.inflate(layoutId, null));
    }

    protected void setEmptyView(@NonNull Drawable drawable, @NonNull CharSequence text) {
        if (!isAdded()) return;

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View emptyView = inflater.inflate(R.layout.mold_recycler_empty, null);

        ImageView emptyIcon = emptyView.findViewById(R.id.iv_empty_icon);
        emptyIcon.setImageDrawable(drawable);

        TextView emptyText = emptyView.findViewById(R.id.tv_empty_text);
        emptyText.setText(text);

        setEmptyView(emptyView);
    }

    protected void setEmptyView(@NonNull Drawable drawable, @StringRes int textId) {
        if (!isAdded()) return;

        setEmptyView(drawable, getActivity().getString(textId));
    }

    protected void setEmptyView(@DrawableRes int imageId, @NonNull CharSequence text) {
        if (!isAdded()) return;

        setEmptyView(getActivity().getDrawable(imageId), text);
    }

    protected void setEmptyView(@DrawableRes int imageId, @StringRes int textId) {
        if (!isAdded()) return;
        FragmentActivity activity = getActivity();
        assert activity != null;

        setEmptyView(Objects.requireNonNull(activity.getDrawable(imageId)),
                getActivity().getString(textId));
    }

    //----------------------------------------------------------------------------------------------

    public void setHasLongClick(boolean enableLongClick) {
        this.enableLongClick = enableLongClick;
    }

    public void setListFilter(@Nullable MyPredicate<E> predicate) {
        if (isAdded() && adapter != null) {
            adapter.predicateOthers = predicate;
            adapter.filter();
        }
    }

    @NonNull
    public MyArray<E> getListFilteredItems() {
        if (isAdded() && adapter != null) {
            return adapter.getFilteredItems();
        }
        return MyArray.emptyArray();
    }

    @NonNull
    public MyArray<E> getListItems() {
        if (isAdded() && adapter != null) {
            return adapter.getItems();
        }
        return MyArray.emptyArray();
    }

    @SafeVarargs
    public final void setListItems(@NonNull E... elements) {
        setListItems(MyArray.from(elements));
    }

    public void setListItems(@NonNull List<E> elements) {
        setListItems(MyArray.from(elements));
    }

    public void setListItems(@NonNull Set<E> elements) {
        setListItems(MyArray.from(elements));
    }

    public void setListItems(@NonNull MyArray<E> items) {
        if (!isAdded()) return;

        if (adapter == null) {
            adapter = new ContentRecyclerAdapter(getActivity());
            adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                private void populate() {
                    if (!isAdded()) return;
                    if (adapter.getItemCount() == 0) {
                        if (mRecyclerView != null) mRecyclerView.setVisibility(View.GONE);
                        rFindViewById(R.id.empty_layout).setVisibility(View.VISIBLE);
                    } else {
                        if (mRecyclerView != null) mRecyclerView.setVisibility(View.VISIBLE);
                        rFindViewById(R.id.empty_layout).setVisibility(View.GONE);
                    }
                    onListItemChanged();
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    populate();
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    populate();
                }

                @Override
                public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                    populate();
                }

                @Override
                public void onChanged() {
                    super.onChanged();
                    populate();
                }
            });

            if (mRecyclerView != null) mRecyclerView.setAdapter(adapter);

            rFindViewById(R.id.fl_list_content).setVisibility(View.VISIBLE);
            rFindViewById(R.id.fl_progress).setVisibility(View.GONE);
        }
        adapter.setItems(items);
    }

    //----------------------------------------------------------------------------------------------

    protected void onItemClick(RecyclerViewHolder holder, E item) {

    }

    protected boolean onItemLongClick(RecyclerViewHolder holder, E item) {
        return false;
    }

    protected void onListItemChanged() {

    }

    protected abstract int adapterGetLayoutResource();

    protected abstract void adapterPopulate(ViewSetup vsItem, E item);

    //----------------------------------------------------------------------------------------------

    private class ContentRecyclerAdapter extends RecyclerAdapter<E> {

        ContentRecyclerAdapter(Context context) {
            super(context);
        }


        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecyclerViewHolder(new ViewSetup(inflater, adapterGetLayoutResource()));
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, int position) {
            final E item = getItem(position);

            holder.itemView.setOnClickListener(view -> onItemClick(holder, item));

            if (enableLongClick) {
                holder.itemView.setOnLongClickListener(view -> onItemLongClick(holder, item));
            }

            adapterPopulate(holder.vsItem, item);
        }
    }

    public abstract class MoldSearchListQuery implements MoldSearchQuery, Runnable {

        public int delayMillis = 1000;

        private final Handler handler = new Handler(Looper.getMainLooper());
        private long lastCallHandle = 0;
        private Setter<String> searchText = new Setter<>();

        @Override
        public final void onQueryText(final String s) {
            handler.removeCallbacks(MoldSearchListQuery.this);

            searchText.value = s;

            handler.postDelayed(MoldSearchListQuery.this, delayMillis);
        }

        public abstract boolean filter(E item, String text);

        @Override
        public void run() {
            if (isAdded() && adapter != null) {
                adapter.predicateSearch = new MyPredicate<E>() {
                    @Override
                    public boolean apply(E e) {
                        return filter(e, searchText.value);
                    }
                };
                adapter.setSearchText(searchText.value);
                adapter.filter();
            }
        }
    }
}
