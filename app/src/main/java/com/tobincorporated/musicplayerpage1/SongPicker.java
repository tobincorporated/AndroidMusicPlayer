package com.tobincorporated.musicplayerpage1;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import static android.R.id.message;
import static android.os.Environment.getRootDirectory;

public class SongPicker extends AppCompatActivity {
//    public static final String songMessage = "songMessage";
    ListView songListVar ;
    SongAdapter songAdapterVar;
    MediaMetadataRetriever songInfo = new MediaMetadataRetriever();
    String songTitle;
    String songArtist;
    int songID;
    public static int[] songIDs;
    public static ArrayList<SongObjectF> songList ;
    SongObject currentSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_picker);

        songList = new ArrayList<SongObjectF>();

//HAVE TO ADD PERMISSION IN  Settings > Apps > "Your app name" > Permissions > storage
        String dir = "/storage/emulated/0/Music/";
        File songsPath = new File(dir);
//        File rootDir = new File();
        Log.d("tag", "Zach's debug");
        Log.d("tag","Absolute path: "+songsPath.getAbsolutePath() );
        Log.d("tag", "Folder: "+songsPath.isDirectory());
        Log.d("tag", "File: "+songsPath.isFile());
        Log.d("tag", "Exists: "+songsPath.exists());
        Log.d("tag", "Files: "+songsPath.listFiles());

        File[] songFiles = songsPath.listFiles();
        for (File songFile : songFiles){
            Uri mediaPath = Uri.fromFile( songFile );
            songInfo.setDataSource(this, mediaPath);

            songTitle = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            songArtist = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);

            songList.add( new SongObjectF(songFile, songTitle, songArtist) );
        }

//        songIDs = new int[10];
//        songIDs[0] = R.raw.bargainsinatuxedo;
//        songIDs[1] = R.raw.glaringlyablaze;
//        songIDs[2] = R.raw.aqualounge;
//        songIDs[3] = R.raw.arduoustask;
//        songIDs[4] = R.raw.blowtohead;
//        songIDs[5] = R.raw.fightingcombat;
//        songIDs[6] = R.raw.letitrip;
//        songIDs[7] = R.raw.losingaccusations;
//        songIDs[8] = R.raw.nightmarestogo;
//        songIDs[9] = R.raw.rabidcourage;

//        for( int i=0;i<songIDs.length;i++) {
//            songID = songIDs[i];
//            Uri mediaPath = Uri.parse("android.resource://" + getPackageName() + "/" + songID);
//            songInfo.setDataSource(this, mediaPath);
//
//            songTitle = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
//            songArtist = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
//
//
//            songList.add( new SongObject(songID, songTitle, songArtist) );
//
//        }

        SongAdapter mySongAdapter = new SongAdapter(this, songList);
        ListView listView = (ListView) findViewById(R.id.songListView);
        listView.setAdapter(mySongAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                songPlayer(position);
            }
        });
    }




    private void songPlayer(int songNumber){
        Intent launchSongPlayer = new Intent(this, MainActivity.class);
        launchSongPlayer.putExtra("songMessage", String.valueOf(songNumber));
        startActivity(launchSongPlayer);
    }



}
