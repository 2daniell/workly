package com.example.workly.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ServiceCard(
    title: String,
    description: String,
    metadata: String,
    actionText: String,
    onActionClick: () -> Unit = {}
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(title, style = MaterialTheme.typography.titleLarge)
            Text(description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(metadata, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Button(onClick = onActionClick) {
                    Text(actionText)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ServiceCardPreview() {
    ServiceCard(
        title = "Conserto de pia",
        description = "Troca de canos na cozinha e revisão geral.",
        metadata = "R$ 180,00 • Vila Mariana",
        actionText = "Ver detalhes"
    )
}
