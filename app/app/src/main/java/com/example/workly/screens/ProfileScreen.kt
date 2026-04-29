package com.example.workly.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {

    var userName by remember { mutableStateOf("João") }
    var userEmail by remember { mutableStateOf("joao@email.com") }
    var profession by remember { mutableStateOf("Eletricista") }
    var professionalDescription by remember { mutableStateOf("Profissional qualificado em instalações elétricas residenciais.") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meu perfil") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier.size(72.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text("Olá, $userName", style = MaterialTheme.typography.headlineSmall)
                    Text(
                        userEmail,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }

            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = userEmail,
                onValueChange = { userEmail = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = profession,
                onValueChange = { profession = it },
                label = { Text("Profissão") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = professionalDescription,
                onValueChange = { professionalDescription = it },
                label = { Text("Descrição profissional") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                maxLines = 5
            )

            Button(
                onClick = { /* Salvar alterações */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Salvar alterações")
            }

            OutlinedButton(
                onClick = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Sair da conta")
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 900)
@Composable
fun ProfileScreenPreview() {
    val navController = rememberNavController()
    ProfileScreen(navController = navController)
}
