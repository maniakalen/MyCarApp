package com.example.maniakalen.mycarapp;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewMaintenanceEntry extends AddNewFragment {


    public AddNewMaintenanceEntry() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = R.layout.fragment_add_maintenance;
        expense = MyDbHandler.ExpenseType.MAINTENANCE;
    }


    @Override
    public void onButtonAddPressed(View view) {
        (new DataSaveWorker(getActivity(), mListener.getProfileId())).execute();
    }
    private class DataSaveWorker extends AsyncTask<Void, Void, Uri> {
        private Activity act;
        private long profile_id;
        public DataSaveWorker(Activity act, long profile_id) {
            this.act = act;
            this.profile_id = profile_id;
        }
        @Override
        protected Uri doInBackground(Void... params) {
            EditText mileage = (EditText)this.act.findViewById(R.id.mileage);
            EditText price = (EditText)this.act.findViewById(R.id.price);
            EditText desc = (EditText)this.act.findViewById(R.id.description);

            ContentValues values = new ContentValues();

            //calculateAmountQuantityValues(returnIntent);
            values.put(MyDbHandler.COLUMN_ID_PROFILE_ID, this.profile_id);
            values.put(MyDbHandler.COLUMN_MILEAGE, Integer.parseInt(mileage.getText().toString()));
            values.put(MyDbHandler.COLUMN_PRICE, Float.parseFloat(price.getText().toString()));
            values.put(MyDbHandler.COLUMN_QUANTITY, 1);
            values.put(MyDbHandler.COLUMN_DESCRIPTION, desc.getText().toString());
            values.put(MyDbHandler.COLUMN_TYPE_EXPENSE, expense.getExpenseId());

            ContentResolver cr = this.act.getContentResolver();
            return cr.insert(MyCarContentProvider.CONTENT_URI, values);
            //getLoaderManager().restartLoader(0, null, this);

        }
    }
}
