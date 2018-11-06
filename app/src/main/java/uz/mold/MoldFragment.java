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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public abstract class MoldFragment extends Fragment {

    private static final String ARG_FRAGMENT_DATA = "uz.mold.mold_content_data";

    protected ViewSetup vsRoot;

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
        vsRoot = new ViewSetup(populateContentView(inflater, container, savedInstanceState));
        return vsRoot.view;
    }

    @Nullable
    public <V extends View> V findViewById(@IdRes int resId) {
        View view = getView();
        return view == null ? null : view.findViewById(resId);
    }

    @NonNull
    public <V extends View> V rFindViewById(@IdRes int resId) {
        return (V) vsRoot.id(resId);
    }

    //----------------------------------------------------------------------------------------------

    @NonNull
    public TextView textView(@IdRes int resId) {
        return vsRoot.id(resId);
    }

    @NonNull
    public ImageView imageView(@IdRes int resId) {
        return vsRoot.id(resId);
    }

    @NonNull
    public EditText editText(@IdRes int resId) {
        return vsRoot.id(resId);
    }

    @NonNull
    public Button button(@IdRes int resId) {
        return vsRoot.id(resId);
    }

    @NonNull
    public <T extends CompoundButton> T compoundButton(@IdRes int resId) {
        return vsRoot.id(resId);
    }

    @NonNull
    public Spinner spinner(@IdRes int resId) {
        return vsRoot.id(resId);
    }

    @NonNull
    public <T extends ViewGroup> T viewGroup(@IdRes int resId) {
        return vsRoot.id(resId);
    }

    //----------------------------------------------------------------------------------------------

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MoldActivity.onActivityCreated(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        MoldActivity.onStart(this);
    }

    public void onAboveContentPopped(@NonNull Object result) {
    }

    @Override
    public void onStop() {
        super.onStop();
        MoldActivity.onStop(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (data != null) outState.putParcelable(ARG_FRAGMENT_DATA, data);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        MoldActivity.onLowMemory(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MoldActivity.onDestroy(this);
    }

    public <T extends Parcelable> T manageFragmentData(@NonNull Parcelable data) {
        if (this.data == null) {
            this.data = data;
        }
        return (T) this.data;
    }

    public void setFragmentData(@NonNull Parcelable data) {
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
