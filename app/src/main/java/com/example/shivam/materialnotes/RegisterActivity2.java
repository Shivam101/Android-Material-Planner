package com.example.shivam.materialnotes;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class RegisterActivity2 extends ActionBarActivity {

    Dialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity2);
        final EditText mName = (EditText)findViewById(R.id.setnameET);
        final EditText mEmail = (EditText)findViewById(R.id.setemailET);
        FloatingActionButton mRegDone = (FloatingActionButton)findViewById(R.id.registration2);
        mRegDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString();
                String email = mEmail.getText().toString();
                email = email.trim();
                ParseUser currentUser = ParseUser.getCurrentUser();
                if(name.isEmpty()||email.isEmpty())
                {
                    MaterialDialog.Builder builder = new MaterialDialog.Builder(RegisterActivity2.this);
                    builder.content("Details cannot be blank.");
                    builder.title("Oops !");
                    builder.positiveText(android.R.string.ok);
                    builder.show();
                }
                else
                {
                    currentUser.setEmail(email);
                    currentUser.put("Name", name);
                    RegisterActivity2.this.progressDialog= ProgressDialog.show(RegisterActivity2.this, "", "Finalizing your account...", true);
                    currentUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null)
                            {
                                RegisterActivity2.this.progressDialog.dismiss();
                                Intent i = new Intent(RegisterActivity2.this,MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            }
                            else
                            {
                                RegisterActivity2.this.progressDialog.dismiss();
                                MaterialDialog.Builder builder = new MaterialDialog.Builder(RegisterActivity2.this);
                                builder.title("Oops!");
                                builder.content("Could not create your account. Try again later.");
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
        getMenuInflater().inflate(R.menu.menu_register_activity2, menu);
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
}
