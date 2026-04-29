package com.example.workly.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ServiceCard(
    title: String,
    description: String,
    buttonText: String,
    onClick: () -> Unit = {}
) {
    Card {
        Column {
            Text(title)
            Text(description)

            Button(onClick = onClick) {
                Text(buttonText)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ServiceCardPreview() {
    ServiceCard(
        title = "Conserto de pia",
        description = "Preciso urgente",
        buttonText = "Editar",
        onClick = {}
    )
}