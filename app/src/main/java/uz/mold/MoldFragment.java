package uz.mold;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class MoldFragment extends Fragment {

    @LayoutRes
    protected int getContentResourceId() {
        return -1;
    }

    protected View populateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int contentResourceId = getContentResourceId();
        if (contentResourceId == -1) {
            throw new RuntimeException("contentResourceId == -1");
        }
        return inflater.inflate(contentResourceId, container, false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return populateContentView(inflater, container, savedInstanceState);
    }

    @Nullable
    public <V extends View> V findViewById(@IdRes int resId) {
        View view = getView();
        return view == null ? null : view.findViewById(resId);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MoldFragmentListener listener = Mold.cast(getActivity()).mMoldFragmentListener;
        if (listener != null) listener.onActivityCreated(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        MoldFragmentListener listener = Mold.cast(getActivity()).mMoldFragmentListener;
        if (listener != null) listener.onStart(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        MoldFragmentListener listener = Mold.cast(getActivity()).mMoldFragmentListener;
        if (listener != null) listener.onStop(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        MoldFragmentListener listener = Mold.cast(getActivity()).mMoldFragmentListener;
        if (listener != null) listener.onLowMemory(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        MoldFragmentListener listener = Mold.cast(getActivity()).mMoldFragmentListener;
        if (listener != null) listener.onDestroy(this);
    }

    public boolean onBackPressed() {
        return false;
    }
}
