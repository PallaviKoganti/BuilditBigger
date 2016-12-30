package com.udacity.gradle.builditbigger;

import com.udacity.gradle.builditbigger.MainActivityFragment;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by pallavi on 12/27/2016.
 */

public class EndpointsAsyncTaskTest {

    @Test
    public void testDoInBackground() throws Exception {
        MainActivityFragment fragment = new MainActivityFragment();
        fragment.testFlag = true;
        new EndpointsAsyncTask().execute(fragment);
        Thread.sleep(5000);
        assertTrue("Error: Fetched Joke = " + fragment.loadedJoke, fragment.loadedJoke != null);
    }
}
