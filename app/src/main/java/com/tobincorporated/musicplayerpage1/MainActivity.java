package com.tobincorporated.musicplayerpage1;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.util.concurrent.TimeUnit;

import static android.R.attr.start;
import static com.tobincorporated.musicplayerpage1.R.id.seekBar;

public class MainActivity extends AppCompatActivity {

    private Button forwardButtonVar, pauseButtonVar, playButtonVar, backButtonViewVar;
    private ImageView iv;
    private MediaPlayer mediaPlayer;
    private double startTimeMS = 0;
    private double finalTimeMS = 0;
    private Handler myHandler = new Handler();
    private int seekTime=0;
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekbar;
    private TextView startTimeViewVar, endTimeViewVar, songArtistViewVar, songNameViewVar;
    public static int oneTimeOnly = 0;
    private String songTitle;
    private String songArtist;
    MediaMetadataRetriever songInfo = new MediaMetadataRetriever();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        forwardButtonVar = (Button) findViewById(R.id.forwardButton);
        pauseButtonVar = (Button) findViewById(R.id.pauseButton);
        playButtonVar = (Button) findViewById(R.id.playButton);
        backButtonViewVar = (Button) findViewById(R.id.backButtonView);
        iv = (ImageView) findViewById(R.id.imageView);
        startTimeViewVar = (TextView) findViewById(R.id.startTimeView);
        endTimeViewVar = (TextView) findViewById(R.id.endTimeView);
        songArtistViewVar = (TextView) findViewById(R.id.songArtistView);
        songNameViewVar=(TextView) findViewById(R.id.subHeadingView);

        Uri mediaPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bargainsinatuxedo);
        songInfo.setDataSource(this, mediaPath);

        songTitle = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        songArtist = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);

        songNameViewVar.setText(songTitle);
        songArtistViewVar.setText(songArtist);

        mediaPlayer = MediaPlayer.create(this, R.raw.bargainsinatuxedo);
        finalTimeMS = mediaPlayer.getDuration();
        startTimeMS = mediaPlayer.getCurrentPosition();


        seekbar = (SeekBar) findViewById(seekBar);
        seekbar.setMax((int) finalTimeMS);

        seekbar.setClickable(false);
        pauseButtonVar.setEnabled(false);

        int endMinutes = (int) (finalTimeMS / 1000 / 60);
        int endSeconds = ((int) (finalTimeMS / 1000)) %60;
        endTimeViewVar.setText(endMinutes + " min, "+ endSeconds+" sec");

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekTime=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo( seekTime);
                startTimeMS = seekTime;
            }
        });

    }

    public void playSong(View view) {
        Toast.makeText(getApplicationContext(), "Playing sound", Toast.LENGTH_SHORT).show();
        mediaPlayer.start();




        int startMinutes =(int) (startTimeMS/1000/60);
        int startSeconds = ((int)(startTimeMS/1000)) %60;
        startTimeViewVar.setText(startMinutes + " min, "+ startSeconds+" sec");

        seekbar.setProgress((int) startTimeMS);
        myHandler.postDelayed(UpdateSongTime, 100);
        pauseButtonVar.setEnabled(true);
        playButtonVar.setEnabled(false);
    }


    public void pauseSong(View view) {
        Toast.makeText(getApplicationContext(), "Pausing sound", Toast.LENGTH_SHORT).show();
        mediaPlayer.pause();
        pauseButtonVar.setEnabled(false);
        playButtonVar.setEnabled(true);
    }

    public void stopSong(View view) {
        Toast.makeText(getApplicationContext(), "Pausing sound", Toast.LENGTH_SHORT).show();
        mediaPlayer.pause();
        mediaPlayer.seekTo(0);
        pauseButtonVar.setEnabled(false);
        playButtonVar.setEnabled(true);
        Toast.makeText(getApplicationContext(), "STOP!", Toast.LENGTH_SHORT).show();
    }


    public void skipForward(View view) {
        int temp = (int) startTimeMS;
        if ((temp + forwardTime) <= finalTimeMS) {
            startTimeMS = startTimeMS + forwardTime;
            mediaPlayer.seekTo((int) startTimeMS);
            Toast.makeText(getApplicationContext(), "You have Jumped forward 5 seconds", Toast.LENGTH_SHORT);
        } else {
            Toast.makeText(getApplicationContext(), "Cannot jump forward 5 seconds", Toast.LENGTH_SHORT);
        }
    }

    public void skipBack(View view) {
        int temp = (int) startTimeMS;
        if ((temp - backwardTime) > 0) {
            startTimeMS = startTimeMS - backwardTime;
            mediaPlayer.seekTo((int) startTimeMS);
            Toast.makeText(getApplicationContext(), "You have Jumped backward 5 seconds", Toast.LENGTH_SHORT);
        } else {
            Toast.makeText(getApplicationContext(), "Cannot jump backward 5 seconds", Toast.LENGTH_SHORT);
        }
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTimeMS = mediaPlayer.getCurrentPosition();

            int startMinutes =(int) (startTimeMS/1000/60);
            int startSeconds = ((int)(startTimeMS/1000)) %60;
            startTimeViewVar.setText(startMinutes + " min, "+ startSeconds+" sec");

            seekbar.setProgress((int) startTimeMS);
            myHandler.postDelayed(this, 100);
        }
    };
}