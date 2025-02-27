package com.example.todotask.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.todotask.data.DataSource
import com.example.todotask.data.MyCheckList
import com.example.todotask.data.MyCheckListElements

class CheckListViewModel : ViewModel() {
    var checkLists = mutableStateOf(DataSource.loadDemoToDoList())
        private set

    fun toggleTask(listIndex: Int, taskIndex: Int) {
        val updatedLists = checkLists.value.toMutableList()
        val updatedTasks = updatedLists[listIndex].items.toMutableList()

        updatedTasks[taskIndex as Int] =
            updatedTasks[taskIndex].copy(checked = !updatedTasks[taskIndex].checked)
        updatedLists[listIndex] =
            updatedLists[listIndex].copy(items = updatedTasks)

        checkLists.value = updatedLists
    }

    fun deleteList(indexList: Int) {
        val updatedLists = checkLists.value.toMutableList()
        updatedLists.removeAt(indexList)
        checkLists.value = updatedLists
    }

    fun deleteListItem(indexList: Int, indexItem: Int) {
        val updatedLists = checkLists.value.toMutableList()
        val updatedItems = updatedLists[indexList].items.toMutableList()
        updatedItems.removeAt(indexItem)
        updatedLists[indexList] = updatedLists[indexList].copy(items = updatedItems)
        checkLists.value = updatedLists
    }

    fun addList(icon: Int, newName: String) {
        val newList = MyCheckList(
            name = newName,
            icon = icon,
            items = listOf(MyCheckListElements("Ny oppgave")))
        checkLists.value += newList
    }

    fun addListItem(indexList: Int, newText: String) {
        val newItem = MyCheckListElements(text = newText)
        val updatedList = checkLists.value.toMutableList()
        val updatedItems = updatedList[indexList].items.toMutableList()
        updatedItems += newItem

        updatedList[indexList] = updatedList[indexList].copy(items = updatedItems)
        checkLists.value = updatedList
    }

    fun renameList(indexList: Int, newName: String) {
        val updatedList = checkLists.value.toMutableList()
        updatedList[indexList] = updatedList[indexList].copy(name = newName)
        checkLists.value = updatedList
    }

    fun renameItemList(indexList: Int, itemIndex: Int, newText: String) {
        val updatedList = checkLists.value.toMutableList()
        val updatedItems = updatedList[indexList].items.toMutableList()

        updatedItems[itemIndex] = updatedItems[itemIndex].copy(text = newText)
        updatedList[indexList] = updatedList[indexList].copy(items = updatedItems)
        checkLists.value = updatedList
    }}

