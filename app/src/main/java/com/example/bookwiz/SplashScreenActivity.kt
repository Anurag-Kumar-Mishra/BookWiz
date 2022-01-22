package com.example.bookwiz

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        if(loadData()){

            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent (this,SignInActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)

        }
        else
            startActivity(Intent(this,OnBoardingActivity::class.java))

    }
    private fun loadData(): Boolean {
        val sharedPreferences: SharedPreferences = getSharedPreferences("OnBoardingBoolean",
            Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("FIRST_RUN_OVER", false)
    }
}