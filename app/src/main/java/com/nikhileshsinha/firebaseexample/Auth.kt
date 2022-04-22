package com.nikhileshsinha.firebaseexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nikhileshsinha.firebaseexample.databinding.ActivityAuthBinding

class Auth : AppCompatActivity() {

    lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}