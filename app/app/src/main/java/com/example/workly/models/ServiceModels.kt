package com.example.workly.models

data class ServiceItem(
    val id: Int,
    val title: String,
    val description: String,
    val estimatedValue: String,
    val clientName: String,
    val postedAgo: String,
    val location: String
)
