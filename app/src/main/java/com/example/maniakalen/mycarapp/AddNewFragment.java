package com.example.maniakalen.mycarapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddNewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNewFragment newInstance(String param1, String param2) {
        AddNewFragment fragment = new AddNewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new, container, false);
    }

    public void onButtonAddPressed(View view) {
        if (mListener != null) {
            Activity act = (Activity)mListener;
            EditText mileage = (EditText)act.findViewById(R.id.mileage);
            EditText price = (EditText)act.findViewById(R.id.price);
            EditText quantity = (EditText)act.findViewById(R.id.quantity);
            EditText amount = (EditText)act.findViewById(R.id.amount);

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

            values.put(MyDbHandler.COLUMN_MILEAGE, Integer.parseInt(mileage.getText().toString()));
            values.put(MyDbHandler.COLUMN_PRICE, Float.parseFloat(price.getText().toString()));
            values.put(MyDbHandler.COLUMN_QUANTITY, Float.parseFloat(qty));
            values.put(MyDbHandler.COLUMN_AMOUNT, Float.parseFloat(amt));
            ContentResolver cr = getActivity().getContentResolver();
            cr.insert(MyCarContentProvider.CONTENT_URI, values);
            mListener.notifyDataChanged();
            //getLoaderManager().restartLoader(0, null, this);
        }
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
    }

}
