package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.udacity.gradle.builditbigger.EndpointsAsyncTask;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.jokedisplay.JokeActivity;

import static com.udacity.gradle.builditbigger.R.id.progressBar;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    ProgressBar progressbar = null;
    public String loadedJoke = null;
    public boolean testFlag = false;
    PublisherInterstitialAd mPublisherInterstitialAd = null;
    String LOG_TAG = "PAIDADS";

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Set up for pre-fetching interstitial ad request
        mPublisherInterstitialAd = new PublisherInterstitialAd(getContext());
        mPublisherInterstitialAd.setAdUnitId("ca-app-pub-4707117554213885/7529325252");

        mPublisherInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                //process the joke Request
                progressbar.setVisibility(View.VISIBLE);
                fetchJoke();

                //pre-fetch the next ad
                requestNewInterstitial();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);

                Log.i(LOG_TAG, "onAdFailedToLoad: ad Failed to load. Reloading...");

                //prefetch the next ad
                requestNewInterstitial();

            }

            @Override
            public void onAdLoaded() {
                Log.i(LOG_TAG, "onAdLoaded: interstitial is ready!");
                super.onAdLoaded();
            }
        });

        //Kick off the fetch
        requestNewInterstitial();


        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);

        // Set onClickListener for the button
        Button button = (Button) root.findViewById(R.id.joke_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mPublisherInterstitialAd.isLoaded()) {
                    Log.i(LOG_TAG, "onClick: interstitial was ready");
                    mPublisherInterstitialAd.show();
                } else {
                    Log.i(LOG_TAG, "onClick: interstitial was not ready.");
                    progressbar.setVisibility(View.VISIBLE);
                    fetchJoke();
                }
            }
        });

        progressbar = (ProgressBar) root.findViewById(R.id.joke_progressbar);
        progressbar.setVisibility(View.GONE);



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

    private void requestNewInterstitial() {
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("EA27D37DF5448BF42AA5F7A6D4F11A9B")
                .build();

        mPublisherInterstitialAd.loadAd(adRequest);
    }

}
