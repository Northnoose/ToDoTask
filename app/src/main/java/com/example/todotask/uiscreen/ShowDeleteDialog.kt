package com.example.todotask.uiscreen

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun ShowDeleteDialog(
    showDialog: Boolean,
    onConfirmDelete: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Bekreft sletting") },
            text = { Text("Er du sikker på at du vil slette dette elementet?") },
            confirmButton = {
                Button(onClick = onConfirmDelete) {
                    Text("Slett")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Avbryt")
                }
            }
        )
    }
}