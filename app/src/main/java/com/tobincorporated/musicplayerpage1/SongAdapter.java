package com.tobincorporated.musicplayerpage1;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class SongAdapter extends ArrayAdapter<SongObjectF> {

    private Context context;
    private ArrayList<SongObjectF> values;

    public SongAdapter(Context context, ArrayList<SongObjectF> values){
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View songView, ViewGroup parent) {

        SongObjectF thisSong = getItem(position);

        if (songView == null) {
            songView = LayoutInflater.from(getContext()).inflate(R.layout.song_list_item, parent, false);
        }


        TextView songNameViewVar= (TextView) songView.findViewById(R.id.songTitle);
        songNameViewVar.setText(thisSong.songTitle);
        TextView songArtistViewVar =(TextView) songView.findViewById(R.id.songArtist);
        songArtistViewVar.setText(thisSong.songArtist);

        return songView;
    }
}
