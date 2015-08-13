package com.example.maniakalen.mycarapp;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewInsuranceEntry extends AddNewFragment {


    public AddNewInsuranceEntry() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = R.layout.fragment_add_insurance;
        expense = MyDbHandler.ExpenseType.INSURANCE;
    }
    @Override
    public void onButtonAddPressed(View view) {
        (new DataSaveWorker(mListener, mListener.getProfileId())).prepare().execute();
    }
    protected class DataSaveWorker extends AddNewFragment.DataSaveWorkerAbstract {
        String prc;
        String cmp;
        String tp;
        String mlg;
        Integer pq;
        public DataSaveWorker(AddNewFragment.OnFragmentInteractionListener act, long profile_id) {
            super(act, profile_id);
        }
        public DataSaveWorker prepare()
        {
            Activity act = (Activity)this.act;
            EditText mileage = (EditText)act.findViewById(R.id.mileage);
            EditText price = (EditText)act.findViewById(R.id.price);
            Spinner company = (Spinner)act.findViewById(R.id.company);
            Spinner type = (Spinner)act.findViewById(R.id.ins_type);
            EditText pparts = (EditText)act.findViewById(R.id.payment_parts);

            prc = price.getText().toString();
            cmp = company.getSelectedItem().toString();
            tp = type.getSelectedItem().toString();
            mlg = mileage.getText().toString();
            pq = Integer.parseInt(pparts.getText().toString());

            return this;
        }
        @Override
        protected Uri doInBackground(Void... params) {
            Activity act = (Activity)this.act;

            ContentValues values = new ContentValues();

            values.put(MyDbHandler.COLUMN_ID_PROFILE_ID, this.profile_id);
            values.put(MyDbHandler.COLUMN_MILEAGE, Integer.parseInt(mlg));
            values.put(MyDbHandler.COLUMN_PRICE, Float.parseFloat(prc));
            values.put(MyDbHandler.COLUMN_QUANTITY, Float.parseFloat(cmp));
            values.put(MyDbHandler.COLUMN_AMOUNT, Float.parseFloat(tp));
            values.put(MyDbHandler.COLUMN_TYPE_EXPENSE, expense.getExpenseId());

            ContentResolver cr = act.getContentResolver();
            return cr.insert(MyCarContentProvider.CONTENT_URI, values);
        }
    }


}
