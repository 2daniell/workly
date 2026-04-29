package com.example.workly.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.workly.components.ServiceCard
import com.example.workly.domain.sampleServices

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderHomeScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredServices = sampleServices.filter { service ->
        service.title.contains(searchQuery, ignoreCase = true) ||
            service.description.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Serviços disponíveis") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("profile") }) {
                        Icon(Icons.Default.Menu, contentDescription = "Perfil")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Explore oportunidades", style = MaterialTheme.typography.headlineSmall)
                    Text(
                        "Veja serviços publicados por clientes e envie propostas diretamente.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar serviços") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (filteredServices.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "Nenhum serviço encontrado.",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    items(filteredServices) { service ->
                        ServiceCard(
                            title = service.title,
                            description = service.description,
                            metadata = "${service.estimatedValue} • ${service.location}",
                            actionText = "Enviar proposta",
                            onActionClick = {
                                navController.navigate("service_detail/${service.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 1000)
@Composable
fun ProviderHomeScreenPreview() {
    val navController = rememberNavController()
    ProviderHomeScreen(navController = navController)
}
