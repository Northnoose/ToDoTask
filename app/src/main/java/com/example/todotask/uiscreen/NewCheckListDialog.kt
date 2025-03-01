package com.example.todotask.uiscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.todotask.R


@Composable
fun NewCheckListDialog(
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var checklistName by remember { mutableStateOf("") }
    val maxLength = 9

    AlertDialog(
        onDismissRequest = onDismiss,
        // Endret tittel her:
        title = { Text(stringResource(R.string.dialog_gi_navn_til_sjekkliste_2)) },
        text = {
            Column(modifier = Modifier.semantics(mergeDescendants = true) { }) {
                TextField(
                    value = checklistName,
                    onValueChange = { newValue ->
                        if (newValue.length <= maxLength) {
                            checklistName = newValue
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .semantics(mergeDescendants = true) { },
                    singleLine = true,
                    label = {
                        // Her beholdes "Gi navn til sjekkliste" slik at den redigerbare noden får med denne teksten
                        Text(stringResource(R.string.dialog_gi_navn_til_sjekkliste))
                    }
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                if (checklistName.isNotBlank()) {
                    onConfirm(checklistName)
                }
            }) {
                Text(stringResource(R.string.btn_ok))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(R.string.btn_avbryt))
            }
        }
    )}