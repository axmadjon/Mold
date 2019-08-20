package uz.mold.variable;


import uz.mold.collection.MyArray;
import uz.mold.collection.MyPredicate;

public class ValueArray<E extends Variable> implements Variable {

    private MyArray<E> items;
    private boolean changed = false;

    public ValueArray() {
        this.items = MyArray.emptyArray();
    }

    public ValueArray(MyArray<E> items) {
        this.items = items;
        if (this.items == null) {
            throw new NullPointerException();
        }
    }

    public MyArray<E> getItems() {
        return items;
    }

    public void prepend(E item) {
        this.items = items.prepend(item);
        this.changed = true;
    }

    public void append(E item) {
        this.items = items.append(item);
        this.changed = true;
    }

    public void delete(final E item) {
        this.items = items.filter(new MyPredicate<E>() {
            @Override
            public boolean apply(E e) {
                return e != item;
            }
        });
        this.changed = true;
    }

    @Override
    public void readyToChange() {
        changed = false;
        VariableUtil.readyToChange(items);
    }

    @Override
    public boolean modified() {
        if (changed) {
            return true;
        }
        return VariableUtil.modified(items);
    }

    @Override
    public ErrorResult getError() {
        return VariableUtil.getError(items);
    }



}
