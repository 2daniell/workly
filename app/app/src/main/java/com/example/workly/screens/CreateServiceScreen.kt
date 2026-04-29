package com.example.workly.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateServiceScreen(navController: NavController) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var jobTypeExpanded by remember { mutableStateOf(false) }
    var selectedJobType by remember { mutableStateOf("Eletricista") } // valor inicial

    val jobTypes = listOf(
        "Eletricista",
        "Encanador",
        "Pintor",
        "Pedreiro",
        "Marido de aluguel",
        "Limpeza",
        "Outro"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Criar Serviço",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título do serviço") },
            placeholder = { Text("Ex: Troca de chuveiro") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            singleLine = true
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
                .fillMaxWidth(),
            maxLines = 5
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            OutlinedButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Text("Cancelar")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    // por enquanto só volta
                    navController.popBackStack()
                }
            ) {
                Text("Salvar")
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 700)
@Composable
fun CreateServiceScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        CreateServiceScreen(navController = navController)
    }
}