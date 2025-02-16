package com.example.todotask.data

import androidx.annotation.DrawableRes

data class MyCheckList(
    val name: String,
    @DrawableRes val icon: Int,
    val items: List<MyCheckListElements>
)