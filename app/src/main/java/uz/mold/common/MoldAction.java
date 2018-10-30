package uz.mold.common;

import android.view.View;

public class MoldAction {

    public final int iconResId;
    public final CharSequence title;
    public final Command command;
    public final boolean submenu;
    public final View view;

    public int id = 0;

    public MoldAction(int iconResId, CharSequence title, Command command, boolean submenu, View view) {
        this.iconResId = iconResId;
        this.title = title;
        this.command = command;
        this.submenu = submenu;
        this.view = view;
    }
}
