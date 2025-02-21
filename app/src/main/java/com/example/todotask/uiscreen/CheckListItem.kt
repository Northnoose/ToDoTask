package com.example.todotask.uiscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.todotask.data.MyCheckList
import com.example.todotask.R

enum class DialogType {
    LIST, ITEM
}

@Composable
fun CheckListItem(
    index: Int,
    checkList: MyCheckList,
    onDelete: () -> Unit,
    onToggle: (Int) -> Unit,
    onRename: (String) -> Unit,
    onEditItem: (Int, String) -> Unit,
    onDeleteItem: (Int) -> Unit,
    onAddItem: (String) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf(checkList.name) }
    var isExpanded by remember { mutableStateOf(false) }
    var editedItems by remember { mutableStateOf(checkList.items.map {it.text}) }
    var itemToDelete by remember { mutableStateOf(-1) }
    var dialogType by remember { mutableStateOf<DialogType?>(null) }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(isEditing) {
                    TextField(
                        value = newName,
                        onValueChange = { newName = it },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    Text(
                        text = checkList.name,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.weight(1f)
                    )
                }
                Button(onClick = {
                        if (isEditing) {
                            onRename(newName)
                            editedItems.forEachIndexed { itemIndex, newText ->
                                onEditItem(itemIndex, newText)
                            }
                            isEditing = false
                        } else {
                            isEditing = true
                        }
                    }) {
                        Text(if(isEditing) "Lagre" else "Rediger")
                    }

                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_arrow_back_ios_new_24),
                        contentDescription = "Expand/Collapse",
                        modifier = Modifier.rotate(if (isExpanded) 90f else 270f)
                    )
                }
            }

            if (isExpanded) {
                checkList.items.forEachIndexed { itemIndex, task ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if(isEditing) {
                            TextField(
                                value = editedItems[itemIndex],
                                onValueChange = {newText ->
                                    editedItems = editedItems.toMutableList().apply { set(itemIndex,newText) }
                                },
                                singleLine = true,
                                textStyle = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.weight(1f)
                            )
                        } else{
                            Text(
                                text = task.text,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        IconButton(onClick = {
                            itemToDelete = itemIndex
                            dialogType = DialogType.ITEM
                        }) {
                            Icon(
                                ImageVector.vectorResource(R.drawable.baseline_close_24),
                                contentDescription = "DeleteItem"
                            )
                        }
                        Checkbox(
                            checked = task.checked,
                            onCheckedChange = { onToggle(itemIndex) }
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Button(
                    onClick = { dialogType = DialogType.LIST},
                    modifier = Modifier.padding(8.dp).weight(1f)
                ) {
                    Text("Slett liste")
                }

                IconButton(onClick = { onAddItem("Ny Oppgave") }) {
                    Icon(
                        ImageVector.vectorResource(R.drawable.baseline_add_circle_24),
                        contentDescription = "AddItem"
                    )
                }
            }
        }
    }

    if(dialogType != null) {
        ShowDeleteDialog(
            showDialog = true,
            onConfirmDelete = {
                when(dialogType) {
                    DialogType.LIST -> onDelete()
                    DialogType.ITEM -> onDeleteItem(itemToDelete)
                    null -> {} }
                dialogType = null
            },
            onDismiss = {dialogType = null}
            )
    }
}


