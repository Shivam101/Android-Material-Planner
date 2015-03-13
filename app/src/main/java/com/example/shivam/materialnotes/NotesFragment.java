package com.example.shivam.materialnotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Shivam Bhalla on 18/01/15.
 */
public class NotesFragment extends Fragment {

    FloatingActionButton mNotes, mReminders;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView notesList;
    List<ParseObject> notes;
    NotesAdapter mAdapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_notes, null);
        mNotes = (FloatingActionButton) root.findViewById(R.id.make_notes);
        mReminders = (FloatingActionButton) root.findViewById(R.id.make_reminder);
        SearchBox sb = (SearchBox)root.findViewById(R.id.searchBox);
        mSwipeRefreshLayout = (SwipeRefreshLayout)root.findViewById(R.id.listRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_blue,R.color.refresh_red);
        sb.setLogoText("Search for notes or reminders");
        notesList = (ListView)root.findViewById(R.id.notesList);
        refreshNotes();

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

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    refreshNotes();
            }
        });
        return root;
    }

    public void onResume() {
        super.onResume();
        getActivity().setProgressBarIndeterminateVisibility(true);
        //List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        refreshNotes();
    }

    public NotesFragment newInstance(){
        NotesFragment mFragment = new NotesFragment();
        return mFragment;
    }

    public void refreshNotes()
    {
        ParseQuery<ParseObject> noteQuery = new ParseQuery<ParseObject>("Note");
        noteQuery.whereEqualTo("Author", ParseUser.getCurrentUser());
        noteQuery.addDescendingOrder("createdAt");
        noteQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                getActivity().setProgressBarIndeterminateVisibility(false);
                if(mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                if(e==null)
                {
                    notes = parseObjects;
                    //String[] usernames = new String[notes.size()];
                    //int i = 0;
                    if(notesList.getAdapter()==null)
                    {
                        mAdapter = new NotesAdapter(getActivity(), notes);
                        notesList.setAdapter(mAdapter);
                    }
                    else
                    {
                        mAdapter.refreshAdapter(notes);
                    }

                }
            }
        });
    }


}
