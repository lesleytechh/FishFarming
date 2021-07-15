package com.lesley.engelsimmanuel.fishfarming;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    // initialize your views here
    private FirebaseUser user;
    private ActionBar actionBar;
    private View splashRootView;
    private ProgressBar loadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // an action bar is the top bar that appears in an app
        // we want this splash screen to be fullscreen so we need to hide
        // the action bar too. First let us get the action bar
        user = FirebaseAuth.getInstance().getCurrentUser();
        actionBar = getSupportActionBar();

        // find views by id first to avoid null pointer exceptions
        splashRootView = findViewById(R.id.splash_root_view);
        loadingProgress = findViewById(R.id.splash_loading_progress);

        // hide the action bar if present
        if (actionBar != null) {
            actionBar.hide();
        }

        // make this activity full screen by hiding system UI components
        splashRootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        // show progress bar 3000 milliseconds (3 seconds) after this activity starts up
        // in programming we express time in milliseconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingProgress.setVisibility(View.VISIBLE);
            }
        }, 1500);

        // call this method
        finishThisActivity();

    }

    // finish this activity 5000 milliseconds (5 seconds) after it starts up
    private void finishThisActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // go to another activity
                if (user != null) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                }

                // destroy this activity after going to the other activity
                finish();
            }
        }, 2500);
    }

}
