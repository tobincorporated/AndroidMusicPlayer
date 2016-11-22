package com.tobincorporated.musicplayerpage1;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SongPicker extends AppCompatActivity {
//    public static final String songMessage = "songMessage";
    ListView songListVar ;
    SongAdapter songAdapterVar;
    MediaMetadataRetriever songInfo = new MediaMetadataRetriever();
    String songTitle;
    String songArtist;
    int songID;
    public static int[] songIDs;
    ArrayList<SongObject> songList ;
    SongObject currentSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_picker);

        songIDs = new int[10];
        songIDs[0] = R.raw.bargainsinatuxedo;
        songIDs[1] = R.raw.glaringlyablaze;
        songIDs[2] = R.raw.aqualounge;
        songIDs[3] = R.raw.arduoustask;
        songIDs[4] = R.raw.blowtohead;
        songIDs[5] = R.raw.fightingcombat;
        songIDs[6] = R.raw.letitrip;
        songIDs[7] = R.raw.losingaccusations;
        songIDs[8] = R.raw.nightmarestogo;
        songIDs[9] = R.raw.rabidcourage;


                songList = new ArrayList<SongObject>();
        for( int i=0;i<songIDs.length;i++) {
            songID = songIDs[i];
            Uri mediaPath = Uri.parse("android.resource://" + getPackageName() + "/" + songID);
            songInfo.setDataSource(this, mediaPath);

            songTitle = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            songArtist = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);


            songList.add( new SongObject(songID, songTitle, songArtist) );

        }

        SongAdapter mySongAdapter = new SongAdapter(this, songList);
        ListView listView = (ListView) findViewById(R.id.songListView);
        listView.setAdapter(mySongAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                songPlayer(String.valueOf(songIDs[position]));
            }
        });
    }


    public void playBargains(View view){
        String sendSong = String.valueOf(R.raw.bargainsinatuxedo);
        songPlayer(sendSong);
    }
    public void playGlaring(View view){
        String sendSong = String.valueOf(R.raw.glaringlyablaze);
        songPlayer(sendSong);
    }

    private void songPlayer(String message){
        Intent launchSongPlayer = new Intent(this, MainActivity.class);
        launchSongPlayer.putExtra("songMessage", message);
        startActivity(launchSongPlayer);
    }

    public void launchSong(){

    }


}
