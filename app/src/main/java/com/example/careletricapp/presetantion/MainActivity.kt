package com.example.careletricapp.presetantion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.careletricapp.R
import com.example.careletricapp.data.CarFactory
import com.example.careletricapp.presetantion.adapter.CarAdapter

class MainActivity : AppCompatActivity() {
    lateinit var btnRedicionar: Button
    lateinit var listInfo : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        setupList()
        setupListeners()
    }

    fun setupView() {
        btnRedicionar = findViewById(R.id.btn_redirecionar)
        listInfo = findViewById(R.id.rv_lista_carros)
    }

    fun setupList(){
        val adapter = CarAdapter(CarFactory.list)
        listInfo.adapter = adapter
    }

    fun setupListeners() {
        btnRedicionar.setOnClickListener {
            startActivity(Intent(this, CalcularAutonomiaActivity::class.java))
        }
    }

}