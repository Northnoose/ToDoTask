package com.example.todotask.data

data class MyCheckListElements(
    val text: String,
    val checked: Boolean = false,
    val amount: Int = 1
)