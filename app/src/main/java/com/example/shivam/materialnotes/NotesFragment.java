package com.example.shivam.materialnotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.dexafree.materialList.cards.BasicImageButtonsCard;
import com.dexafree.materialList.cards.BigImageButtonsCard;
import com.dexafree.materialList.model.Card;
import com.dexafree.materialList.view.MaterialListView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shivam Bhalla on 18/01/15.
 */
public class NotesFragment extends Fragment {

    FloatingActionButton mNotes, mReminders;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView notesList;
    SearchBox sb;
    List<ParseObject> notes;
    private int mBaseTranslationY;
    NotesAdapter mAdapter = null;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_notes, null);
        mNotes = (FloatingActionButton) root.findViewById(R.id.make_notes);
        mReminders = (FloatingActionButton) root.findViewById(R.id.make_reminder);
        sb = (SearchBox)root.findViewById(R.id.searchBox);

        final ActionBar ab = ((ActionBarActivity)getActivity()).getSupportActionBar();
        //ab.setCustomView((View)ab);

        //MaterialListView mListView = (MaterialListView) root.findViewById(R.id.material_listview);
        //BasicImageButtonsCard card = new BasicImageButtonsCard(getActivity());
        //card.setTitle("Your title");
        //card.setDescription("Your description");
        //mListView.add(card);
        mSwipeRefreshLayout = (SwipeRefreshLayout)root.findViewById(R.id.listRefresh);
        //setHasOptionsMenu(true);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_blue,R.color.refresh_red,R.color.refresh_green,R.color.refresh_yellow);
        sb.setLogoText("Search for notes or reminders");

        notesList = (ListView)root.findViewById(R.id.notesList);
        /*notesList.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
                int toolbarHeight = ab.getHeight();
                if (dragging || scrollY < toolbarHeight) {
                    if (firstScroll) {
                        float currentHeaderTranslationY = ViewHelper.getTranslationY(ab.getCustomView());
                        if (-toolbarHeight < currentHeaderTranslationY && toolbarHeight < scrollY) {
                            mBaseTranslationY = scrollY;
                        }
                    }
                    float headerTranslationY = ScrollUtils.getFloat(-(scrollY - mBaseTranslationY), -toolbarHeight, 0);
                    ViewPropertyAnimator.animate(ab.getCustomView()).cancel();
                    ViewHelper.setTranslationY(ab.getCustomView(), headerTranslationY);
                }

            }

            @Override
            public void onDownMotionEvent() {

            }

            @Override
            public void onUpOrCancelMotionEvent(ScrollState scrollState) {
                mBaseTranslationY = 0;

                float headerTranslationY = ViewHelper.getTranslationY(ab.getCustomView());
                int toolbarHeight = ab.getHeight();
                if (scrollState == ScrollState.UP) {
                    if (toolbarHeight < notesList.getCurrentScrollY()) {
                        if (headerTranslationY != -toolbarHeight) {
                            ViewPropertyAnimator.animate(ab.getCustomView()).cancel();
                            ViewPropertyAnimator.animate(ab.getCustomView()).translationY(-toolbarHeight).setDuration(200).start();
                        }
                    }
                } else if (scrollState == ScrollState.DOWN) {
                    if (toolbarHeight < notesList.getCurrentScrollY()) {
                        if (headerTranslationY != 0) {
                            ViewPropertyAnimator.animate(ab.getCustomView()).cancel();
                            ViewPropertyAnimator.animate(ab.getCustomView()).translationY(0).setDuration(200).start();
                        }
                    }
                }


            }
        });*/
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
        SwipeDismissListViewTouchListener swipeDismiss = new SwipeDismissListViewTouchListener(notesList,new SwipeDismissListViewTouchListener.DismissCallbacks() {
            @Override
            public boolean canDismiss(int position) {
                return true;
            }

            @Override
            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                for(final int position : reverseSortedPositions)
                {
                    mAdapter.remove(mAdapter.getItem(position));
                    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                            "Note");
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> parseObjects, ParseException e) {
                            if(e==null)
                            {
                                notes = parseObjects;
                                ArrayList<String> list_items = new ArrayList<String>();
                                for(ParseObject obj : notes)
                                {
                                    obj = notes.get(position);
                                    obj.deleteInBackground();
                                }
                            }
                        }
                    });
                    Toast.makeText(getActivity(),"Done with this task", Toast.LENGTH_SHORT).show();
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        notesList.setOnTouchListener(swipeDismiss);
        notesList.setOnScrollListener(swipeDismiss.makeScrollListener());
        return root;
    }

    public void onResume() {
        super.onResume();
       // getActivity().setProgressBarIndeterminateVisibility(true);
        //List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        refreshNotes();
    }

    public NotesFragment newInstance(){
        NotesFragment mFragment = new NotesFragment();
        return mFragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main,menu);
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_search)
        {
            //sb.revealFromMenuItem(R.id.action_search, this);
        }
    }*/

    public void refreshNotes()
    {
        ParseQuery<ParseObject> noteQuery = new ParseQuery<ParseObject>("Note");
        noteQuery.whereEqualTo("Author", ParseUser.getCurrentUser());
        noteQuery.addDescendingOrder("createdAt");
        noteQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
//                getActivity().setProgressBarIndeterminateVisibility(false);
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
                        mAdapter = new NotesAdapter(getActivity(),R.layout.notes_list_item, notes);
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
