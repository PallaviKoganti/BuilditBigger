package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.udacity.gradle.jokedisplay.JokeActivity;
import com.udacity.gradle.jokes.Joker;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){
        Joker joker = new Joker();

        // Create Intent to launch JokeFactory Activity
        Intent intent = new Intent(this, JokeActivity.class);
        // Put the string in the envelope
        intent.putExtra(getString(R.string.hello_world), joker.getJoke());
        startActivity(intent);

        Toast.makeText(this, joker.getJoke(), Toast.LENGTH_SHORT).show();
        //new EndpointsAsyncTask().execute(this);
    }


}
