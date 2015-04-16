package com.example.shivam.materialnotes;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.nispok.snackbar.Snackbar;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MakeReminderActivity extends ActionBarActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    Toolbar toolbar;
    public static final String DATEPICKER_TAG = "datepicker";
    public static final String TIMEPICKER_TAG = "timepicker";
    TextView dateTV,timeTV;
    SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyy");
    SimpleDateFormat formatter2 = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
    Date dateObj;
    public Dialog progressDialog;
    MaterialEditText mTitle;
    Spinner mTag;
    ReminderClient reminderClient;
    Calendar c;
    int y,m,d;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reminder);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        dateTV = (TextView)findViewById(R.id.reminderDate);
        timeTV = (TextView)findViewById(R.id.reminderTime);
        mTitle = (MaterialEditText)findViewById(R.id.reminderTitle);
        reminderClient = new ReminderClient(this);
        reminderClient.doBindService();
        mTag = (Spinner)findViewById(R.id.tagSpinner);
        FloatingActionButton saveReminder = (FloatingActionButton)findViewById(R.id.saveReminder);
        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), false, false);
        dateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.setYearRange(1985, 2028);
                datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });
        timeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);
            }
        });

        saveReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitle.getText().toString();
                String tag = mTag.getSelectedItem().toString();
                if(title.isEmpty())
                {
                    Snackbar.with(getApplicationContext()).text("Your reminder isn't complete yet").show(MakeReminderActivity.this);

                }
                else
                {
                    ParseObject pobj = new ParseObject("Reminders");
                    pobj.put("Author", ParseUser.getCurrentUser());
                    pobj.put("Tag",tag);
                    pobj.put("Reminder",title);
                    pobj.put("DueDate",c.getTime());
                    MakeReminderActivity.this.progressDialog= ProgressDialog.show(MakeReminderActivity.this, "", "Setting your reminder...", true);
                    pobj.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            if(e==null)
                            {
                                reminderClient.setAlarmForNotification(c);
                                MakeReminderActivity.this.progressDialog.dismiss();
                                Toast.makeText(MakeReminderActivity.this, "Reminder set for "+formatter2.format(c.getTime()), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(MakeReminderActivity.this,MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            }
                            else
                            {
                                MakeReminderActivity.this.progressDialog.dismiss();
                                MaterialDialog.Builder builder = new MaterialDialog.Builder(MakeReminderActivity.this);
                                builder.content("Couldn't save your reminder :( Try again later.");
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
        getMenuInflater().inflate(R.menu.menu_make_reminder, menu);
        return true;
    }

    @Override
    protected void onStop() {
        if(reminderClient != null)
            reminderClient.doUnbindService();
        super.onStop();

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

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

        dateObj = new Date(year-1900, month, day);
        String mDate2 = day + "/" + month + "/" + year;
        try {
            dateObj = formatter.parse(mDate2);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String dateStr = formatter.format(dateObj);
        y = year;
        m = month;
        d = day;
        dateTV.setText(dateStr);


    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {
        String timeStr = hourOfDay + ":" + minute;
        timeTV.setText(timeStr);
        c = Calendar.getInstance();
        c.set(y, m, d);
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        //c.set(Calendar.SECOND, 0);
    }
}
