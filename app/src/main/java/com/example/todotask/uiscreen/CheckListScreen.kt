package com.example.todotask.uiscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todotask.R
import com.example.todotask.viewmodel.CheckListViewModel

@Composable
fun CheckListScreen(
    modifier: Modifier = Modifier,
    viewModel: CheckListViewModel = viewModel()
) {
    val checkLists = viewModel.checkLists.value

    val totalTasks = checkLists.sumOf { it.items.size }
    val completedTasks = checkLists.sumOf { it.items.count { item -> item.checked } }

    var isTwoColumn by remember { mutableStateOf(false) }
    var showIconPicker by remember { mutableStateOf(false) }
    var showNewChecklistDialog by remember { mutableStateOf(false) }
    var selectedIcon by remember { mutableIntStateOf(com.example.todotask.R.drawable.baseline_shopping_cart_24) }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp) // Økt høyde for mer plass
                    .padding(top = 16.dp), // Skyver hele seksjonen opp
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.title_mine_huskelister),
                        fontSize = 26.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.text_vis_to_kolonner),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Switch(
                            checked = isTwoColumn,
                            onCheckedChange = { isTwoColumn = it },
                            modifier = Modifier.scale(0.9f)
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showIconPicker = true },
                modifier = Modifier.padding(16.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Legg til ny sjekkliste"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 8.dp)
        ) {
            // Viser antall oppgaver ferdig/fullført
            Text(
                text = stringResource(R.string.text_oppgaver_ferdig, completedTasks, totalTasks),
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )

            // Første dialog: Velg ikon
            if (showIconPicker) {
                IconPickerDialog(
                    onIconSelected = { icon ->
                        selectedIcon = icon
                        showIconPicker = false
                        showNewChecklistDialog = true // Etter ikonvalg vises navn-dialogen
                    },
                    onDismiss = { showIconPicker = false }
                )
            }

            // Andre dialog: Velg navn
            if (showNewChecklistDialog) {
                NewCheckListDialog(
                    onConfirm = { name ->
                        viewModel.addList(selectedIcon, name)
                        showNewChecklistDialog = false
                    },
                    onDismiss = { showNewChecklistDialog = false }
                )
            }

            // Viser sjekklister i enten én eller to kolonner
            if (!isTwoColumn) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    itemsIndexed(checkLists) { index, checkList ->
                        CheckListItem(
                            checkList = checkList,
                            onDelete = { viewModel.deleteList(index) },
                            onToggle = { taskIndex -> viewModel.toggleTask(index, taskIndex) },
                            onRename = { newName -> viewModel.renameList(index, newName) },
                            onEditItem = { idxItem, newText ->
                                viewModel.renameItemList(index, idxItem, newText)
                            },
                            onDeleteItem = { idxItem ->
                                viewModel.deleteListItem(index, idxItem)
                            },
                            onAddItem = { newText -> viewModel.addListItem(index, newText) }
                        )
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(checkLists) { index, checkList ->
                        CheckListItemTwoColumn(
                            checkList = checkList,
                            onDelete = { viewModel.deleteList(index) },
                            onToggle = { itemIndex -> viewModel.toggleTask(index, itemIndex) },
                            onRename = { newName -> viewModel.renameList(index, newName) },
                            onEditItem = { i, newText -> viewModel.renameItemList(index, i, newText) },
                            onDeleteItem = { i -> viewModel.deleteListItem(index, i) },
                            onAddItem = { newText -> viewModel.addListItem(index, newText) }
                        )
                    }
                }
            }
        }
    }
}
