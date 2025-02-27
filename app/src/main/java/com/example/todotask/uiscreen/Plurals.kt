package com.example.todotask.uiscreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todotask.R

@Composable
fun ListCountText(listCount: Int) {
    Text(
        text = pluralStringResource(
            id = R.plurals.list_count,
            count = listCount,
            listCount
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 13.dp, horizontal = 16.dp),
        fontSize = 20.sp,
        textAlign = TextAlign.Center
    )
}
