package com.orangeink.regalia22.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.orangeink.regalia22.databinding.ActivitySplashBinding
import com.orangeink.regalia22.home.ui.HomeActivity
import com.orangeink.regalia22.login.LoginActivity
import com.orangeink.regalia22.preferences.Prefs
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        decideNextPage()
    }

    private fun decideNextPage() {
        val user = Prefs(this).user
        if (user == null)
            startActivity(Intent(this, LoginActivity::class.java))
        else
            startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}