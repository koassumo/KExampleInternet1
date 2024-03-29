package com.example.kexampleinternet1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kexampleinternet1.R

class MainActivity : AppCompatActivity() {

    // comment in detailedFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}