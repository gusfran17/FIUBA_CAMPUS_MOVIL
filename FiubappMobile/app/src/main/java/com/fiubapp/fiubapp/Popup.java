package com.fiubapp.fiubapp;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class Popup extends Toast{
    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link android.app.Application}
     *                or {@link android.app.Activity} object.
     */
    public Popup(Context context) {
        super(context);
    }

    public Popup(Context context, Toast toast){
        super(context);
        this.setView(toast.getView());
        this.setDuration(toast.getDuration());
    }

    public static Popup showText(Context context, CharSequence message, int duration){
        return new Popup(context,Toast.makeText(context,message,duration));
    }

    public void show(){
        this.setGravity(Gravity.CENTER, 0, 0);
        super.show();
    }

}
