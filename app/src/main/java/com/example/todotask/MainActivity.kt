package com.example.todotask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todotask.ui.theme.ToDoTaskTheme
import com.example.todotask.uiscreen.CheckListScreen
import com.example.todotask.viewmodel.CheckListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoTaskTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: CheckListViewModel = viewModel()) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        CheckListScreen(
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ToDoTaskTheme {
        MainScreen()
    }
}
