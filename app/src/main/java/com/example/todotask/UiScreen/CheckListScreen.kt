package com.example.todotask.UiScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.todotask.viewmodel.CheckListViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckListScreen(viewModel: CheckListViewModel = viewModel()) {
    Column{
        TopAppBar(title = { Text("ToDo Huskelister") })

        var showIconPicker by remember { mutableStateOf(false) }

        Button(onClick = { showIconPicker = true }){
            Text("Ny Liste")
        }

        if(showIconPicker) {
            TODO()
        }

        LazyColumn {
            items(viewModel.checkLists.value) {checkList ->
                val index = viewModel.checkLists.value.indexOf(checkList)
                CheckListItem(
                    checkList = viewModel.checkLists.value[index],
                    onDelete = { viewModel.deleteList(index) },
                    onToggle = { taskIndex -> viewModel.toggleTask(index, taskIndex)}
                )
            }
        }
    }
}