package uz.mold.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import uz.mold.Mold;
import uz.mold.MoldRecyclerSwipeFragment;
import uz.mold.R;
import uz.mold.ViewSetup;

public class TestRecyclerSwipeFragment extends MoldRecyclerSwipeFragment<String> {

    public static void open(Activity activity) {
        Mold.openContent(activity, TestRecyclerSwipeFragment.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setSearchMenu(new MoldSearchListQuery() {
            @Override
            public boolean filter(String item, String text) {
                return item.contains(text);
            }
        });

        setProgressText("Loading in database");
        setEmptyView(R.drawable.ic_smartup_launcher, "Text Empty");

        onRefresh();
    }

    @Override
    public void onRefresh() {
        startRefresh();
        if (!isAdded()) return;
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (isAdded()) {

                ArrayList<String> items = new ArrayList<>();
                for (int i = 0; i < 100; i++) {
                    items.add("Text item => " + i);
                }
                setListItems(items);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);
                adapter.addAll(items);
                getAutoCompleteTextView().setAdapter(adapter);

                stopRefresh();
            }

        }, 2000);
    }

    @Override
    protected int adapterGetLayoutResource() {
        return android.R.layout.simple_list_item_1;
    }

    @Override
    protected void adapterPopulate(ViewSetup vsItem, String item) {
        vsItem.textView(android.R.id.text1).setText(item);
    }
}
