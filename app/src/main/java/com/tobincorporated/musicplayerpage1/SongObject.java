package com.tobincorporated.musicplayerpage1;

import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.widget.ListView;

import static android.provider.ContactsContract.Directory.PACKAGE_NAME;

/**
 * Created by Zach Tobin on 11/21/2016.
 */

public class SongObject {
    MediaMetadataRetriever songInfo = new MediaMetadataRetriever();
    public int songID;
    public String songTitle;
    public String songArtist;

    public SongObject( int songID, String songTitle,String songArtist){
        this.songID = songID;
        this.songTitle = songTitle;
        this.songArtist = songArtist;

    }
}
