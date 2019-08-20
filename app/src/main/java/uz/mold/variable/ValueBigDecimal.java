package uz.mold.variable;

import android.text.TextUtils;

import java.math.BigDecimal;

public class ValueBigDecimal implements TextValue, Quantity {

    private int precision;
    private int scale;
    private ValueString value;
    private BigDecimal cache;

    public ValueBigDecimal(int precision, int scale) {
        this.precision = precision;
        this.scale = scale;
        this.value = new ValueString(100);
    }

    @Override
    public BigDecimal getQuantity() {
        BigDecimal val = getValue();
        if (val == null) {
            val = BigDecimal.ZERO;
        }
        return val;
    }

    public BigDecimal getValue() {
        if (cache == null) {
            String s = value.getValue();
            if (!TextUtils.isEmpty(s)) {
                try {
                    cache = new BigDecimal(s);
                } catch (Exception ignore) {
                }
            }
        }
        return cache;
    }

    public void setValue(BigDecimal value) {
        this.cache = null;
        String v = "";
        if (value != null) {
            v = value.toPlainString();
        }
        this.value.setValue(v);
    }

    @Override
    public void readyToChange() {
        value.readyToChange();
    }

    @Override
    public boolean modified() {
        return value.modified();
    }

    @Override
    public ErrorResult getError() {
        BigDecimal v = getValue();
        if (value.nonEmpty() && v == null) {
            return ErrorResult.make("Поле должно содержать только числовые данные.");
        }
        if (v != null && (v.precision() > precision || v.scale() > scale)) {
            return ErrorResult.make("Форма заполнено неверно, поле имеет следующий формат " + precision + "," + scale);
        }
        return ErrorResult.NONE;
    }

    @Override
    public String getText() {
        return value.getText();
    }

    @Override
    public void setText(String text) {
        this.cache = null;
        value.setText(text);
    }

    public boolean isEmpty() {
        return value.isEmpty();
    }

    public boolean nonEmpty() {
        return value.nonEmpty();
    }

    public boolean isZero() {
        if (isEmpty()) {
            return true;
        }
        BigDecimal v = getValue();
        return v == null || v.compareTo(BigDecimal.ZERO) == 0;
    }

    public boolean nonZero() {
        return !isZero();
    }
}
