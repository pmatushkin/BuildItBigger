package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.text.TextUtils;

import java.util.concurrent.CountDownLatch;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
// credits to http://marksunghunpark.blogspot.com/2015/05/how-to-test-asynctask-in-android.html
public class ApplicationTest extends ApplicationTestCase<Application> {
    String mJokeString = null;
    CountDownLatch signal = null;

    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        signal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        signal.countDown();
    }

    public void testGetJokeAsyncTask() throws Exception {
        EndpointsAsyncTask2 task = new EndpointsAsyncTask2(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                mJokeString = output;
                signal.countDown();
            }
        });
        task.execute();
        signal.await();

        assertFalse(TextUtils.isEmpty(mJokeString));
    }
}