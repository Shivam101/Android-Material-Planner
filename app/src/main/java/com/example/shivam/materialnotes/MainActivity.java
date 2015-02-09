package com.example.shivam.materialnotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import br.liveo.interfaces.NavigationLiveoListener;
import br.liveo.navigationliveo.NavigationLiveo;


public class MainActivity extends NavigationLiveo implements NavigationLiveoListener {

    public ArrayList<String> mListNameItem;
    @Override
    public void onInt(Bundle bundle) {
        this.setNavigationListener(this);
        this.setDefaultStartPositionNavigation(0);
        mListNameItem = new ArrayList<>();
        mListNameItem.add(0, "Notes");
        mListNameItem.add(1, "Reminders");
        mListNameItem.add(2, "Upcoming Events");

        ArrayList<Integer> mListIconItem = new ArrayList<>();
        mListIconItem.add(0, R.drawable.ic_create_black_24dp);
        mListIconItem.add(1, R.drawable.ic_alarm_black_24dp); //Item no icon set 0
        mListIconItem.add(2, R.drawable.ic_notifications_black_24dp); //Item no icon set 0

        ArrayList<Integer> mListHeaderItem = new ArrayList<>();
        mListHeaderItem.add(4);


        this.setFooterNavigationVisible(true);
        this.setFooterInformationDrawer("Feedback", R.drawable.ic_message_black_24dp);
        SparseIntArray mSparseCounterItem = new SparseIntArray(); //indicate all items that have a counter
        //this.setFooterNavigationVisible(false);
        this.setNavigationAdapter(mListNameItem, mListIconItem, mListHeaderItem, mSparseCounterItem);

        //this.setNavigationAdapter(mListNameItem,mListIconItem);
    }

    @Override
    public void onUserInformation() {
        this.mUserName.setText("Shivam Bhalla");
        this.mUserEmail.setText("shivam.bhalla10@gmail.com");
        this.mUserBackground.setImageResource(R.drawable.background2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClickNavigation(int position, int containerLayout) {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        Fragment mFragment;
        switch(position) {
            case 0:
                this.getToolbar().setTitle("Notes");
                mFragment = new NotesFragment().newInstance();
                if (mFragment != null){
                    mFragmentManager.beginTransaction().replace(containerLayout, mFragment).commit();
                }
                break;
            case 1:
                this.getToolbar().setTitle("Reminders");
                mFragment = new RemindersFragment().newInstance();
                if (mFragment != null){
                    mFragmentManager.beginTransaction().replace(containerLayout, mFragment).commit();
                }
                break;
            case 2:
                this.getToolbar().setTitle("Upcoming Events");
                mFragment = new RemindersFragment().newInstance();
                if (mFragment != null){
                    mFragmentManager.beginTransaction().replace(containerLayout, mFragment).commit();
                }
                break;

        }


    }

    @Override
    public void onPrepareOptionsMenuNavigation(Menu menu, int i, boolean b) {

    }

    @Override
    public void onClickFooterItemNavigation(View view) {

    }

    @Override
    public void onClickUserPhotoNavigation(View view) {

    }

    /*FloatingActionButton mNotes, mReminders;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        mNotes = (FloatingActionButton) findViewById(R.id.make_notes);
        mReminders = (FloatingActionButton) findViewById(R.id.make_reminder);
        mNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Note",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, MakeNoteActivity.class);
                startActivity(i);
            }
        });
    }


    protected void setActionBarIcon(int iconRes) {

        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
