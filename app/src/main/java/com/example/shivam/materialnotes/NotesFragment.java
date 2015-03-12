package com.example.shivam.materialnotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.FloatingActionButton;

/**
 * Created by Shivam Bhalla on 18/01/15.
 */
public class NotesFragment extends Fragment {

    FloatingActionButton mNotes, mReminders;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_notes, null);
        mNotes = (FloatingActionButton) root.findViewById(R.id.make_notes);
        mReminders = (FloatingActionButton) root.findViewById(R.id.make_reminder);
        SearchBox sb = (SearchBox)root.findViewById(R.id.searchBox);
        sb.setLogoText("Search for notes or reminders");

        mNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Note",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), MakeNoteActivity.class);
                startActivity(i);
            }
        });
        mReminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),MakeReminderActivity.class);
                startActivity(i);
            }
        });
        return root;
    }

    public NotesFragment newInstance(){
        NotesFragment mFragment = new NotesFragment();
        return mFragment;
    }


}
