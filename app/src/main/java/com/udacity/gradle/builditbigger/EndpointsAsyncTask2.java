package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import net.catsonmars.android.app.backend.myApi.MyApi;

import java.io.IOException;

/**
 * Created by pmatushkin on 12/5/2015.
 */
// AsyncTask structure: credits to https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
// Delegates: credits to http://stackoverflow.com/questions/12575068/how-to-get-the-result-of-onpostexecute-to-main-activity-because-asynctask-is-a
public class EndpointsAsyncTask2 extends AsyncTask<Void, Void, String> {
    private MyApi myApiService = null;

    public IAsyncResponse delegate = null;

    public EndpointsAsyncTask2(IAsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(Void... params) {
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("https://javajokesserver.appspot.com/_ah/api/");

            myApiService = builder.build();
        }

        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String joke) {
        if (null != delegate)
            delegate.processFinish(joke);
    }
}
