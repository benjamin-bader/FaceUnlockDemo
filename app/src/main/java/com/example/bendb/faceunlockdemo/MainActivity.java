package com.example.bendb.faceunlockdemo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bendb.faceunlock.FaceUnlock;
import com.bendb.faceunlock.FaceUnlockCallback;


public class MainActivity extends ActionBarActivity implements FaceUnlockCallback {
    private FaceUnlock mFaceUnlock;
    private View mSurface;
    private Button mConnectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSurface = findViewById(R.id.surface);

        mFaceUnlock = new FaceUnlock(this, this);
        boolean hasLock = mFaceUnlock.isEnabled();

        mConnectButton = (Button) findViewById(R.id.button);

        mConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFaceUnlock.start(mSurface);
            }
        });


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

    @Override
    public void unlock() {
        Log.d("MainActivity", "Hey, I know you!");
        Toast.makeText(this, "Unlock!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cancel() {
        Log.d("MainActivity", "Nope.");
        Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void reportFailedAttempt() {
        Log.d("MainActivity", "Not yet....");
    }
}
