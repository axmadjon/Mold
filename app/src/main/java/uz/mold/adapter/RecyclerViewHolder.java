package uz.mold.adapter;

import android.content.Context;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import uz.mold.ViewSetup;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public final ViewSetup vsItem;

    public RecyclerViewHolder(View view) {
        super(view);
        this.vsItem = new ViewSetup(view);
    }

    public RecyclerViewHolder(ViewSetup view) {
        super(view.view);
        this.vsItem = view;
    }

    public RecyclerViewHolder(Context ctx, @LayoutRes int layout) {
        this(View.inflate(ctx, layout, null));
    }
}
