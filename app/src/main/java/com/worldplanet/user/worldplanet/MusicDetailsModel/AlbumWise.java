package com.worldplanet.user.worldplanet.MusicDetailsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlbumWise {

    @SerializedName("TopSong_Id")
    @Expose
    private Integer topSongId;
    @SerializedName("Song_Id")
    @Expose
    private Integer songId;
    @SerializedName("Category_Id")
    @Expose
    private Integer categoryId;
    @SerializedName("Category_Name")
    @Expose
    private String categoryName;
    @SerializedName("Song_Name")
    @Expose
    private String songName;
    @SerializedName("Song_Path")
    @Expose
    private String songPath;
    @SerializedName("Album_Id")
    @Expose
    private Integer albumId;
    @SerializedName("Album_Name")
    @Expose
    private String albumName;
    @SerializedName("Movie_Id")
    @Expose
    private Integer movieId;
    @SerializedName("Movie_Name")
    @Expose
    private String movieName;
    @SerializedName("Artist_Id")
    @Expose
    private Integer artistId;
    @SerializedName("Artist_Name")
    @Expose
    private String artistName;
    @SerializedName("Image")
    @Expose
    private String image;

    public Integer getTopSongId() {
        return topSongId;
    }

    public void setTopSongId(Integer topSongId) {
        this.topSongId = topSongId;
    }

    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}