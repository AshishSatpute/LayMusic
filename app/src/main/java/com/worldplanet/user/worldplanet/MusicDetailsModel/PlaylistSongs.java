package com.worldplanet.user.worldplanet.MusicDetailsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaylistSongs {
    @SerializedName("Song_Id")
    @Expose
    private long songId;
    @SerializedName("Song_Name")
    @Expose
    private String songName;
    @SerializedName("Song_Path")
    @Expose
    private String songPath;
    @SerializedName("Playlist_name")
    @Expose
    private String plyalistName;


    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }

    public String getPlyalistName() {
        return plyalistName;
    }

    public void setPlyalistName(String plyalistName) {
        this.plyalistName = plyalistName;
    }
}
