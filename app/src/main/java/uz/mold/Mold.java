package uz.mold;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@SuppressWarnings("WeakerAccess")
public class Mold {

    //----------------------------------------------------------------------------------------------

    public static Intent newContent(@NonNull Activity activity, @NonNull Class<? extends MoldFragment> cls, @Nullable Bundle args) {
        Intent i = new Intent(activity, MoldActivity.class);
        i.putExtra(MoldUtil.ARG_CLASS, cls);
        if (args != null) {
            i.putExtra(MoldUtil.ARG_BUNDLE, args);
        }
        return i;
    }

    @NonNull
    public static Intent newContent(@NonNull Activity activity, @NonNull Class<? extends MoldFragment> cls) {
        return newContent(activity, cls, null);
    }

    public static void openContent(@NonNull Activity activity, @NonNull Class<? extends MoldFragment> cls, @Nullable Bundle args) {
        activity.startActivity(newContent(activity, cls, args));
    }

    public static void openContent(@NonNull Activity activity, @NonNull Class<? extends MoldFragment> cls) {
        activity.startActivity(newContent(activity, cls, null));
    }

    //----------------------------------------------------------------------------------------------

    @Nullable
    public static <T extends MoldFragment> T getContentFragment(@NonNull MoldActivity activity) {
        return (T) activity.getContentFragment();
    }

    @Nullable
    public static <T extends MoldFragment> T getContentFragment(@NonNull Activity activity) {
        return (T) ((MoldActivity) activity).getContentFragment();
    }

    //----------------------------------------------------------------------------------------------

    @Nullable
    public static <T extends Parcelable> T getData(@NonNull Activity activity) {
        return (T) Mold.cast(activity).getData();
    }

    public static void setData(@NonNull Activity activity, @NonNull Parcelable data) {
        Mold.cast(activity).setData(data);
    }

    //----------------------------------------------------------------------------------------------

    @NonNull
    public static MoldActivity cast(@NonNull Activity activity) {
        return (MoldActivity) activity;
    }

    //----------------------------------------------------------------------------------------------

    @NonNull
    public static Bundle parcelableArgument(@NonNull Parcelable argument) {
        Bundle args = new Bundle();
        args.putParcelable(MoldUtil.ARG_FARGMENT_BINDLE, argument);
        return args;
    }

    @Nullable
    public static <T extends Parcelable> T parcelableArgument(@NonNull MoldFragment fragment) {
        Bundle arguments = fragment.getArguments();
        return arguments == null ? null : arguments.getParcelable(MoldUtil.ARG_FARGMENT_BINDLE);
    }

    @NonNull
    public static <T extends MoldFragment> T parcelableArgumentNewInstance(@NonNull Class<T> cls, @NonNull Parcelable argument) {
        T f = MoldUtil.classNewInstance(cls);
        Bundle args = parcelableArgument(argument);
        f.setArguments(args);
        return f;
    }

    @NonNull
    public static <T extends MoldFragment> T parcelableArgumentNewInstance(@NonNull Class<T> cls, @NonNull Bundle args) {
        T f = MoldUtil.classNewInstance(cls);
        f.setArguments(args);
        return f;
    }

    //----------------------------------------------------------------------------------------------
}
