package com.example.todotask.uiscreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todotask.R
import com.example.todotask.data.MyCheckList

@Composable
fun CheckListHeaderWithIconTwoColumn(name: String, @androidx.annotation.DrawableRes iconRes: Int, modifier: Modifier = Modifier) {
    val inlineId = "listIcon"
    val annotatedText = buildAnnotatedString {
        appendInlineContent(inlineId, "[icon]")
        append(" ")
        append(name)
    }
    val inlineContent = mapOf(
        inlineId to InlineTextContent(
            Placeholder(
                width = 20.sp,
                height = 20.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            )
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "Ikon",
                tint = Color.Unspecified,
                modifier = Modifier.size(20.dp)
            )
        }
    )
    Text(
        text = annotatedText,
        inlineContent = inlineContent,
        style = MaterialTheme.typography.headlineSmall.copy(fontSize = 19.sp),
        modifier = modifier
    )
}

enum class DialogType2Col {
    LIST, ITEM
}

@Composable
fun CheckListItemTwoColumn(
    checkList: MyCheckList,
    onDelete: () -> Unit,
    onToggle: (Int) -> Unit,
    onRename: (String) -> Unit,
    onDeleteItem: (Int) -> Unit,
    onAddItem: (String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    val isEditing by remember { mutableStateOf(false) }
    var isEditingListName by remember { mutableStateOf(false) }
    var editedItems by remember { mutableStateOf(checkList.items.map { it.text }) }
    var itemToDelete by remember { mutableIntStateOf(-1) }
    var dialogType by remember { mutableStateOf<DialogType2Col?>(null) }
    var showNewItemDialog by remember { mutableStateOf(false) }
    var newItemName by remember { mutableStateOf("") }
    val totalItems = checkList.items.size
    val checkedItems = checkList.items.count { it.checked }
    val allChecked = (totalItems > 0 && checkedItems == totalItems)
    val bubbleColor by animateColorAsState(
        targetValue = if (allChecked) Color(0xFF4CAF50) else MaterialTheme.colorScheme.primary,
        animationSpec = tween(durationMillis = 500)
    )
    var newName by remember { mutableStateOf(checkList.name) }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isEditingListName) {
                    TextField(
                        value = newName,
                        onValueChange = { newName = it },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = {
                        onRename(newName)
                        isEditingListName = false
                    }) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Lagre listenavn"
                        )
                    }
                } else {
                    CheckListHeaderWithIconTwoColumn(
                        name = newName.ifBlank { stringResource(R.string.dialog_gi_navn_til_sjekkliste) },
                        iconRes = checkList.icon,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { isEditingListName = true }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Rediger listenavn"
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clip(CircleShape)
                        .background(bubbleColor)
                        .size(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (allChecked) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Alle oppgaver fullført",
                            tint = Color.White
                        )
                    } else {
                        Text("$checkedItems/$totalItems", color = Color.White)
                    }
                }
                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector = androidx.compose.ui.graphics.vector.ImageVector.vectorResource(
                            id = R.drawable.baseline_arrow_back_ios_new_24
                        ),
                        contentDescription = "Expand/Collapse",
                        modifier = Modifier.rotate(if (isExpanded) 90f else 270f)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { dialogType = DialogType2Col.LIST }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Slett liste",
                        modifier = Modifier.size(32.dp)
                    )
                }
                IconButton(onClick = { showNewItemDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Legg til oppgave",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            if (isExpanded) {
                checkList.items.forEachIndexed { indexItem, task ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 1.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isEditing) {
                            TextField(
                                value = editedItems[indexItem],
                                onValueChange = { newText ->
                                    editedItems = editedItems.toMutableList().apply {
                                        set(indexItem, newText)
                                    }
                                },
                                singleLine = true,
                                textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp),
                                modifier = Modifier.weight(1f)
                            )
                        } else {
                            Text(
                                text = task.text,
                                fontSize = 14.sp,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        IconButton(onClick = {
                            itemToDelete = indexItem
                            dialogType = DialogType2Col.ITEM
                        }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Slett oppgave"
                            )
                        }
                        Checkbox(
                            checked = task.checked,
                            onCheckedChange = { onToggle(indexItem) },
                            modifier = Modifier.testTag("TaskToggle")
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 1.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Knapp for å kryss av alle oppgaver
                    IconButton(
                        onClick = {
                            checkList.items.forEachIndexed { index, task ->
                                if (!task.checked) {
                                    onToggle(index)
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Kryss av alle oppgaver"
                        )
                    }
                }
            }
        }
    }

    if (dialogType != null) {
        AlertDialog(
            onDismissRequest = { dialogType = null },
            title = { Text(stringResource(R.string.dialog_bekreft_sletting)) },
            text = { Text(stringResource(R.string.dialog_sikker_sletting)) },
            confirmButton = {
                Button(onClick = {
                    when (dialogType) {
                        DialogType2Col.LIST -> onDelete()
                        DialogType2Col.ITEM -> onDeleteItem(itemToDelete)
                        null -> {}
                    }
                    dialogType = null
                }) {
                    Text(stringResource(R.string.btn_slett))
                }
            },
            dismissButton = {
                Button(onClick = { dialogType = null }) {
                    Text(stringResource(R.string.btn_avbryt))
                }
            }
        )
    }

    if (showNewItemDialog) {
        AlertDialog(
            onDismissRequest = { showNewItemDialog = false },
            title = { Text(stringResource(R.string.dialog_ny_oppgave)) },
            text = {
                TextField(
                    value = newItemName,
                    onValueChange = { newItemName = it },
                    singleLine = true,
                    label = { Text(stringResource(R.string.dialog_oppgavenavn)) }
                )
            },
            confirmButton = {
                Button(onClick = {
                    onAddItem(newItemName)
                    newItemName = ""
                    showNewItemDialog = false
                }) {
                    Text(stringResource(R.string.btn_ok))
                }
            },
            dismissButton = {
                Button(onClick = { showNewItemDialog = false }) {
                    Text(stringResource(R.string.btn_avbryt))
                }
            }
        )
    }
}
