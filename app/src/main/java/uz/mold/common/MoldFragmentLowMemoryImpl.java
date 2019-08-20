package uz.mold.common;


import androidx.annotation.NonNull;

import uz.mold.MoldFragment;

public interface MoldFragmentLowMemoryImpl {
    void onLowMemory(@NonNull MoldFragment fragment);
}
