package com.example.workly.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.workly.models.ServiceItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceDetailScreen(
    navController: NavController,
    service: ServiceItem
) {

    var proposalText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes do serviço") },
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
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Text(service.title, style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold))
            Text("${service.location} • ${service.postedAgo}", color = MaterialTheme.colorScheme.onSurfaceVariant)

            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large
            ) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Descrição", style = MaterialTheme.typography.titleMedium)
                    Text(service.description, style = MaterialTheme.typography.bodyLarge)

                    Text("Orçamento estimado", style = MaterialTheme.typography.titleMedium)
                    Text(service.estimatedValue, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold))

                    Text("Cliente", style = MaterialTheme.typography.titleMedium)
                    Text(service.clientName, style = MaterialTheme.typography.bodyLarge)
                }
            }

            OutlinedTextField(
                value = proposalText,
                onValueChange = { proposalText = it },
                label = { Text("Proposta") },
                placeholder = { Text("Descreva sua proposta e prazo") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                maxLines = 6
            )

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) {
                Text("Enviar proposta")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 900)
@Composable
fun ServiceDetailScreenPreview() {
    val navController = rememberNavController()
    ServiceDetailScreen(
        navController,
        ServiceItem(
            id = 1,
            title = "Instalação elétrica residencial",
            description = "Troca de fiação antiga e instalação de tomadas extras.",
            estimatedValue = "R$ 280,00",
            clientName = "Mariana Silva",
            postedAgo = "2h atrás",
            location = "Zona Sul"
        )
    )
}
