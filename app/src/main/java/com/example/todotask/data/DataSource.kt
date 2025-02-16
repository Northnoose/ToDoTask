package com.example.todotask.data

import androidx.annotation.DrawableRes
import com.example.todotask.R

class DataSource(
    ) {
    fun loadDemoToDoList(): List<MyCheckList> {
        return listOf(
            MyCheckList(
                "Min ToDo Liste",
                icon = R.drawable.baseline_shopping_cart_24,
                items = listOf(
                            MyCheckListElements("Kjøp melk", false, 2),
                            MyCheckListElements("Kjøp brød", true, 5),
                            MyCheckListElements("Kjøp smør", false),
                            MyCheckListElements("Kjøp ost", true, 2),
                            MyCheckListElements("Kjøp skinke", false, 2),
                            MyCheckListElements("Kjøp syltetøy", true),
                            MyCheckListElements("Kjøp knekkebrød", false),
                            MyCheckListElements("Kjøp kaviar", true)
                        )
            )
        )
    }

}