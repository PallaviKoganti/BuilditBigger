package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.udacity.gradle.jokedisplay.JokeActivity;

import static com.udacity.gradle.builditbigger.R.id.progressBar;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    ProgressBar progressbar = null;
    public String loadedJoke = null;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        Button button = (Button) root.findViewById(R.id.joke_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                progressbar.setVisibility(View.VISIBLE);
                fetchJoke();
            }
        });

        progressbar = (ProgressBar) root.findViewById(R.id.joke_progressbar);
        progressbar.setVisibility(View.GONE);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        return root;
    }

    public void fetchJoke(){

        new EndpointsAsyncTask().execute(this);
    }

    public void launchJokeActivity(){
        Context context = getActivity();
        Intent intent = new Intent(context, JokeActivity.class);
        intent.putExtra(context.getString(R.string.hello_world), loadedJoke);
        //Toast.makeText(context, loadedJoke, Toast.LENGTH_LONG).show();
        context.startActivity(intent);
        progressbar.setVisibility(View.GONE);
    }
}
