package com.example.maniakalen.mycarapp;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by maniakalen on 24-Feb-15.
 */
public class AsyncImageSimpleCursorAdapter extends SimpleCursorAdapter {
    private ContentResolver r;
    private Resources res;
    public AsyncImageSimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        r = context.getContentResolver();
        res = context.getResources();
    }

    @Override
    public void setViewImage(ImageView v, String value) {
        new BitmapWorkerTask<ImageView>(v, r, res).execute(value);
    }
}
