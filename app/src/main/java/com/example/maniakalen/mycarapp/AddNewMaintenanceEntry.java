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
        (new DataSaveWorker(mListener, mListener.getProfileId())).execute();
    }
    protected class DataSaveWorker extends DataSaveWorkerAbstract {
        public DataSaveWorker(OnFragmentInteractionListener act, long profile_id) {
            super(act, profile_id);
        }
        @Override
        protected Uri doInBackground(Void... params) {
            Activity act = (Activity)this.act;
            EditText mileage = (EditText)act.findViewById(R.id.mileage);
            EditText price = (EditText)act.findViewById(R.id.amount);
            EditText desc = (EditText)act.findViewById(R.id.description);

            ContentValues values = new ContentValues();

            //calculateAmountQuantityValues(returnIntent);
            values.put(MyDbHandler.COLUMN_ID_PROFILE_ID, this.profile_id);
            values.put(MyDbHandler.COLUMN_MILEAGE, Integer.parseInt(mileage.getText().toString()));
            values.put(MyDbHandler.COLUMN_AMOUNT, Float.parseFloat(price.getText().toString()));
            values.put(MyDbHandler.COLUMN_QUANTITY, 1);
            values.put(MyDbHandler.COLUMN_DESCRIPTION, desc.getText().toString());
            values.put(MyDbHandler.COLUMN_TYPE_EXPENSE, expense.getExpenseId());

            ContentResolver cr = act.getContentResolver();
            return cr.insert(MyCarContentProvider.CONTENT_URI, values);
        }
    }
}
