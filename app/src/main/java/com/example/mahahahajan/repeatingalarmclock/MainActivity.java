package com.example.mahahahajan.repeatingalarmclock;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FragmentListenerInterface {

    //AlarmDBHelper mydb;

    static ArrayList<AlarmObject> alarms = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("Test", "current alarm size: " + alarms.size());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();
                openTimePicker(view);
            }
        });
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

    public void onPause() {
        //TODO: DOES THIS WORK
        super.onPause();
        if(getSupportFragmentManager().getFragments().size() != 0){
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("timePicker")).commit();
        }

    }

    public void openTimePicker(View view){
        //OPEN TIME PICKER FRAGMENT
        DialogFragment timePickerFragment = new SetAlarmFragment();
        timePickerFragment.show(getSupportFragmentManager(), "timePicker");
    }
    //TODO: THIS METHOD
//    public void onResume(){
//        super.onResume();
//        Log.v("Test", "Idk if this works lmao ");
//    }
    public void onTimeSet(long alarmTime){
        makeSnackbar("Alarm set for " + convertTimeWithTimeZone(alarmTime) );
        printAlarms();
        //TODO: SET A CARD HERE

    }
    public String printAlarms(){
        Log. v("Test", "current number of alarms: " + alarms.size());
        for(int i = 0; i < alarms.size(); i++){
            //Log.v("Test", String.valueOf(alarmManager.getNextAlarmClock()));
            Log.v("Test", "current alarm is number: " + i);
        }
        return "";
    }

    public String convertTimeWithTimeZone(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("hh:mm a");
        return format.format(date);
    }
    public void makeSnackbar(String message){
        Snackbar.make(this.findViewById(R.id.toolbar), message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}
