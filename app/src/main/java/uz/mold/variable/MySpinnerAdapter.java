package uz.mold.variable;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import uz.mold.R;
import uz.mold.collection.MyArray;

public class MySpinnerAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;
    private final MyArray<SpinnerOption> options;

    public MySpinnerAdapter(Context ctx, MyArray<SpinnerOption> options) {
        this.mInflater = LayoutInflater.from(ctx);
        this.options = options;
    }

    @Override
    public int getCount() {
        return options.size();
    }

    @Override
    public SpinnerOption getItem(int position) {
        return options.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private View createView(int position, View convertView, ViewGroup parent, int resource) {
        TextView view = (TextView) convertView;
        if (view == null) {
            view = (TextView) mInflater.inflate(resource, parent, false);
        }
        SpinnerOption item = getItem(position);
        view.setText(item.name);
        view.setTextColor(Color.parseColor("#545454"));

        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int resId = android.R.layout.simple_spinner_item;
        return createView(position, convertView, parent, resId);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createView(position, convertView, parent,
                android.R.layout.simple_spinner_dropdown_item);
    }
}
