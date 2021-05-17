package com.android.michaeljacksonsongs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity implements ProgressHandlerCallback {

    private RecyclerView mRecyclerView;
    private SongListAdapter adapter;
    String respose = null;
    IOUtils ioUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ioUtils = new IOUtils(this, this);
        ioUtils.getResultList();
        mRecyclerView = findViewById(R.id.rv_song_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        mRecyclerView.setLayoutManager(linearLayoutManager);


    }

    SongList<Song> getSongListObject(String response){
        Type listType = new TypeToken<SongList<Song>>() {}.getType();
        Gson gson = new Gson();
        SongList<Song> songListObject = null;
         try{
             songListObject = gson.fromJson(response, listType);
         } catch (Exception e){
             e.printStackTrace();
         }

        return songListObject;
    }


    @Override
    public void onDataDownloadFinish() {
        respose = ioUtils.Response;
        adapter = new SongListAdapter(this, getSongListObject(respose));

        mRecyclerView.setAdapter(adapter);
    }
}