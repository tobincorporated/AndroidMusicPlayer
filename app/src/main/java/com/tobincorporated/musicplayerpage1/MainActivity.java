package com.tobincorporated.musicplayerpage1;

import android.content.Intent;
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
import static android.R.id.message;
import static com.tobincorporated.musicplayerpage1.R.id.seekBar;
import static com.tobincorporated.musicplayerpage1.SongPicker.songIDs;

public class MainActivity extends AppCompatActivity {

    private Button forwardButtonVar, pauseButtonVar, playButtonVar, backButtonViewVar, stopButtonVar;
    private ImageView iv;
    private static MediaPlayer mediaPlayer;
    private double startTimeMS = 0;
    private double finalTimeMS = 0;
    private Handler myHandler = new Handler();
    private int seekTime=0;
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekbar;
    private TextView startTimeViewVar, endTimeViewVar, songArtistViewVar, songNameViewVar;
    private String songTitle;
    private String songArtist;
    MediaMetadataRetriever songInfo = new MediaMetadataRetriever();
    Intent thisIntent;
    String songID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        forwardButtonVar = (Button) findViewById(R.id.forwardButton);
        pauseButtonVar = (Button) findViewById(R.id.pauseButton);
        playButtonVar = (Button) findViewById(R.id.playButton);
        stopButtonVar = (Button) findViewById(R.id.stopButton);
        backButtonViewVar = (Button) findViewById(R.id.backButtonView);
        iv = (ImageView) findViewById(R.id.imageView);
        startTimeViewVar = (TextView) findViewById(R.id.startTimeView);
        endTimeViewVar = (TextView) findViewById(R.id.endTimeView);
        songArtistViewVar = (TextView) findViewById(R.id.songArtistView);
        songNameViewVar=(TextView) findViewById(R.id.subHeadingView);

        thisIntent = getIntent();
        songID = thisIntent.getStringExtra("songMessage");
        Toast.makeText(getApplicationContext(), songID, Toast.LENGTH_SHORT).show();

        Uri mediaPath = Uri.parse("android.resource://" + getPackageName() + "/" + songID);
        songInfo.setDataSource(this, mediaPath);

        songTitle = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        songArtist = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);

        songNameViewVar.setText(songTitle);
        songArtistViewVar.setText(songArtist);
        if( mediaPlayer!=null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        mediaPlayer = MediaPlayer.create(this, Integer.parseInt(songID));
        seekbar = (SeekBar) findViewById(seekBar);
        seekbar.setClickable(false);
        pauseButtonVar.setEnabled(false);

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
                mediaPlayer.seekTo((int) seekTime);
                startTimeMS = seekTime;
            }
        });

        playSongNow();

    }

    private void playNewSong(String songID){
        Uri mediaPath = Uri.parse("android.resource://" + getPackageName() + "/" + songID);
        songInfo.setDataSource(this, mediaPath);

        songTitle = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        songArtist = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);

        songNameViewVar.setText(songTitle);
        songArtistViewVar.setText(songArtist);
        mediaPlayer.stop();
        mediaPlayer = MediaPlayer.create(this, Integer.parseInt(songID));
        mediaPlayer.seekTo(0);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                playNextSong();

            }
        });

        playSongNow();

    }

    public void playSong(View view){
        playSongNow();
    }

    public void playSongNow() {
        Toast.makeText(getApplicationContext(), "Playing sound", Toast.LENGTH_SHORT).show();
        mediaPlayer.start();
        finalTimeMS = mediaPlayer.getDuration();
        startTimeMS = mediaPlayer.getCurrentPosition();
        seekbar.setMax((int) finalTimeMS);


        int endMinutes = (int) (finalTimeMS / 1000 / 60);
        int endSeconds = ((int) (finalTimeMS / 1000)) %60;
        endTimeViewVar.setText(endMinutes + " min, "+ endSeconds+" sec");

        int startMinutes =(int) (startTimeMS/1000/60);
        int startSeconds = ((int)(startTimeMS/1000)) %60;
        startTimeViewVar.setText(startMinutes + " min, "+ startSeconds+" sec");

        seekbar.setProgress((int) startTimeMS);
        myHandler.postDelayed(UpdateSongTime, 100);
        stopButtonVar.setEnabled(true);
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
        stopButtonVar.setEnabled(false);
        playButtonVar.setEnabled(true);
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

    public void backSongClick(View view){
    mediaPlayer.seekTo(0);
    }

    public void nextSongClick(View view){
        playNextSong();
    }

    private void playNextSong(){

        int[] songIDs = SongPicker.songIDs;
        int songIndex = 0;

        for(int i =0; i<songIDs.length; i++){
            if(Integer.parseInt(songID)==songIDs[i]){
                songIndex = i;
            }
        }
        songIndex++;
        if(songIndex > songIDs.length-1) {
            songIndex = 0;
        }

        songID = String.valueOf(songIDs[songIndex]);
        String message = songID;
        playNewSong(songID);
//
//        Intent launchSongPlayer = new Intent(this, MainActivity.class);
//        launchSongPlayer.putExtra("songMessage", message);
//        startActivity(launchSongPlayer);
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