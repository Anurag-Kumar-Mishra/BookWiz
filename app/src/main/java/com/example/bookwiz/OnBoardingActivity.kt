package com.example.bookwiz

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.bookwiz.adapters.IPageAdapter
import com.example.bookwiz.adapters.ViewPagerAdapter
import me.relex.circleindicator.CircleIndicator3

class OnBoardingActivity : AppCompatActivity(), IPageAdapter {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        val instructions = listOf(
            "Hi, Thanks for installing ELECTROGEN, now, let's take a quick tour of the app. You need" +
                    " to sign in in order to continue",
            "Find the nearest charging station for your vehicle by clicking on locate icon",
            "Scan the QR Code of the station owner before charging your vehicle",
            "In a hurry?, don't worry, you can pay later by clicking on pay icon",
            "You can also contact us through call or email by clicking on contact icon",
            "Remember!, signing out will clear payment and charging history"
        )
        val imageUrls = listOf(
            "https://i.postimg.cc/jC8CtMWX/sign-In-Image.jpg",
            "https://i.postimg.cc/vD8DJJmT/locate-Image.jpg",
            "https://i.postimg.cc/nj2rsk3n/charge-Image.jpg",
            "https://i.postimg.cc/jWzLTKNw/payImage.jpg",
            "https://i.postimg.cc/47snNMb8/contact-Us-Image.jpg",
            "https://i.postimg.cc/BPv62KzH/sign-Out-Image.jpg"
        )
        val adapter = ViewPagerAdapter(instructions, imageUrls, this)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = adapter
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val circleIndicator3: CircleIndicator3 = findViewById(R.id.circleIndicator)
        circleIndicator3.setViewPager(viewPager)

    }

    @SuppressLint("ApplySharedPref")
    private fun saveData() {

        val sharedPreferences: SharedPreferences = getSharedPreferences("OnBoardingBoolean",
            Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putBoolean("FIRST_RUN_OVER", true)
        }.commit()

    }

    override fun onGetStartedClicked() {
        saveData()
        startActivity(Intent(this, SplashScreenActivity::class.java))
    }

}