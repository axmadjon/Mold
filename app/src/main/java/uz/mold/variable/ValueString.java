package uz.mold.variable;

import android.text.TextUtils;

import uz.mold.util.Util;

public class ValueString implements TextValue {

    private final int size;
    private String oldValue = "";
    private String value = "";

    public ValueString(int size) {
        this.size = size;
    }

    public ValueString(int size, String value) {
        this.size = size;
        this.value = Util.nvl(value);
    }

    @Override
    public void readyToChange() {
        oldValue = value;
    }

    @Override
    public boolean modified() {
        return !value.equals(oldValue);
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(value);
    }

    public boolean nonEmpty() {
        return !isEmpty();
    }

    @Override
    public ErrorResult getError() {
        if (value.length() > size) {
            return ErrorResult.make("Text length is greater than " + size);
        }
        return ErrorResult.NONE;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = Util.nvl(value);
    }

    @Override
    public String getText() {
        return getValue();
    }

    @Override
    public void setText(String text) {
        setValue(text);
    }

}
