package com.example.maniakalen.mycarapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.maniakalen.mycarapp.dummy.DummyContent;


public class ProfilesFragment extends ListFragment implements AbsListView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {


    private static final int LIST_ID = 1;
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
        getLoaderManager().initLoader(LIST_ID, null, this);
        buildNewAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profiles, container, false);

        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
        setListAdapter(mAdapter);
        return view;
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

    @Override
    public void onPause()
    {
        super.onPause();
        mListView.setOnItemClickListener(null);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mListView.setOnItemClickListener(this);
    }
    private void buildNewAdapter()
    {
        cr = getActivity().getContentResolver();


        String[] fromColumns = {
                MyDbHandler.COLUMN_PROFILE_PHOTO,
                MyDbHandler.COLUMN_PROFILE_NAME,
                MyDbHandler.COLUMN_PROFILE_PLATE
        };
        int[] toViews = {R.id.profile_img, R.id.profile_name, R.id.plate_number};
        mAdapter = new AsyncImageSimpleCursorAdapter(getActivity(),
                R.layout.profile_item_layout, null,
                fromColumns, toViews, 0);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public void notifyDataChanged() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onProfileSelected(id);
        }
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);
        if (null != mListener) {
            mListener.onProfileSelected(id);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this.getActivity().getApplicationContext(), MyCarContentProvider.PROFILE_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ((SimpleCursorAdapter)this.getListAdapter()).swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ((SimpleCursorAdapter)this.getListAdapter()).swapCursor(null);
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
        public void onProfilesFragmentInteraction(String id);
        public void onProfileSelected(long id);
    }

}
