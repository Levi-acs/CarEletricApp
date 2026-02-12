package com.example.careletricapp.presetantion

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
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
import org.json.JSONArray
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.net.URL

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

    fun callService() {
        val urlBase = "https://igorbag.github.io/cars-api/cars.json"
        MyTask().execute(urlBase)
    }

    inner class MyTask : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            Log.d("MyTask", "Iniciando...")
            progress.visibility = View.VISIBLE

        }

        override fun doInBackground(vararg url: String?): String {
            var urlConnection: HttpURLConnection? = null

            try {
                val url = URL(url[0])
                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = 60000
                urlConnection.readTimeout = 60000
                urlConnection.setRequestProperty(
                    "Accept",
                    "application/json"
                )

                val responseCode = urlConnection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    var response = urlConnection.inputStream.bufferedReader().readText()
                    publishProgress(response)
                } else {
                    Log.e("Erro", "Serviço Indisponivel no momento...")

                }


            } catch (ex: Exception) {
                Log.e("Erro", "erro ao conectar")
            } finally {
                urlConnection?.disconnect()
            }
            return ""
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {
                val jsonArray = JSONTokener(values[0]).nextValue() as JSONArray
                for (i in 0 until jsonArray.length()) {
                    val id = jsonArray.getJSONObject(i).getString("id")
                    Log.d("ID ->", id)

                    val preco = jsonArray.getJSONObject(i).getString("preco")
                    Log.d("Preco ->", preco)

                    val bateria = jsonArray.getJSONObject(i).getString("bateria")
                    Log.d("Preco ->", bateria)

                    val potencia = jsonArray.getJSONObject(i).getString("potencia")
                    Log.d("Potencia ->", potencia)

                    val recarga = jsonArray.getJSONObject(i).getString("potencia")
                    Log.d("Recarga ->", recarga)

                    val urlFoto = jsonArray.getJSONObject(i).getString("urlPhoto")
                    Log.d("urlPhoto ->", urlFoto)
                    val model = Carro(
                        id = id.toInt(),
                        preco = preco,
                        bateria = bateria,
                        potencia = potencia,
                        recarga = recarga,
                        urlFoto = urlFoto
                    )
                    carrosList.add(model)
                }
                progress.visibility = View.GONE
              //  setupList()
            } catch (ex: Exception) {
                Log.e("Erro ->", ex.message.toString())
            }
        }


    }
}
