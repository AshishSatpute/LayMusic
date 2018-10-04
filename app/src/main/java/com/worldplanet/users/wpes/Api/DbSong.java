package com.worldplanet.users.wpes.Api;

import java.io.Serializable;

public class DbSong implements Serializable {
    private String Song_Name;
    private String Song_Path;
    private String Image;
    private String Artist_Name;
    private String Album_Name;
    private String Movie_Name;
    private String Category_Name;
    private String Category_Id;
    private String Album_Id;
    private String Artist_Id;
    private String TopSong_Id;
    private String Song_Id;
    private String Movie_Id;

    public String getTopSong_Id() {
        return TopSong_Id;
    }

    public void setTopSong_Id(String topSong_Id) {
        TopSong_Id = topSong_Id;
    }

    public String getSong_Id() {
        return Song_Id;
    }

    public void setSong_Id(String song_Id) {
        Song_Id = song_Id;
    }

    public String getMovie_Id() {
        return Movie_Id;
    }

    public void setMovie_Id(String movie_Id) {
        Movie_Id = movie_Id;
    }

    public String getCategory_Name() {
        return Category_Name;
    }

    public void setCategory_Name(String category_Name) {
        Category_Name = category_Name;
    }

    public String getCategory_Id() {
        return Category_Id;
    }

    public void setCategory_Id(String category_Id) {
        Category_Id = category_Id;
    }

    public String getAlbum_Id() {
        return Album_Id;
    }

    public void setAlbum_Id(String album_Id) {
        Album_Id = album_Id;
    }

    public String getArtist_Id() {
        return Artist_Id;
    }

    public void setArtist_Id(String artist_Id) {
        Artist_Id = artist_Id;
    }

    public String getArtist_Name()
    {
        return Artist_Name;
    }

    public void setArtist_Name(String artist_Name) {

        Artist_Name = artist_Name;
    }

    public String getAlbum_Name() {

        return Album_Name;
    }

    public void setAlbum_Name(String album_Name)
    {
        Album_Name = album_Name;
    }

    public String getMovie_Name()
    {
        return Movie_Name;
    }

    public void setMovie_Name(String movie_Name)
    {
        Movie_Name = movie_Name;
    }


    public String getSong_Name()
    {
        return Song_Name;
    }

    public void setSong_Name(String song_Name) {

        Song_Name = song_Name;
    }

    public String getSong_Path()
    {
        return Song_Path;
    }

    public void setSong_Path(String song_Path) {

        Song_Path = song_Path;
    }

    public String getImage() {

        return Image;
    }

    public void setImage(String image) {

        Image = image;
    }

    @Override
    public String toString() {
        return "DbSong{" +
                "Song_Name='" + Song_Name + '\'' +
                ", Song_Path='" + Song_Path + '\'' +
                ", Image='" + Image + '\'' +
                ", Artist_Name='" + Artist_Name + '\'' +
                ", Album_Name='" + Album_Name + '\'' +
                ", Movie_Name='" + Movie_Name + '\'' +
                ", Category_Name='" + Category_Name + '\'' +
                ", Category_Id='" + Category_Id + '\'' +
                ", Album_Id='" + Album_Id + '\'' +
                ", Artist_Id='" + Artist_Id + '\'' +
                '}';
    }


    public DbSong(String song_Name, String song_Path, String image, String artist_Name, String album_Name, String movie_Name) {
        Song_Name = song_Name;
        Song_Path = song_Path;
        Image = image;
        Artist_Name = artist_Name;
        Album_Name = album_Name;
        Movie_Name = movie_Name;

    }



}
