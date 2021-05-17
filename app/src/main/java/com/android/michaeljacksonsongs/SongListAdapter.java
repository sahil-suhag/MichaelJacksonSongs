package com.android.michaeljacksonsongs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongViewHolder> {

    Context context;
    SongList<Song> songList;

    public SongListAdapter(Context context, SongList<Song> songList){
        this.context = context;
        this.songList = songList;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_view, viewGroup,false);
        SongViewHolder mViewHolder = new SongViewHolder(view);

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(SongViewHolder viewHolder, int position){
        Song songItem = songList.getResults().get(position);

        viewHolder.mTrackName.setText(songItem.trackName);
        viewHolder.mArtistName.setText(songItem.artistName);
        viewHolder.mCollection.setText(songItem.collectionName);

        String diskNo = "Disk: "+songItem.discNumber + " / " + songItem.discCount;
        viewHolder.mDiskNumber.setText(diskNo);
        String trackNo = "Track: "+ songItem.trackNumber + " / "+songItem.trackCount;
        viewHolder.mTrackNumber.setText(trackNo);

        String nationalSongPrice = songItem.currency+" "+songItem.trackPrice+"/-";
        viewHolder.mSongPrice.setText(nationalSongPrice);

        int timeInMilliSec = songItem.trackTimeMillis;
        int timeInMin = timeInMilliSec/60000;
        int secRemamining = (timeInMilliSec - (timeInMin*60000))/1000;
        String trackTime = timeInMin +" min "+secRemamining+" sec";
        viewHolder.mSongLength.setText(trackTime);

        viewHolder.mSongCountry.setText(songItem.country);

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date date = null;
        String formattedDate;
        try {
            date = inputFormat.parse(songItem.releaseDate);
            formattedDate = outputFormat.format(date);
        } catch (ParseException e) {
            formattedDate = "N/A Date";
        }
        viewHolder.mSongReleaseDate.setText(formattedDate);
    }

    @Override
    public int getItemCount(){
        return songList.results.size();
    }

    class SongViewHolder extends RecyclerView.ViewHolder{


        TextView mTrackName;
        TextView mArtistName;
        TextView mCollection;

        TextView mDiskNumber;
        TextView mTrackNumber;
        TextView mSongLength;
        TextView mSongCountry;
        TextView mSongReleaseDate;
        TextView mSongPrice;


        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            mTrackName = itemView.findViewById(R.id.tv_track_name);
            mArtistName = itemView.findViewById(R.id.tv_artist_name);
            mCollection = itemView.findViewById(R.id.tv_collection);
            mDiskNumber = itemView.findViewById(R.id.tv_disk_number);
            mTrackNumber = itemView.findViewById(R.id.tv_track_number);
            mSongLength = itemView.findViewById(R.id.tv_song_length);
            mSongReleaseDate = itemView.findViewById(R.id.tv_song_release_date);
            mSongCountry = itemView.findViewById(R.id.tv_song_country);
            mSongPrice = itemView.findViewById(R.id.tv_song_price);
        }



    }

}
