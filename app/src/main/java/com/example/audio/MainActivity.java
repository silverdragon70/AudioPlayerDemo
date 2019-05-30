package com.example.audio;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer MP;
    AudioManager AudioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MP = MediaPlayer.create(this, R.raw.nancy);
        // Setup AudioManager
        AudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        // Defining MaxStreamVolume
        int MaxVolume = AudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        // Define CurrentVolume
        int CurrentVolume = AudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        TextView CurrentVolumeText = findViewById(R.id.VolumeVlaue);
        CurrentVolumeText.setText(Integer.toString(CurrentVolume));
        TextView Progress_TotalText = findViewById(R.id.Progress_Total);
        Progress_TotalText.setText(Integer.toString(MP.getDuration()));
        TextView Progress_CurrentText = findViewById(R.id.Progress_Current);
        Progress_CurrentText.setText(Integer.toString(MP.getCurrentPosition()));

        /// SeekBar
        SeekBar VolumeControl = findViewById(R.id.VolumeSeekBar);
        VolumeControl.setMax(MaxVolume);
        VolumeControl.setProgress(CurrentVolume);

        VolumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                AudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        final SeekBar ScrubSeekBar = findViewById(R.id.ScrubSeekBar);
        ScrubSeekBar.setMax(MP.getDuration());
        ScrubSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
MP.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
ScrubSeekBar.setProgress(MP.getCurrentPosition());
            }
        } ,0,1000);
    }


    /// Play
    public void play(View view) {
        MP.start();
    }

    /// Pause
    public void pause(View view) {
        MP.pause();
    }
}