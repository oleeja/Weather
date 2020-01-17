package com.currentweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.currentweather.ui.WeatherFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.layoutConteiner, WeatherFragment.newInstance()).commit()
    }
}
