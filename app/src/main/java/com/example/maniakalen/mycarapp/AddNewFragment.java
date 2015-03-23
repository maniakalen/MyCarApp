package com.example.maniakalen.mycarapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddNewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddNewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewFragment extends Fragment {
    protected static final String ARG_TYPE = "expense";

    protected OnFragmentInteractionListener mListener;
    protected MyDbHandler.ExpenseType expense;
    protected int layout;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddNewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNewFragment newInstance(MyDbHandler.ExpenseType type) {
        AddNewFragment fragment = new AddNewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    public AddNewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            expense = (MyDbHandler.ExpenseType)getArguments().getSerializable(ARG_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(layout, container, false);
    }

    public void onButtonAddPressed(View view) {


    }

    private void calculateAmountQuantityValues(Intent data) {
        Float price = data.getFloatExtra(MyDbHandler.COLUMN_PRICE, 0);
        Float quantity = data.getFloatExtra(MyDbHandler.COLUMN_QUANTITY, 0);
        Float amount = data.getFloatExtra(MyDbHandler.COLUMN_AMOUNT, 0);

        if (quantity == 0 && amount > 0) {
            quantity = amount / price;
            data.putExtra(MyDbHandler.COLUMN_QUANTITY, quantity);
        } else if (quantity > 0 && amount == 0) {
            amount = quantity * price;
            data.putExtra(MyDbHandler.COLUMN_AMOUNT, amount);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onAddNewItem(View view);
        public void onRejectNewItem(View view);
        public void notifyDataChanged();
        public long getProfileId();
    }

}
