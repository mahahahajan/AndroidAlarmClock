package com.example.mahahahajan.repeatingalarmclock;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

public class ActivatedAlarm extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    TextView info;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activated_alarm);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Retrieve default ringtone file URI
        Uri myUri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_ALARM);

        // Set up MediaPlayer asynchronously
        mediaPlayer = new MediaPlayer();
        class Listener implements MediaPlayer.OnPreparedListener {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        }
        mediaPlayer.setOnPreparedListener(new Listener());
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(getApplicationContext(), myUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
        mediaPlayer.start();
    }


    protected void finished(View view) {


        // Release MediaPlayer
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        finish();
    }
}
