package com.example.todotask.data

import androidx.annotation.DrawableRes
import com.example.todotask.R

data class IconOption(
    @DrawableRes val icon: Int,
    val text: String
)

object IconData {
    val availableIcons = listOf(
        IconOption(R.drawable.baseline_shopping_cart_24, "Handlekurv"),
        IconOption(R.drawable.baseline_work_24, "Jobb"),
        IconOption(R.drawable.baseline_brush_24, "Hobby"),
        IconOption(R.drawable.baseline_build_24, "Verktøy")
    )
}