package com.example.maniakalen.mycarapp;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by maniakalen on 22-Feb-15.
 */
public class AsyncImageView extends ImageView {
    public AsyncImageView(Context context) {
        super(context);
    }

    public void setImageURI(Uri uri) {
        int i = 0;
        Log.i("Image", "Image url set");
        super.setImageURI(uri);
    }


}
