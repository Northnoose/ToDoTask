package com.example.todotask.UiScreen

import com.example.todotask.data.MyCheckList

class CheckListItem(
    val checkList: MyCheckList,
    val onDelete: () -> Unit,
    val onToggle: (Any) -> Unit
) {
}