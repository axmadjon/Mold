package uz.mold.variable;


import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import uz.mold.collection.MyArray;

public class VariableUtil {

    public static void readyToChange(Variable... items) {
        for (Variable e : items) {
            if (e != null) {
                e.readyToChange();
            }
        }
    }

    public static boolean modified(Variable... items) {
        for (Variable e : items) {
            if (e != null && e.modified()) {
                return true;
            }
        }
        return false;
    }

    public static ErrorResult getError(Variable... items) {
        for (Variable e : items) {
            if (e != null) {
                ErrorResult r = e.getError();
                if (r.isError()) {
                    return r;
                }
            }
        }
        return ErrorResult.NONE;
    }

    public static <E extends Variable> void readyToChange(MyArray<E> items) {
        for (E e : items) {
            if (e != null) {
                e.readyToChange();
            }
        }
    }

    public static <E extends Variable> boolean modified(MyArray<E> items) {
        for (E e : items) {
            if (e != null && e.modified()) {
                return true;
            }
        }
        return false;
    }

    public static <E extends Variable> ErrorResult getError(MyArray<E> items) {
        for (E e : items) {
            if (e != null) {
                ErrorResult r = e.getError();
                if (r.isError()) {
                    return r;
                }
            }
        }
        return ErrorResult.NONE;
    }

    public static void bind(EditText et, TextValue value) {
        ListenerEditText tag = null;
        if (et.getTag() instanceof ListenerEditText) {
            tag = (ListenerEditText) et.getTag();
            et.removeTextChangedListener(tag);
        }

        et.setText(value.getText());

        Model model = new Model(value);
        tag = new ListenerEditText(model);

        et.setTag(tag);
        et.addTextChangedListener(tag);
    }

    public static Model getModel(EditText et) {
        if (et.getTag() instanceof ListenerEditText) {
            ListenerEditText tag = (ListenerEditText) et.getTag();
            return tag.model;
        }
        return null;
    }

    public static void bind(CompoundButton cb, ValueBoolean value) {
        cb.setOnCheckedChangeListener(null);
        cb.setTag(null);

        cb.setChecked(value.getValue());

        Model model = new Model(value);
        cb.setTag(model);
        cb.setOnCheckedChangeListener(new ListenerCompoundButton(model));
    }

    public static Model getModel(CompoundButton cb) {
        if (cb.getTag() instanceof Model) {
            return (Model) cb.getTag();
        }
        return null;
    }

    public static void bind(Spinner sp, ValueSpinner value) {
        sp.setAdapter(new MySpinnerAdapter(sp.getContext(), value.options));

        sp.setOnItemSelectedListener(null);

        sp.setSelection(value.getPosition());

        sp.setOnItemSelectedListener(new ListenerSpinner(new Model(value)));
    }

    public static Model getModel(Spinner sp) {
        AdapterView.OnItemSelectedListener onItemSelectedListener = sp.getOnItemSelectedListener();
        if (onItemSelectedListener instanceof ListenerSpinner) {
            return ((ListenerSpinner) onItemSelectedListener).model;
        }
        return null;
    }

}
