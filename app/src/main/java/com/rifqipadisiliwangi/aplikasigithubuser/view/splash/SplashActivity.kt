package com.rifqipadisiliwangi.aplikasigithubuser.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.rifqipadisiliwangi.aplikasigithubuser.R
import com.rifqipadisiliwangi.aplikasigithubuser.databinding.ActivitySplashBinding
import com.rifqipadisiliwangi.aplikasigithubuser.view.home.HomeActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, 3000)
        setContentView(binding.root)
//        supportActionBar?.elevation = 0f
//        supportActionBar?.setDisplayUseLogoEnabled(false);
//        supportActionBar?.setDisplayShowHomeEnabled(false);
//        supportActionBar?.setDisplayHomeAsUpEnabled(false);
        supportActionBar?.hide()
    }
}