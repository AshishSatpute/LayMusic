package com.worldplanet.user.worldplanet.Api;

import com.worldplanet.user.worldplanet.MusicDetailsModel.Album;
import com.worldplanet.user.worldplanet.MusicDetailsModel.AlbumWise;
import com.worldplanet.user.worldplanet.MusicDetailsModel.Artist;
import com.worldplanet.user.worldplanet.MusicDetailsModel.ArtistWise;
import com.worldplanet.user.worldplanet.MusicDetailsModel.Categories;
import com.worldplanet.user.worldplanet.MusicDetailsModel.CategoryWise;
import com.worldplanet.user.worldplanet.MusicDetailsModel.MovieWise;
import com.worldplanet.user.worldplanet.MusicDetailsModel.Movies;
import com.worldplanet.user.worldplanet.MusicDetailsModel.NewRelease;
import com.worldplanet.user.worldplanet.MusicDetailsModel.TopSongs;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by user on 11/21/2017.
 */

public interface APInterface {
    @GET("/api/GetAllSong")
    void getAllSongs(@Query("Type") String Type, Callback<List<TopSongs>> callback);

    @GET("/api/GetAllTopSong")
    void getTopSongs(@Query("Top_Songs") String Type, Callback<List<TopSongs>> callback);

    @GET("/api/GetAllNewReleaseSong")
    void getNewReleaseSongs(@Query("NewR_Songs") String NewR_Songs, Callback<List<NewRelease>> callback);

    @GET("/api/ArtistList")
    void getArtistDetails(@Query("Artist") String Artist, Callback<List<Artist>> callback);

    @GET("/api/AlbumList")
    void getAlbums(@Query("Album") String Artist, Callback<List<Album>> callback);

    @GET("/api/CategoryList")
    void getCategories(@Query("Category") String category, Callback<List<Categories>> callback);

    @GET("/api/MovieList")
    void getMovieList(@Query("Movie") String movies, Callback<List<Movies>> callback);

    @GET("/api/GetAlbumWiseSong")
    void getAlbumsWiseData(@Query("Album_Name") String Artist, Callback<List<AlbumWise>> callback);

    @GET("/api/GetArtistWiseSong")
    void getArtistWiseData(@Query("Artist_Name") String Artist, Callback<List<ArtistWise>> callback);

    @GET("/api/GetMovieWiseSong")
    void getMovieWiseData(@Query("Movie_Name") String Artist, Callback<List<MovieWise>> callback);

    @GET("/api/GetCategoryWiseSong")
    void getCategoryWiseData(@Query("Category_Name") String Artist, Callback<List<CategoryWise>> callback);
}

