package com.lesley.engelsimmanuel.fishfarming

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        actionBar?.hide()

        // make this activity full screen by hiding system UI components
        splash_root_view.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        // show progress bar after 3 seconds or rather 3000 milliseconds
        Handler().postDelayed({
            splash_loading_progress.visibility = View.VISIBLE
        }, 3000)

        finishThisActivity()
    }

    // finish this activity after 5 seconds or rather 5000 milliseconds
    private fun finishThisActivity(finishAfter: Long = 5000) {
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, finishAfter)
    }

}