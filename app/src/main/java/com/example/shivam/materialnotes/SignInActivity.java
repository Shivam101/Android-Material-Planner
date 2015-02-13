package com.example.shivam.materialnotes;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.shivam.materialnotes.MyApplication;

import com.gc.materialdesign.views.ButtonFlat;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class SignInActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        final EditText mUsername = (EditText)findViewById(R.id.usernameET);
        final EditText mPassword = (EditText)findViewById(R.id.passwordET);
        ButtonFlat mRegister = (ButtonFlat)findViewById(R.id.register);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignInActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
        Button mSignIn = (Button)findViewById(R.id.done);
        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                username=username.trim();
                password=password.trim();
                if(password.isEmpty()||username.isEmpty())
                {

                    MaterialDialog.Builder builder = new MaterialDialog.Builder(SignInActivity.this);
                    builder.content("Details cannot be blank.");
                    builder.title("Oops !");
                    builder.positiveText(android.R.string.ok);
                    builder.iconRes(R.drawable.ic_error_red_36dp);
                    builder.show();
                }
                else
                {
                    ParseUser.logInInBackground(username,password,new LogInCallback() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {
                            if(e==null)
                            {
                                MyApplication.updateInstallation(parseUser);
                                Intent i = new Intent(SignInActivity.this,MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            }
                            else
                            {
                                MaterialDialog.Builder builder = new MaterialDialog.Builder(SignInActivity.this);
                                builder.content("Couldn't Sign In! Try again");
                                builder.title("Ouch !");
                                builder.positiveText(android.R.string.ok);
                                builder.iconRes(R.drawable.ic_error_red_36dp);
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
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
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
