package com.example.maniakalen.mycarapp;


import android.content.ContentUris;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FuelItemFragment extends ItemFragment {
    protected static final MyDbHandler.ExpenseType type = MyDbHandler.ExpenseType.FUEL;
    @Override
    protected Uri getLoaderUri() {
        return ContentUris.withAppendedId(MyCarContentProvider.CONTENT_PER_TYPE_URI, type.getExpenseId());
    }
}
