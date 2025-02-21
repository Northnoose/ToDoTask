package com.example.todotask.uiscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todotask.viewmodel.CheckListViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckListScreen(
    modifier: Modifier = Modifier,
    viewModel: CheckListViewModel = viewModel()) {
    Column(modifier = modifier){
        var showIconPicker by remember { mutableStateOf(false) }
        TopAppBar(
            title = {
                Row(modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically){
                    Text(
                        "ToDo Huskelister",
                        style = MaterialTheme.typography.headlineMedium, // 🔹 Større og fet skrift
                        modifier = Modifier.padding(start = 16.dp).weight(1f)
                    )
                    Button(onClick = { showIconPicker = true }) {
                        Text("Ny Liste")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            )


        if(showIconPicker) {
            IconPickerDialog(
                onIconSelected = { selectedIcon ->
                    viewModel.addList(selectedIcon, "Ny Liste")
                    showIconPicker = false
                },
                onDismiss = { showIconPicker = false }
            )
        }

        LazyColumn {
            itemsIndexed(viewModel.checkLists.value) { index, checkList ->
                CheckListItem(
                    checkList = checkList,
                    onDelete = { viewModel.deleteList(index) },
                    onToggle = { taskIndex -> viewModel.toggleTask(index, taskIndex)},
                    onRename = { newName -> viewModel.renameList(index, newName) },
                    onEditItem = {indexItem, newText -> viewModel.renameItemList(index, indexItem, newText)},
                    onDeleteItem = { indexItem -> viewModel.deleteListItem(index, indexItem) },
                    onAddItem = { newText -> viewModel.addListItem(index, newText)}
                )
            }
        }
    }
}