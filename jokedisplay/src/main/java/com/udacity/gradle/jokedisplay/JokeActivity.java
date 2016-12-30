package com.udacity.gradle.jokedisplay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    public static String JOKE_KEY = "JOKE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        TextView textview = (TextView) findViewById(R.id.joke_text);

//Retrieve the joke from the Intent Extras
        String JokeResult = null;
//the Intent that started us
        Intent intent = getIntent();
        JokeResult = intent.getStringExtra(getString(R.string.hello_world));

        if (JokeResult != null) {
            textview.setText(JokeResult);
        } else {
            textview.setText("Where is the joke!!!");
        }

    }
}
