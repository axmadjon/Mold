package uz.mold.variable;

import android.widget.CompoundButton;

public class ListenerCompoundButton implements CompoundButton.OnCheckedChangeListener {

    public final Model model;

    public ListenerCompoundButton(Model model) {
        this.model = model;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        ValueBoolean vb = (ValueBoolean) model.value;
        boolean old = vb.getValue();
        vb.setValue(isChecked);
        if (old != vb.getValue()) {
            model.notifyListeners();
        }
    }
}
