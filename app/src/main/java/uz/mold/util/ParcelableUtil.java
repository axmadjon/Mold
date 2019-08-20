package uz.mold.util;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public final class ParcelableUtil {

    private ParcelableUtil() {
    }

    @SuppressWarnings("ConstantConditions")
    public static Parcel marshallToParcel(@NonNull Parcelable parcelable) {
        if (parcelable == null) {
            throw new RuntimeException();
        }
        Parcel parcel = Parcel.obtain();
        parcelable.writeToParcel(parcel, 0);
        return parcel;
    }

    public static byte[] marshallToByte(@NonNull Parcel parcel) {
        byte[] bytes = parcel.marshall();
        parcel.recycle();
        return bytes;
    }

    public static byte[] marshallToByte(@NonNull Parcelable parcelable) {
        Parcel source = marshallToParcel(parcelable);
        return marshallToByte(source);
    }

    public static <T extends Parcelable> T unmarshall(@NonNull byte[] bytes,
                                                      @NonNull Parcelable.Creator<T> creator) {
        Parcel parcel = unmarshall(bytes);
        return creator.createFromParcel(parcel);
    }

    public static Parcel unmarshall(byte[] bytes) {
        if (bytes == null) {
            throw new RuntimeException();
        }
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0);
        return parcel;
    }
}
