package com.example.shivam.materialnotes;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;
import com.rengwuxian.materialedittext.MaterialEditText;


public class MakeNoteActivity extends ActionBarActivity {

    Toolbar toolbar;
    EditText mNote;
    MaterialEditText mTitle;
    Dialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_note);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setTitle("");
        mTitle = (MaterialEditText)findViewById(R.id.noteTitle);
        mNote = (EditText)findViewById(R.id.noteText);
        Slidr.attach(this);
        FloatingActionButton saveNote = (FloatingActionButton)findViewById(R.id.saveNote);
        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String title = mTitle.getText().toString();
                    String note = mNote.getText().toString();
                    if(title.isEmpty()||note.isEmpty())
                    {
                        Snackbar.with(getApplicationContext()).text("Your note isn't complete yet").show(MakeNoteActivity.this);
                        //Toast.makeText(MakeNoteActivity.this,"Your note isn't complete yet",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        ParseObject pobj = new ParseObject("Note");
                        pobj.put("Title",title);
                        pobj.put("Note",note);
                        pobj.put("Author", ParseUser.getCurrentUser());
                        MakeNoteActivity.this.progressDialog= ProgressDialog.show(MakeNoteActivity.this, "", "Saving your Note...", true);
                        pobj.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e==null)
                                {
                                    MakeNoteActivity.this.progressDialog.dismiss();
                                    Toast.makeText(MakeNoteActivity.this,"Note Saved Successfully !",Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(MakeNoteActivity.this,MainActivity.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                }
                                else
                                {
                                    MakeNoteActivity.this.progressDialog.dismiss();
                                    MaterialDialog.Builder builder = new MaterialDialog.Builder(MakeNoteActivity.this);
                                    builder.content("Couldn't save your note :( Try again later.");
                                    builder.title("Oops !");
                                    builder.positiveText(android.R.string.ok);
                                    builder.show();
                                }
                            }
                        });
                    }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_make_note, menu);
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
        else if(id == android.R.id.home)
        {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
