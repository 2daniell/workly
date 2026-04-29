package com.example.workly.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateServiceScreen(navController: NavController) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var estimatedValue by remember { mutableStateOf("") }
    var jobTypeExpanded by remember { mutableStateOf(false) }
    var selectedJobType by remember { mutableStateOf("Eletricista") }

    val jobTypes = listOf(
        "Eletricista",
        "Encanador",
        "Pintor",
        "Pedreiro",
        "Marido de aluguel",
        "Limpeza",
        "Outro"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Novo serviço") },
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
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título do serviço") },
                placeholder = { Text("Ex: Troca de chuveiro") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = estimatedValue,
                onValueChange = { estimatedValue = it },
                label = { Text("Valor estimado") },
                placeholder = { Text("R$ 250,00") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = jobTypeExpanded,
                onExpandedChange = { jobTypeExpanded = it },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = selectedJobType,
                    onValueChange = {},
                    label = { Text("Tipo de profissional") },
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = jobTypeExpanded,
                    onDismissRequest = { jobTypeExpanded = false }
                ) {
                    jobTypes.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type) },
                            onClick = {
                                selectedJobType = type
                                jobTypeExpanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descrição") },
                placeholder = { Text("Detalhes do serviço, local, urgência...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                maxLines = 6
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = { navController.popBackStack() }) {
                    Text("Cancelar")
                }
                Spacer(modifier = Modifier.width(12.dp))
                Button(onClick = { navController.popBackStack() }) {
                    Text("Publicar")
                }
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 900)
@Composable
fun CreateServiceScreenPreview() {
    val navController = rememberNavController()
    CreateServiceScreen(navController = navController)
}
