package com.example.mahahahajan.repeatingalarmclock;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;

import java.util.Calendar;


public class SetAlarmFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    //static ArrayList<Fragment> alarmsFrag =
    Calendar c, c2, targetCal;
    int hour, minute, currHour, currMinute;
    long alarmTime;
    boolean alarmSet;
    String alarmTimeString = null;
    static int alarmNum = 0;


    private FragmentListenerInterface listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        alarmSet = false;
        c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentListenerInterface) {
            listener = (FragmentListenerInterface) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement OnItemSelectedListener");
        }
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        Log.v("Test", "Set Time is: " + hourOfDay + ":" + minute);


        c2 = Calendar.getInstance();
        currHour = c2.get(Calendar.HOUR_OF_DAY);
        currMinute = c2.get(Calendar.MINUTE);

        targetCal = Calendar.getInstance();


        if(currHour < hourOfDay){
            Log.v("Test", "SET ALARM FOR TODAY");
            Log.v("Test", targetCal.toString());

        }
        else if(currHour > hourOfDay){
            Log.v("Test", "SET ALARM FOR TOMORROW");
            targetCal.add(Calendar.DATE , 1);

        }
        else {
            //TODO: CHECK FOR MINUTES STILL
            Log.v("Test", "Check for minutes");
        }
        targetCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        targetCal.set(Calendar.MINUTE, minute);
        targetCal.set(Calendar.SECOND, 0);

        Log.v("Test", "This is the set time: " + String.valueOf(targetCal.getTime())); //Need to do a get so that the values changed in set take place

        createAlarm(targetCal);



    }

    public SetAlarmFragment() {
        // Required empty public constructor


    }
    public void setAlarm(long alarmTime){
        // Create intent to set off the alarm

        Intent alarmIntent = new Intent(getContext(), ActivatedAlarm.class);
        alarmIntent.putExtra("ALARM_TIME", "test");
        Log.v("Test", "ALARM INTENT CHECK");
        // Retrieve AlarmManager
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
//

//        // Set alarm for intended date-time to open AlarmActivity
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime,
        PendingIntent.getActivity(getContext(), alarmNum, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT));

    }



    public void clearAlarms(){
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(getContext(), ActivatedAlarm.class);
        Log.v("Test", "TRYING TO CLEAR ALARMS");
        while(alarmNum != 0){
            PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), alarmNum, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager.cancel(pendingIntent);
            alarmNum--;
        }
        Log.v("Test", "Alarms should be cleard");
    }
    public void createAlarm(Calendar targetCal){
        alarmSet = true;
        alarmTime = targetCal.getTimeInMillis();

        alarmTimeString = String.valueOf(alarmTime);
        Log.v("Test", "Alarm time as a long is: " + alarmTimeString);

        AlarmObject newAlarm = new AlarmObject(alarmTime, alarmNum);
        alarmNum++;
        MainActivity.alarms.add(newAlarm);

        Log.v("Test", "New Alarm number is " + alarmNum );

        listener.onTimeSet(alarmTime);

        //TODO: UNCOMMENT THIS SO ACTUAL ALARMS ARE SET
       //setAlarm(alarmTime);
        //clearAlarms();
    }

}
