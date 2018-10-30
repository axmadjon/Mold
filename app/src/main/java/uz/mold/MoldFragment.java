package uz.mold;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class MoldFragment extends Fragment {

    private static final String ARG_FRAGMENT_DATA = "uz.mold.mold_content_data";

    private Parcelable data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            data = savedInstanceState.getParcelable(ARG_FRAGMENT_DATA);
        }
    }

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

    public void onAboveContentPopped(Object result) {
    }

    @Override
    public void onStop() {
        super.onStop();

        MoldFragmentListener listener = Mold.cast(getActivity()).mMoldFragmentListener;
        if (listener != null) listener.onStop(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (data != null) outState.putParcelable(ARG_FRAGMENT_DATA, data);
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

    public <T extends Parcelable> T manageFragmentData(Parcelable data) {
        if (this.data == null) {
            this.data = data;
        }
        return (T) this.data;
    }

    public void setFragmentData(Parcelable data) {
        this.data = data;
    }

    public <T extends Parcelable> T getFragmentData() {
        return (T) data;
    }

    public void reloadContent() {
    }

    public boolean onBackPressed() {
        return false;
    }

}
