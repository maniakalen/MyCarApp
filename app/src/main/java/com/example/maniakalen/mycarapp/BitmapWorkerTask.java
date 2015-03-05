package com.example.maniakalen.mycarapp;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

/**
 * Created by maniakalen on 23-Feb-15.
 */
class BitmapWorkerTask<T> extends AsyncTask<String, Void, Drawable> {
    private final WeakReference<T> itemReference;
    private final ContentResolver r;
    private final Resources res;

    public BitmapWorkerTask(T item, ContentResolver resolver, Resources resources) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        itemReference = new WeakReference<T>(item);
        r = resolver;
        res = resources;
    }

    // Decode image in background.
    @Override
    protected Drawable doInBackground(String... params) {
        return loadImageFromUri(params[0]);
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Drawable bitmap) {
        if (itemReference != null && bitmap != null) {
            final T imageView = itemReference.get();
            if (MenuItem.class.isInstance(imageView)) {
                ((MenuItem) imageView).setIcon(bitmap);
            } else if (ImageView.class.isInstance(imageView)) {
                ((ImageView) imageView).setImageDrawable(bitmap);
            }
        }
    }
    private Drawable loadImageFromUri(String uri) {
        Drawable dwb;
        try {
            if (uri != null && uri.length() == 0) {
                throw new FileNotFoundException("No file uri given");
            }
            InputStream stream = r.openInputStream(Uri.parse(uri));
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(stream, null, opts);
            int width = R.dimen.mini_icon_width;
            int height = R.dimen.mini_icon_height;
            final T imgView = itemReference.get();
            if (ImageView.class.isInstance(imgView)) {
                width = ((ImageView) imgView).getLayoutParams().width;
                height = ((ImageView) imgView).getLayoutParams().height;
            }
            opts.inSampleSize = calculateInSampleSize(opts, width, height);
            opts.inJustDecodeBounds = false;

            stream = r.openInputStream(Uri.parse(uri));
            Bitmap bmp = BitmapFactory.decodeStream(stream, null, opts);
            dwb = new BitmapDrawable(res, bmp);
        } catch (FileNotFoundException | NullPointerException ex) {
            dwb = res.getDrawable(android.R.drawable.ic_menu_gallery);
        }
        return dwb;
    }
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
}
