package com.example.careletricapp.presetantion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.careletricapp.R

class MainActivity : AppCompatActivity() {
    lateinit var btnRedicionar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        setupListeners()
    }

    fun setupView() {
        btnRedicionar = findViewById(R.id.btn_redirecionar)
    }

    fun setupListeners() {
        btnRedicionar.setOnClickListener {
            startActivity(Intent(this, CalcularAutonomiaActivity::class.java))
        }
    }

}