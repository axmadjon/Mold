package uz.mold.common;


import android.support.annotation.NonNull;

import uz.mold.MoldFragment;

public interface MoldFragmentLowMemoryImpl {
    void onLowMemory(@NonNull MoldFragment fragment);
}
