package com.example.maniakalen.mycarapp;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.View;
import android.widget.EditText;


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
        (new DataSaveWorker(mListener, mListener.getProfileId())).prepare().execute();
    }
    protected class DataSaveWorker extends DataSaveWorkerAbstract {
        String qty;
        String amt;
        String mlag;
        String prc;
        public DataSaveWorker(OnFragmentInteractionListener act, long profile_id) {
            super(act, profile_id);
        }
        public DataSaveWorker prepare()
        {
            Activity act = (Activity)this.act;
            EditText mileage = (EditText)act.findViewById(R.id.mileage);
            EditText price = (EditText)act.findViewById(R.id.price);
            EditText quantity = (EditText)act.findViewById(R.id.quantity);
            EditText amount = (EditText)act.findViewById(R.id.amount);
            qty = quantity.getText().toString();
            amt = amount.getText().toString();
            mlag = mileage.getText().toString();
            prc = price.getText().toString();
            return this;
        }
        @Override
        protected Uri doInBackground(Void... params) {



            if (qty.equals("")) {
                qty = "0";
            }
            if (amt.equals("")) {
                amt = "0";
            }
            Activity act = (Activity)this.act;
            ContentValues values = new ContentValues();

            values.put(MyDbHandler.COLUMN_ID_PROFILE_ID, this.profile_id);
            values.put(MyDbHandler.COLUMN_MILEAGE, Integer.parseInt(mlag));
            values.put(MyDbHandler.COLUMN_PRICE, Float.parseFloat(prc));
            values.put(MyDbHandler.COLUMN_QUANTITY, Float.parseFloat(qty));
            values.put(MyDbHandler.COLUMN_AMOUNT, Float.parseFloat(amt));
            values.put(MyDbHandler.COLUMN_TYPE_EXPENSE, expense.getExpenseId());

            ContentResolver cr = act.getContentResolver();
            return cr.insert(MyCarContentProvider.CONTENT_URI, values);
        }
    }
}
