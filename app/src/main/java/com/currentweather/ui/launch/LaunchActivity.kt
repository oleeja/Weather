package com.currentweather.ui.launch

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.currentweather.R


class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.launchContainer) as NavHostFragment
        val fragment = navHostFragment.childFragmentManager.fragments[0]
        fragment?.onActivityResult(requestCode, resultCode, data)
    }
}
