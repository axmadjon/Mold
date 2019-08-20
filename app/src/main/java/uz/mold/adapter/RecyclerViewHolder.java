package uz.mold.adapter;

import android.view.View;

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
}
