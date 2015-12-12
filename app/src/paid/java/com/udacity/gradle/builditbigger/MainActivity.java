package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import net.catsonmars.android.displayjoke.JokeActivity;


public class MainActivity extends ActionBarActivity implements IAsyncResponse {
    private ProgressBar mSpinner;
    private Button mTellJokeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize a progress bar
        mSpinner = (ProgressBar)findViewById(R.id.progressBar1);
        mSpinner.setVisibility(View.GONE);

        // initialize a button
        mTellJokeButton = (Button) findViewById(R.id.buttonTellJoke);
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
        mTellJokeButton.setEnabled(false);
        mSpinner.setVisibility(View.VISIBLE);

        EndpointsAsyncTask2 asyncTask = new EndpointsAsyncTask2(this);
        asyncTask.execute();
    }

    @Override
    public void processFinish(String output) {
        mSpinner.setVisibility(View.GONE);

        Intent intent = new Intent(this, JokeActivity.class);
        intent.putExtra(JokeActivity.JOKE_KEY, output);
        startActivity(intent);

        mTellJokeButton.setEnabled(true);
    }
}
