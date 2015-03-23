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
public class AddNewFuelEntry extends AddNewFragment {


    public AddNewFuelEntry() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = R.layout.fragment_add_fuel;
        expense = MyDbHandler.ExpenseType.FUEL;
    }
    @Override
    public void onButtonAddPressed(View view) {
        (new DataSaveWorker(getActivity())).execute();
    }
    private class DataSaveWorker extends AsyncTask<Void, Void, Uri> {
        private Activity act;
        public DataSaveWorker(Activity act) {
            this.act = act;
        }
        @Override
        protected Uri doInBackground(Void... params) {
            EditText mileage = (EditText)this.act.findViewById(R.id.mileage);
            EditText price = (EditText)this.act.findViewById(R.id.price);
            EditText quantity = (EditText)this.act.findViewById(R.id.quantity);
            EditText amount = (EditText)this.act.findViewById(R.id.amount);

            Intent returnIntent = new Intent();

            String qty = quantity.getText().toString();
            String amt = amount.getText().toString();
            if (qty.equals("")) {
                qty = "0";
            }
            if (amt.equals("")) {
                amt = "0";
            }

            ContentValues values = new ContentValues();

            //calculateAmountQuantityValues(returnIntent);
            values.put(MyDbHandler.COLUMN_ID_PROFILE_ID, mListener.getProfileId());
            values.put(MyDbHandler.COLUMN_MILEAGE, Integer.parseInt(mileage.getText().toString()));
            values.put(MyDbHandler.COLUMN_PRICE, Float.parseFloat(price.getText().toString()));
            values.put(MyDbHandler.COLUMN_QUANTITY, Float.parseFloat(qty));
            values.put(MyDbHandler.COLUMN_AMOUNT, Float.parseFloat(amt));
            values.put(MyDbHandler.COLUMN_TYPE_EXPENSE, expense.getExpenseId());

            ContentResolver cr = this.act.getContentResolver();
            return cr.insert(MyCarContentProvider.CONTENT_URI, values);
            //getLoaderManager().restartLoader(0, null, this);

        }
    }
}
