package com.example.shivam.materialnotes;

import android.app.AlertDialog;
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
import com.parse.SignUpCallback;


public class RegisterActivity extends ActionBarActivity {

    EditText mSetUsername,mSetPassword;
    FloatingActionButton registerNext;
    Dialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mSetUsername = (EditText)findViewById(R.id.setusernameET);
        mSetPassword = (EditText)findViewById(R.id.setpasswordET);
        registerNext = (FloatingActionButton)findViewById(R.id.registration1);
        registerNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mSetUsername.getText().toString();
                String password = mSetPassword.getText().toString();
                username = username.trim();
                password = password.trim();
                if(password.isEmpty()||username.isEmpty())
                {

                    MaterialDialog.Builder builder = new MaterialDialog.Builder(RegisterActivity.this);
                    builder.content("Details cannot be blank.");
                    builder.title("Oops !");
                    builder.positiveText(android.R.string.ok);
                    builder.iconRes(R.drawable.ic_error_red_36dp);
                    builder.show();
                }
                else
                {
                    ParseUser newUser = new ParseUser();
                    newUser.setUsername(username);
                    newUser.setPassword(password);
                    RegisterActivity.this.progressDialog= ProgressDialog.show(RegisterActivity.this, "", "Setting up your account...", true);
                    newUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null)
                            {
                                //success !
                                MyApplication.updateInstallation(ParseUser.getCurrentUser());
                                RegisterActivity.this.progressDialog.dismiss();
                                Intent i = new Intent(RegisterActivity.this,RegisterActivity2.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            }
                            else
                            {
                                RegisterActivity.this.progressDialog.dismiss();
                                MaterialDialog.Builder builder = new MaterialDialog.Builder(RegisterActivity.this);
                                builder.content("Couldn't sign up :( Try again later.");
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
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
