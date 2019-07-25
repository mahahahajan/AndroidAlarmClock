package com.example.mahahahajan.repeatingalarmclock;

public class AlarmObject  {
    long  alarmTime;
    int alarmNumber; //using int to ID right now -> will this be an issue?
    boolean isRepeating;
    boolean updated;

    public AlarmObject(long time, int num){
        time = alarmTime;
        alarmNumber = num;
        isRepeating = false;
        updated = false;
    }


    public void updateAlarmTime(int alarmNum){
        //TODO: //Cancel Old Intent and create new one
        //PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), alarmNum, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


}
