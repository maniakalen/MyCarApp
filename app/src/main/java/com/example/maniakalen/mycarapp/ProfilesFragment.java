package com.example.maniakalen.mycarapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;


public class ProfilesFragment extends ListFragment {



    private static ProfilesFragment fragment;
    private OnProfilesFragmentInteractionListener mListener;

    ContentResolver cr;
    private AbsListView mListView;
    private ListAdapter mAdapter;

    public static ProfilesFragment newInstance()
    {
        if (ProfilesFragment.fragment == null) {
            ProfilesFragment.fragment = new ProfilesFragment();
        }
        return ProfilesFragment.fragment;
    }
    public ProfilesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profiles, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnProfilesFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    private void buildNewAdapter()
    {
        cr = getActivity().getContentResolver();

        Cursor cursor = cr.query(MyCarContentProvider.PROFILE_URI, null, null, null, null);
        String[] fromColumns = {
                MyDbHandler.COLUMN_DATETIME,
                MyDbHandler.COLUMN_MILEAGE,
                MyDbHandler.COLUMN_AMOUNT,
                MyDbHandler.COLUMN_QUANTITY
        };
        int[] toViews = {R.id.date_time, R.id.mileage, R.id.amount, R.id.quantity};
        mAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.my_list_view, cursor,
                fromColumns, toViews, 0);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public void notifyDataChanged() {

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
    public interface OnProfilesFragmentInteractionListener {
        public void onProfilesFragmentInteraction(Uri uri);
    }

}
