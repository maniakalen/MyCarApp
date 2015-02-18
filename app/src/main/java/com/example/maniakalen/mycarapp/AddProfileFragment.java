package com.example.maniakalen.mycarapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;

public class AddProfileFragment extends Fragment {
    public static final int TAKE_PHOTO_CODE = 1;
    private OnAddProfileFragmentListener mListener;
    private Uri selectedImage = null;
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
        return inflater.inflate(R.layout.fragment_add_profile, container, false);
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


    public void addNewProfile(View view) {

        EditText brand = (EditText)getActivity().findViewById(R.id.brand);
    }
    public interface OnAddProfileFragmentListener {
        public void onAddProfileFragmentInteraction(Uri uri);
        public void onSelectImageClick(View view);
        public void savePhotoFile(Intent data);
        public void addNewProfileEntry(View view);
    }

}
