package com.worldplanet.users.worldplanet.MusicDetailsModel;

import java.io.Serializable;

/**
 * Created by user on 11/29/2017.
 */

public class PlayList implements Serializable {
    private int playList_id;
    private String playList_name;

    public int getPlayList_id() {
        return playList_id;
    }

    public void setPlayList_id(int playList_id) {
        this.playList_id = playList_id;
    }

    public String getPlayList_name() {
        return playList_name;
    }

    public void setPlayList_name(String playList_name) {
        this.playList_name = playList_name;
    }
}
