package com.example.maniakalen.mycarapp;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


}
