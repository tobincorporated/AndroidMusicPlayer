package com.tobincorporated.musicplayerpage1;

import java.io.File;

/**
 * Created by Zach Tobin on 11/22/2016.
 */

public class SongObjectF {


    public File songFile;
    public String songTitle;
    public String songArtist;

    public SongObjectF( File songFile, String songTitle,String songArtist){
        this.songFile = songFile;
        this.songTitle = songTitle;
        this.songArtist = songArtist;

    }
}
