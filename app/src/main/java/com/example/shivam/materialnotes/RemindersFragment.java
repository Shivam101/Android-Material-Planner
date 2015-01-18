package com.example.shivam.materialnotes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Shivam on 18/01/15.
 */
public class RemindersFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_reminders, null);
        return root;
    }

    public RemindersFragment newInstance(){
        RemindersFragment mFragment = new RemindersFragment();
        return mFragment;
    }
}
