package uz.mold;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

class MoldUtil {

    static final String ARG_DATA = "uz.mold.data";
    static final String ARG_CLASS = "uz.mold.arg_class";
    static final String ARG_BUNDLE = "uz.mold.arg_bundle";
    static final String ARG_FARGMENT_BINDLE = "uz.mold.arg_fragment_bundle";

    static Fragment createFragment(@NonNull MoldActivity activity) {
        Bundle extras = activity.getIntent().getExtras();

        if (extras == null) throw new RuntimeException("Intent extras is null");

        Bundle args = extras.getBundle(ARG_BUNDLE);
        Class<?> cls = (Class<?>) extras.getSerializable(ARG_CLASS);

        if (cls == null) throw new RuntimeException("Form class is null");

        Fragment fragment = (Fragment) classNewInstance(cls);
        if (args != null) {
            fragment.setArguments(args);
        }
        return fragment;
    }

    @NonNull
    static <C> C classNewInstance(@NonNull Class<C> cls) {
        try {
            return cls.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
