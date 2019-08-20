package uz.mold.variable;

import android.text.TextUtils;

import uz.mold.collection.MyArray;

public class ValueSpinner implements TextValue {

    public final MyArray<SpinnerOption> options;
    private SpinnerOption oldValue;
    private SpinnerOption value;

    public ValueSpinner(MyArray<SpinnerOption> options, SpinnerOption value) {
        if (value == null) {
            value = options.get(0);
        }
        checkValue(options, value);
        this.options = options;
        this.oldValue = value;
        this.value = value;
    }

    public ValueSpinner(MyArray<SpinnerOption> options) {
        this(options, null);
    }

    private static void checkValue(MyArray<SpinnerOption> options, SpinnerOption value) {
        if (value == null) {
            throw new RuntimeException();
        }
        options.checkUniqueness(SpinnerOption.KEY_ADAPTER);
        for (SpinnerOption o : options) {
            if (o == value) {
                return;
            }
        }
        throw new RuntimeException("Given option value is not found in spinner options");
    }

    public int getPosition() {
        for (int i = 0; i < options.size(); i++) {
            if (value == options.get(i)) {
                return i;
            }
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void readyToChange() {
        oldValue = value;
    }

    @Override
    public boolean modified() {
        return oldValue != value;
    }

    @Override
    public ErrorResult getError() {
        return ErrorResult.NONE;
    }

    public SpinnerOption getValue() {
        return value;
    }

    public void setValue(SpinnerOption value) {
        checkValue(options, value);
        this.value = value;
    }

    @Override
    public String getText() {
        return value.code;
    }

    @Override
    public void setText(String text) {
        SpinnerOption o = options.find(text, SpinnerOption.KEY_ADAPTER);
        setValue(o);
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(value.code);
    }

    public boolean nonEmpty() {
        return !isEmpty();
    }

}