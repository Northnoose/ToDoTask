package com.example.todotask.uiscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.todotask.viewmodel.CheckListViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckListScreen(
    modifier: Modifier = Modifier,
    viewModel: CheckListViewModel = viewModel()) {
    Column(modifier = modifier){
        TopAppBar(title = { Text("ToDo Huskelister") })

        var showIconPicker by remember { mutableStateOf(false) }

        Button(onClick = { showIconPicker = true }){
            Text("Ny Liste")
        }

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
                    index = index,
                    checkList = checkList,
                    onDelete = { viewModel.deleteList(index) },
                    onToggle = { taskIndex -> viewModel.toggleTask(index, taskIndex)},
                    onRename = { newName -> viewModel.renameList(index, newName.toString()) },
                    onEditItem = {indexItem, newText -> viewModel.renameItemList(index, indexItem, newText)},
                    onDeleteItem = { indexItem -> viewModel.deleteListItem(index, indexItem) },
                    onAddItem = { newText -> viewModel.addListItem(index, newText)}
                )
            }
        }
    }
}