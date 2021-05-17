package com.android.michaeljacksonsongs;

import java.util.List;

public class SongList<Song> {

    /*
    * Generic "Song" used as Song object definition is liable to change;
    *
    * */

    int resultCount;
    public List<Song> results;

    public List<Song> getResults() {
        return results;
    }

    public void setResults(List<Song> results) {
        this.results = results;
    }
}
