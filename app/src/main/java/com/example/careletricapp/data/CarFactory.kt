package com.example.careletricapp.data

import com.example.careletricapp.domain.Carro

object CarFactory {

    val list = listOf<Carro>(
        Carro(
            id = 1,
            preco = "R$ 300.000,00",
            bateria = "100Kwh",
            potencia = "200 cv",
            recarga = "30 min",
            urlFoto = "www.google.com.br",
            isFavorito = false
        )
    )
}