package com.example.workly.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderHomeScreen(navController: NavController) {

    val exampleServices = listOf(
        "Trocar chuveiro",
        "Pintar parede",
        "Conserto de pia",
        "Instalar ventilador"
    )

    val exampleDescriptions = listOf(
        "Casa no centro, urgente.",
        "Sala pequena, pintura simples.",
        "Preciso de alguém experiente.",
        "Quarto do apartamento 302."
    )

    val exampleButtonTexts = listOf(
        "Tenho interesse",
        "Tenho interesse",
        "Tenho interesse",
        "Tenho interesse"
    )

    var query by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Serviços disponíveis") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("profile") }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu / Perfil")
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {

                TextField(
                    value = query,
                    onValueChange = { query = it },
                    label = { Text("Buscar serviços") },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 4.dp)
                ) {
                    itemsIndexed(exampleServices) { index, title ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = exampleDescriptions[index],
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Button(
                                        onClick = {},
                                    ) {
                                        Text(exampleButtonTexts[index])
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true, heightDp = 1000)
@Composable
fun ProviderHomeScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        ProviderHomeScreen(navController = navController)
    }
}