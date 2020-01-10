package com.example.hmacrequestbody

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.hmacrequestbody.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val viewModel = Viewmodel(HttpClient())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        binding.vm = viewModel
        viewModel.send()
    }
}
