package com.example.careletricapp.presetantion

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.careletricapp.R
import com.example.careletricapp.data.CarsApi
import com.example.careletricapp.domain.Carro
import com.example.careletricapp.presetantion.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CarFragment : Fragment() {

    lateinit var btnCalcular: FloatingActionButton
    lateinit var listInfo: RecyclerView
    lateinit var progress: ProgressBar

    lateinit var carsApi: CarsApi

    var carrosList: ArrayList<Carro> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.car_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRetrofit()
        setupView(view)
        setupListeners()
        getAllCars()
    }

    fun setupView(view: View) {
        view.apply {
            btnCalcular = findViewById(R.id.fab_calcular)
            listInfo = findViewById(R.id.rv_lista_carros)
            progress = findViewById(R.id.pb_load)
        }

    }


    fun setupList(list: List<Carro>) {
        val carroAdapter = CarAdapter(list)
        listInfo.apply {
            visibility = View.VISIBLE
            adapter = carroAdapter
        }
    }

    fun setupListeners() {
        btnCalcular.setOnClickListener {
            startActivity(Intent(context, CalcularAutonomiaActivity::class.java))
        }
    }

    fun setupRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://igorbag.github.io/cars-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        carsApi = retrofit.create(CarsApi::class.java)
    }

    fun getAllCars() {
        carsApi.getAllCars().enqueue(object : Callback<List<Carro>> {
            override fun onResponse(call: Call<List<Carro>>, response: Response<List<Carro>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        setupList(it)

                    }
                } else {
                    Toast.makeText(context, "Erro na conexão ", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Carro>>, t: Throwable) {
                Toast.makeText(context, "Erro na conexão ", Toast.LENGTH_LONG).show()
            }

        })
    }


}

