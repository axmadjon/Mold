package uz.mold.adapter;// 25.05.2016

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import uz.mold.collection.MyArray;
import uz.mold.collection.MyPredicate;


public abstract class RecyclerAdapter<E> extends RecyclerView.Adapter<RecyclerViewHolder> implements Filterable {

    protected final Context context;
    protected final LayoutInflater inflater;

    protected MyArray<E> items = MyArray.emptyArray();
    protected List<E> filteredItems = new ArrayList<>();

    private String searchText;
    private ItemFilter itemFilter;

    public MyPredicate<E> predicateSearch;
    public MyPredicate<E> predicateOthers;

    public RecyclerAdapter(Context context) {
        if (context == null) {
            throw new NullPointerException("context is null");
        }

        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getSearchText() {
        return TextUtils.isEmpty(searchText) ? "" : searchText;
    }

    public boolean isEmpty() {
        return this.filteredItems == null || this.filteredItems.isEmpty();
    }

    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

    public MyArray<E> getItems() {
        return this.items;
    }

    public E getItem(int position) {
        return filteredItems.get(position);
    }

    public MyArray<E> getFilteredItems() {
        return MyArray.from(filteredItems);
    }

    public void setItems(MyArray<E> items) {
        if (items == null) {
            throw new RuntimeException("items is null");
        }
        this.items = items;
        this.filteredItems = new ArrayList<>(filterItems().asList());
        notifyDataSetChanged();
    }

    private MyArray<E> filterItems() {
        MyPredicate<E> predicate = MyPredicate.True();
        return items.filter(predicate.and(predicateSearch).and(predicateOthers));
    }

    public void setPredicateOthers(MyPredicate<E> predicateOthers) {
        this.predicateOthers = predicateOthers;
    }

    public void filter(Filter.FilterListener listener) {
        getFilter().filter(null, listener);
    }

    public void filter() {
        getFilter().filter(null);
    }

    @Override
    public Filter getFilter() {
        if (itemFilter == null) {
            itemFilter = new ItemFilter();
        }
        return itemFilter;
    }

    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults result = new FilterResults();
            try {
                MyArray<E> filteredItems = filterItems();
                result.values = filteredItems;
                result.count = filteredItems.size();
            } catch (Exception ex) {
                result.values = ex;
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values instanceof Exception) {
                throw new RuntimeException((Throwable) results.values);
            } else {
                animateTo(new ArrayList(((MyArray<E>) results.values).asList()));
            }
        }
    }

    public void animateTo(List<E> nList) {
        for (int i = filteredItems.size() - 1; i >= 0; i--) {
            final E model = filteredItems.get(i);
            if (!nList.contains(model)) {
                removeItem(i);
            }
        }

        for (int i = 0, count = nList.size(); i < count; i++) {
            final E model = nList.get(i);
            if (!filteredItems.contains(model)) {
                addItem(i, model);
            }
        }

        for (int toPosition = nList.size() - 1; toPosition >= 0; toPosition--) {
            final E model = nList.get(toPosition);
            final int fromPosition = filteredItems.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public E removeItem(int position) {
        final E model = filteredItems.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, E model) {
        filteredItems.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        filteredItems.add(toPosition, filteredItems.remove(fromPosition));
        notifyItemMoved(fromPosition, toPosition);
    }
}
