package com.example.saywhat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.saywhat.ui.main.GettingStartedFrag

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, GettingStartedFrag.newInstance())
                .commitNow()
        }
    }
}