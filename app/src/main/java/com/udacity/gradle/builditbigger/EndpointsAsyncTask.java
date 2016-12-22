package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.pallavi.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.udacity.gradle.jokedisplay.JokeActivity;

import java.io.IOException;


/**
 * Created by pallavi on 12/22/2016.
 */

public class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {

    private static MyApi myApiService = null;
    private OnJokeReceivedListener listener;
    private Context context;

    @Override
    protected String doInBackground(Context... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/");
            //.setRootUrl("http://builditbigger-152917.appspot.com/_ah/api/");

            myApiService = builder.build();
        }

        context = params[0];


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
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}
