package com.example.careletricapp.local

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import com.example.careletricapp.domain.Carro
import com.example.careletricapp.local.CarrosContract.CarEntry.COLUMN_NAME_BATERIA
import com.example.careletricapp.local.CarrosContract.CarEntry.COLUMN_NAME_CAR_ID
import com.example.careletricapp.local.CarrosContract.CarEntry.COLUMN_NAME_POTENCIA
import com.example.careletricapp.local.CarrosContract.CarEntry.COLUMN_NAME_PRECO
import com.example.careletricapp.local.CarrosContract.CarEntry.COLUMN_NAME_RECARGA
import com.example.careletricapp.local.CarrosContract.CarEntry.COLUMN_NAME_URL_FOTO
import com.example.careletricapp.local.CarrosContract.TABLE_CAR

class CarRepository(private val context: Context) {

    fun saveDatabase(carro: Carro): Boolean {
        var isSaved = false
        try {
            val dbHelper = CarsDbHelper(context)
            val db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                put(COLUMN_NAME_CAR_ID, carro.id)
                put(COLUMN_NAME_PRECO, carro.preco)
                put(COLUMN_NAME_BATERIA, carro.bateria)
                put(COLUMN_NAME_POTENCIA, carro.potencia)
                put(COLUMN_NAME_RECARGA, carro.recarga)
                put(COLUMN_NAME_URL_FOTO, carro.urlFoto)
            }

            val newRegister = db?.insert(CarrosContract.CarEntry.TABLE_NAME, null, values)
            if (newRegister != null) {
                isSaved = true
            }
        } catch (ex: Exception) {
            ex.message?.let { Log.e("Erro", it) }
        }
        return isSaved
    }

    fun findCarById(id: Int) {
        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.writableDatabase
        // listagem das colunas a serem mostradas no resultado da query
        val columns = arrayOf(
            BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_PRECO,
            COLUMN_NAME_BATERIA,
            COLUMN_NAME_POTENCIA,
            COLUMN_NAME_RECARGA,
            COLUMN_NAME_URL_FOTO
        )
        val filter = "${COLUMN_NAME_CAR_ID} = ?"
        val filterValues = arrayOf(id.toString())

        val cursor = db.query(
            CarrosContract.CarEntry.TABLE_NAME, // nome da tabela
            columns,
            filter,
            filterValues,
            null,
            null,
            null
        )
        val itemCar = mutableListOf<Carro>()
        with(cursor) {
            while (moveToNext()) {
                val itemId = getLong(getColumnIndexOrThrow(COLUMN_NAME_CAR_ID))
                Log.d("ID ->", itemId.toString())

                val itemPreco = getString(getColumnIndexOrThrow(COLUMN_NAME_PRECO))
                Log.d("Preço ->", itemPreco)
            }
        }

        cursor.close()
    }

}
