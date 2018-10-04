package com.worldplanet.users.wpes.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.worldplanet.users.wpes.MusicDetailsModel.PlayList;
import com.worldplanet.users.wpes.MusicDetailsModel.PlaylistSongs;
import com.worldplanet.users.wpes.MusicDetailsModel.TopSongs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String PLAYLIST_TABLE_NAME = "playlistDetails";
    public static final String SONGS_ENTRY_TABLE_NAME = "SongsDetail";

    public MyDatabase(Context context){

        super(context, DATABASE_NAME ,null,2);
    }

    String query = "CREATE TABLE " + PLAYLIST_TABLE_NAME +
            "( playlist_id INTEGER PRIMARY KEY autoincrement , playlist_name TEXT  )";


    String query1 = "CREATE TABLE " + SONGS_ENTRY_TABLE_NAME +
            "( song_id INTEGER PRIMARY KEY autoincrement, song_name TEXT ," +
            " song_path TEXT , playlist_name TEXT  )";

    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL(query);
        database.execSQL(query1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int oldVersion, int newversion){
        System.out.print("Database is upgrated from" +oldVersion +"to" +newversion);
        arg0.execSQL("DROP TABLE IF EXISTS " + PLAYLIST_TABLE_NAME);
        arg0.execSQL("DROP TABLE IF EXISTS " + SONGS_ENTRY_TABLE_NAME);
        onCreate(arg0);
    }

    public SQLiteDatabase openDatabase(){

        return super.getWritableDatabase();
    }

    public List<PlayList> getAllPlaylistRecord() {

        List<PlayList> contactArrayList = new ArrayList<>();
        String selectQuery = " SELECT * FROM " + PLAYLIST_TABLE_NAME;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PlayList contactMap = new PlayList();
                contactMap.setPlayList_id(cursor.getInt(0));
                contactMap.setPlayList_name(cursor.getString(1));
                contactArrayList.add(contactMap);
            } while (cursor.moveToNext());
        }
        return contactArrayList;
    }
    public List<PlaylistSongs> getPlaylistSongRecord(String playlistName) {

        List<PlaylistSongs> contactArrayList = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM SongsDetail where playlist_name = '"+ playlistName + "'";
       // String selectQuery = "SELECT * FROM "+ SONGS_ENTRY_TABLE_NAME + " WHERE playlist_name = " + playlistName;
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PlaylistSongs contactMap = new PlaylistSongs();
                contactMap.setSongName((cursor.getString(1)));
                contactMap.setSongPath(cursor.getString(2));
                contactArrayList.add(contactMap);
            } while (cursor.moveToNext());
        }
        return contactArrayList;
    }

  /*  public Cursor getPlaylistName() {
        Cursor res = null;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            res = db.rawQuery("select * from " + PLAYLIST_TABLE_NAME , null);
            return res;
        } catch (Exception ex) {

        }
        return res;
    }*/

    public List<TopSongs> getAllSongsRecord() {

        List<TopSongs> contactArrayList = new ArrayList<>();
        String selectQuery = " SELECT * FROM " + SONGS_ENTRY_TABLE_NAME;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TopSongs contactMap = new TopSongs();
                contactMap.setSongId(cursor.getInt(0));
                contactMap.setSongName(cursor.getString(1));
                contactMap.setSongPath(cursor.getString(2));
                contactArrayList.add(contactMap);

            } while (cursor.moveToNext());

        }
        return contactArrayList;
    }

    public boolean deleteAllRecord() {
        SQLiteDatabase database = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + SONGS_ENTRY_TABLE_NAME ;
       // database.execSQL( "DROP TABLE IF EXISTS " + PRODUCT_ENTRY_TABLE_NAME );
        database.execSQL(deleteQuery);
        return true;
    }

    public boolean checkAlreadyExist(String playlistName)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT playlist_name FROM "+ PLAYLIST_TABLE_NAME + " WHERE playlist_name " + " =?";
        Cursor cursor = database.rawQuery(query, new String[]{playlistName});
        if (cursor.getCount() > 0)
        {
            return false;
        }
        else
            return true;
    }

    public boolean checkSongAlreadyExist(String songName, String playlistName)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM SongsDetail where playlist_name = '"+ playlistName + "' and song_name = '"+ songName + "'";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst())
        {
            return false;
        }
        else
            return true;
    }
    public void insertPlaylistName(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("playlist_name", queryValues.get("playlist_name"));
        database.insert(PLAYLIST_TABLE_NAME, null, values);
        database.close();

    }

    // Update data in Head tables
    public int updatePlaylistName(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("playlist_name", queryValues.get("playlist_name"));


        return database.update(PLAYLIST_TABLE_NAME, values,
                "playlist_id" + " = ?", new String[]{queryValues.get("playlist_id")});

    }



    // Insert data in Child tables
    public void insertSongsDetails(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("song_name", queryValues.get("song_name"));
        values.put("song_path", queryValues.get("song_path"));
        values.put("playlist_name", queryValues.get("playlist_name"));

        database.insert(SONGS_ENTRY_TABLE_NAME, null, values);
        database.close();
    }

    public boolean deletePlaylist(String playlistName) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("delete from "+PLAYLIST_TABLE_NAME+" where playlist_name='"+playlistName+"'");
        return true;
    }
    public boolean deletePlaylistSongs(String playlistName) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("delete from "+SONGS_ENTRY_TABLE_NAME+" where playlist_name='"+playlistName+"'");
        return true;
    }

    public boolean deleteSongs(String playlistName,String songName) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("delete from SongsDetail where playlist_name = '"+ playlistName + "' and song_name = '"+ songName + "'");
        return true;
    }
}

