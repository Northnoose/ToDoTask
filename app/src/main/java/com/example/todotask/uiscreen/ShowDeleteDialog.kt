package com.example.todotask.uiscreen

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.todotask.R



@Composable
fun ShowDeleteDialog(
    showDialog: Boolean,
    onConfirmDelete: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(stringResource(R.string.dialog_bekreft_sletting)) },
            text = { Text(stringResource(R.string.dialog_sikker_sletting)) },
            confirmButton = { Button(onClick = { onConfirmDelete() }) { Text(stringResource(R.string.btn_slett)) } },
            dismissButton = { Button(onClick = { onDismiss() }) { Text(stringResource(R.string.btn_avbryt)) } }
        )
    }
}