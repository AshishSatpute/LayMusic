package com.worldplanet.users.wpes.Api;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by abc on 05-08-2017.
 */
public class RESTClient {
        public static APInterface apiInterface;

        public static String BASE_URL = "http://laymusic.in";

        static {
            setUpAdapter();
        }


        public static final APInterface getRestAdapter() {
            return apiInterface;
        }

        private static void setUpAdapter() {

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(BASE_URL)
                    .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();

            apiInterface = restAdapter.create(APInterface.class);
        }

    }
