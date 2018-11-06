package uz.mold;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class MoldSwipeRecyclerFragment<E> extends MoldRecyclerFragment<E> implements SwipeRefreshLayout.OnRefreshListener {


    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected int getContentResourceId() {
        return R.layout.mold_content_swipe;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mSwipeRefreshLayout = vsRoot.id(R.id.swipe_container);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        if (mRecyclerView != null)
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    int topRowVerticalPosition = (mRecyclerView == null || mRecyclerView.getChildCount() == 0)
                            ? 0 : mRecyclerView.getChildAt(0).getTop();
                    LinearLayoutManager mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    assert mLayoutManager != null;
                    int firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                    mSwipeRefreshLayout.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
                }
            });
    }

    @Override
    public void onStop() {
        super.onStop();
        stopRefresh();
    }

    protected void startRefresh() {
        this.mSwipeRefreshLayout.setRefreshing(true);
    }

    protected void stopRefresh() {
        this.mSwipeRefreshLayout.setRefreshing(false);
    }
}
