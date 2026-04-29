package com.example.workly.domain

import com.example.workly.models.ServiceItem

val sampleServices = listOf(
    ServiceItem(
        id = 1,
        title = "Instalação elétrica residencial",
        description = "Troca de fiação antiga e instalação de tomadas e pontos de energia.",
        estimatedValue = "R$ 280,00",
        clientName = "Mariana Silva",
        postedAgo = "2h atrás",
        location = "Zona Sul"
    ),
    ServiceItem(
        id = 2,
        title = "Pintura de sala e hall",
        description = "Pintura completa de sala de 20m² e hall de entrada.",
        estimatedValue = "R$ 520,00",
        clientName = "Gabriel Santos",
        postedAgo = "5h atrás",
        location = "Barra Funda"
    ),
    ServiceItem(
        id = 3,
        title = "Conserto de vazamento de pia",
        description = "Substituição de canos e revisão do sifão.",
        estimatedValue = "R$ 180,00",
        clientName = "Ana Pereira",
        postedAgo = "1 dia atrás",
        location = "Vila Mariana"
    )
)
