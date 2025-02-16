package com.example.todotask.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.todotask.data.DataSource
import com.example.todotask.data.MyCheckList
import com.example.todotask.data.MyCheckListElements

class CheckListViewModel : ViewModel() {
    private val dataSource = DataSource()
    private var checkLists = mutableStateOf(dataSource.loadDemoToDoList())

    fun toggleTask(listIndex: Int, taskIndex: Int) {
        val updatedLists = checkLists.value.toMutableList()
        val updatedTasks = updatedLists[listIndex].items.toMutableList()

        updatedTasks[taskIndex] =
            updatedTasks[taskIndex].copy(checked = !updatedTasks[taskIndex].checked)

        checkLists.value = updatedLists
    }

    fun deleteList(index: Int) {
        val updatedLists = checkLists.value.toMutableList()
        updatedLists.removeAt(index)
        checkLists.value = updatedLists
    }

    fun addList(icon: Int) {
        val newList = MyCheckList(
            "Ny Oppgave",
            icon,
            listOf(MyCheckListElements("Ny oppgave")))
        checkLists.value += newList
    }
}