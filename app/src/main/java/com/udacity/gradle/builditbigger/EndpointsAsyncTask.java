package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.pallavi.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
//import com.udacity.gradle.builditbigger.free.MainActivityFragment;

import java.io.IOException;


/**
 * Created by pallavi on 12/22/2016.
 */

public class EndpointsAsyncTask extends AsyncTask<MainActivityFragment, Void, String> {

    private static MyApi myApiService = null;
    private OnJokeReceivedListener listener;
    private Context context;
    private MainActivityFragment mainActivityFragment;

    @Override
    protected String doInBackground(MainActivityFragment... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    //.setRootUrl("http://10.0.2.2:8080/_ah/api/");
            .setRootUrl("https://builditbigger-152917.appspot.com/_ah/api/");

            myApiService = builder.build();
        }

        mainActivityFragment = params[0];
        context = mainActivityFragment.getActivity();


        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        // Create Intent to launch JokeFactory Activity
       /* Intent intent = new Intent(context, JokeActivity.class);
        // Put the string in the envelope
        intent.putExtra(JokeActivity.JOKE_KEY,result);
        context.startActivity(intent);*/
        mainActivityFragment.loadedJoke = result;
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}
