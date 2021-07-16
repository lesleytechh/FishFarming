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
    private FirebaseUser user;
    private ActionBar actionBar;
    private View splashRootView;
    private ProgressBar loadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        user = FirebaseAuth.getInstance().getCurrentUser();
        actionBar = getSupportActionBar();

        splashRootView = findViewById(R.id.splash_root_view);
        loadingProgress = findViewById(R.id.splash_loading_progress);

        if (actionBar != null) {
            actionBar.hide();
        }

        splashRootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingProgress.setVisibility(View.VISIBLE);
            }
        }, 1500);

        finishThisActivity();

    }

    private void finishThisActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (user != null) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                }
                finish();
            }
        }, 2500);
    }

}
