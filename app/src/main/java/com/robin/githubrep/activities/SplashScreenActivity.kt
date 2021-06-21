package com.robin.githubrep.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.robin.githubrep.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Set animation
        binding.ivSplashScreen.alpha = 0f
        binding.ivSplashScreen.animate().setDuration(1800).alpha(1f).withEndAction {

            val intent = Intent(this, MainActivity::class.java)

            //Start the intent
            startActivity(intent)

            //Override ending transition to complete the animation
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

            //Remove this Activity from the backstack so the app closes when the user presses back
            finish()
        }
    }
}