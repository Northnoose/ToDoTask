package com.example.todotask.uiscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todotask.data.MyCheckList
import androidx.compose.ui.text.font.FontWeight


@Composable
fun CheckListItem(
    index: Int,
    checkList: MyCheckList,
    onDelete: () -> Unit,
    onToggle: (Int) -> Unit,
    onRename: (Int, Any?) -> Unit
) {
    var isEditing by remember { mutableStateOf(false)}
    var newName by remember { mutableStateOf(checkList.name) }

    if(isEditing) {
        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = newName,
                onValueChange = { newName = it },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
        Button(onClick = {
            onRename(index, newName)
            isEditing = false
        }) {
            Text("Lagre")
        }
        }} else {
            Text(
                text = checkList.name,
                style = MaterialTheme.typography.headlineSmall
            )
            Button(onClick = {isEditing = true}) {
                Text("Rediger")
            }
        }

    Column {
        checkList.items.forEachIndexed { index, task ->
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = task.text,
                        modifier = Modifier.weight(1f)
                    )
                    Checkbox(
                        checked = task.checked,
                        onCheckedChange = { onToggle(index) }
                    )
                }
            }
        }
    }
        Button(onClick = onDelete) {
            Text("Slett liste")
        }
    }

