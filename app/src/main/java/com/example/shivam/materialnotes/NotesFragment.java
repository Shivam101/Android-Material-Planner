package com.example.shivam.materialnotes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Shivam Bhalla on 18/01/15.
 */
public class NotesFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_notes, null);
        return root;
    }

    public NotesFragment newInstance(){
        NotesFragment mFragment = new NotesFragment();
        return mFragment;
    }


}
