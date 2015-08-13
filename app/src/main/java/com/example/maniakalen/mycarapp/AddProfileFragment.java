package com.example.maniakalen.mycarapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddProfileFragment extends Fragment {
    public static final int TAKE_PHOTO_CODE = 1;
    private OnAddProfileFragmentListener mListener;
    private Uri selectedImage = null;
    private View view;
    public static AddProfileFragment newInstance(String param1, String param2) {
        AddProfileFragment fragment = new AddProfileFragment();
        return fragment;
    }

    public AddProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_profile, container, false);
        this.populateSpinner(view);
        return view;
    }

    private void populateSpinner(View view) {
        Spinner spin = (Spinner)view.findViewById(R.id.edit_profile_year_spin);
        List<String> toSpin = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1960; i <= thisYear; i++) {
            toSpin.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,toSpin);
        spin.setAdapter(adapter);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onAddProfileFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnAddProfileFragmentListener) activity;
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

    public void onPhotoSelection(View view)
    {
        Intent pickIntent = new Intent();
        pickIntent.setType("image/*");
        pickIntent.setAction(Intent.ACTION_GET_CONTENT);

        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String pickTitle = getResources().getString(R.string.pick_photo_intent_chooser); // Or get from strings.xml
        Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
        chooserIntent.putExtra
                (
                        Intent.EXTRA_INITIAL_INTENTS,
                        new Intent[] { takePhotoIntent }
                );

        getActivity().startActivityForResult(chooserIntent, TAKE_PHOTO_CODE);
    }
    public void savePhotoFile(Intent data) {
        selectedImage = data.getData();
        ImageView imgView = (ImageView)getActivity().findViewById(R.id.new_profile_pic);
        imgView.setImageURI(selectedImage);
    }


    public void addNewProfile(View v) {
        (new DataSaveWorker(mListener, this.view)).prepare().execute();
    }

    protected class DataSaveWorker extends AsyncTask<Void, Void, Uri> {
        protected View view;
        protected OnAddProfileFragmentListener act;
        String brd;
        String nm;
        String md;
        String plt;
        String yr;
        public DataSaveWorker(OnAddProfileFragmentListener act, View view) {
            super();
            this.act = act;
            this.view = view;
        }
        public DataSaveWorker prepare()
        {
            EditText brand = (EditText) view.findViewById(R.id.edit_profile_brand);
            EditText name = (EditText) view.findViewById(R.id.edit_profile_name);
            EditText model = (EditText) view.findViewById(R.id.edit_profile_model);
            EditText plate = (EditText) view.findViewById(R.id.edit_profile_plate);
            Spinner year = (Spinner) view.findViewById(R.id.edit_profile_year_spin);

            brd = brand.getText().toString();
            nm = name.getText().toString();
            md = model.getText().toString();
            plt = plate.getText().toString();
            yr = year.getSelectedItem().toString();
            return this;
        }
        @Override
        protected Uri doInBackground(Void... params) {
            Uri uri = null;
            if (this.view != null) {
                ContentValues values = new ContentValues();
                values.put(MyDbHandler.COLUMN_PROFILE_NAME, nm);
                values.put(MyDbHandler.COLUMN_PROFILE_BRAND, brd);
                values.put(MyDbHandler.COLUMN_PROFILE_MODEL, md);
                values.put(MyDbHandler.COLUMN_PROFILE_PLATE, plt);
                values.put(MyDbHandler.COLUMN_PROFILE_YEAR, yr);
                if (selectedImage != null) {
                    values.put(MyDbHandler.COLUMN_PROFILE_PHOTO, selectedImage.toString());
                }
                ContentResolver cr = getActivity().getContentResolver();
                uri = cr.insert(MyCarContentProvider.PROFILE_URI, values);
            }
            return uri;
        }
        protected void onPostExecute(Uri result) {
            this.act.notifyDataChanged();
        }
    }

    public interface OnAddProfileFragmentListener {
        public void onAddProfileFragmentInteraction(Uri uri);
        public void onSelectImageClick(View view);
        public void savePhotoFile(Intent data);
        public void addNewProfileEntry(View view);
        public void notifyDataChanged();
        public void cancelAdd(View view);
    }

}
