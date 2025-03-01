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
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todotask.R
import com.example.todotask.data.MyCheckList
import androidx.compose.ui.res.stringResource

// Ny composable for header med inline ikon
@Composable
fun CheckListHeaderWithIcon(name: String, @androidx.annotation.DrawableRes iconRes: Int, modifier: Modifier = Modifier) {
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
                contentDescription = stringResource(R.string.contentdescription_ikon),
                tint = Color.Unspecified,
                modifier = Modifier.size(20.dp)
            )
        }
    )
    Text(
        text = annotatedText,
        inlineContent = inlineContent,
        style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp),
        modifier = modifier
    )
}

enum class DialogType {
    LIST, ITEM
}

@Composable
fun CheckListItem(
    checkList: MyCheckList,
    onDelete: () -> Unit,
    onToggle: (Int) -> Unit,
    onRename: (String) -> Unit,
    onEditItem: (Int, String) -> Unit,
    onDeleteItem: (Int) -> Unit,
    onAddItem: (String) -> Unit
) {
    // Tilstander
    var isExpanded by remember { mutableStateOf(false) }
    var isEditingListName by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf(checkList.name) }
    var isEditingTasks by remember { mutableStateOf(false) }
    var editedItems by remember { mutableStateOf(checkList.items.map { it.text }) }
    var itemToDelete by remember { mutableIntStateOf(-1) }
    var dialogType by remember { mutableStateOf<DialogType?>(null) }
    var showNewItemDialog by remember { mutableStateOf(false) }
    var newItemName by remember { mutableStateOf("") }

    // teller
    val totalItems = checkList.items.size
    val checkedItems = checkList.items.count { it.checked }
    val allChecked = (totalItems > 0 && checkedItems == totalItems)

    // Animasjon for boble
    val bubbleColor by animateColorAsState(
        targetValue = if (allChecked) Color(0xFF4CAF50) else MaterialTheme.colorScheme.primary,
        animationSpec = tween(durationMillis = 500)
    )

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize()
    ) {
        Column {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
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
                            contentDescription = stringResource(R.string.btn_lagre)

                        )
                    }
                } else {
                    CheckListHeaderWithIcon(
                        name = checkList.name,
                        iconRes = checkList.icon,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { isEditingListName = true }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(R.string.btn_rediger)
                        )
                    }
                }

                // Boble med antall avkryssede oppgaver
                Box(
                    modifier = Modifier
                        .padding(horizontal = 13.dp)
                        .clip(CircleShape)
                        .background(bubbleColor)
                        .sizeIn(minWidth = 35.dp, minHeight = 35.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (allChecked) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(R.string.contentdescription_alle_oppgaver_avkrysset),
                            tint = Color.White
                        )
                    } else {
                        Text(
                            text = "$checkedItems/$totalItems",
                            color = Color.White
                        )
                    }
                }

                // Expand/Collapse-knapp
                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_arrow_back_ios_new_24),
                        contentDescription = stringResource(R.string.contentdescription_expand_collapse),
                        modifier = Modifier.rotate(if (isExpanded) 90f else 270f)
                    )
                }
            }

            // Oppgaveliste
            if (isExpanded) {
                checkList.items.forEachIndexed { itemIndex, task ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isEditingTasks) {
                            TextField(
                                value = editedItems[itemIndex],
                                onValueChange = { newText ->
                                    editedItems = editedItems.toMutableList().apply {
                                        set(itemIndex, newText)
                                    }
                                },
                                singleLine = true,
                                textStyle = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.weight(1f)
                            )
                        } else {
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
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.contentdescription_slett_oppgave)

                            )
                        }
                        Checkbox(
                            checked = task.checked,
                            onCheckedChange = { onToggle(itemIndex) }
                        )
                    }
                }
            }

            // Bunn-rad med slett, ny oppgave og rediger oppgaver
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { dialogType = DialogType.LIST },
                    modifier = Modifier
                        .padding(8.dp)
                        .size(30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.contentdescription_slett_liste),
                        modifier = Modifier.size(30.dp)
                    )
                }
                IconButton(
                    onClick = { showNewItemDialog = true },
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = stringResource(R.string.contentdescription_leg_til_oppgave),
                        modifier = Modifier.size(30.dp)
                    )
                }
                IconButton(
                    onClick = {
                        if (isEditingTasks) {
                            editedItems.forEachIndexed { i, newText ->
                                onEditItem(i, newText)
                            }
                            isEditingTasks = false
                        } else {
                            isEditingTasks = true
                        }
                    },
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(
                        imageVector = if (isEditingTasks) Icons.Default.Check else Icons.Default.Create,
                        contentDescription = if (isEditingTasks)
                            stringResource(R.string.contentdescription_lagre_oppgaver)
                        else
                            stringResource(R.string.contentdescription_rediger_oppgaver),
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }

    if (dialogType != null) {
        ShowDeleteDialog(
            showDialog = true,
            onConfirmDelete = {
                when (dialogType) {
                    DialogType.LIST -> onDelete()
                    DialogType.ITEM -> onDeleteItem(itemToDelete)
                    null -> {}
                }
                dialogType = null
            },
            onDismiss = { dialogType = null }
        )
    }

    if (showNewItemDialog) {
        AlertDialog(
            onDismissRequest = { showNewItemDialog = false },
            title = { Text("Ny oppgave") },
            text = {
                TextField(
                    value = newItemName,
                    onValueChange = { newItemName = it },
                    singleLine = true,
                    label = { Text("Oppgavenavn") }
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
