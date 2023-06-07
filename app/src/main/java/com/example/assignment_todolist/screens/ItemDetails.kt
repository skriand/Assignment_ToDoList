package com.example.assignment_todolist.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment_todolist.R
import com.example.assignment_todolist.data.DataProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetails(navController: () -> Unit, id: String?) {
    val isChecked = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        DataProvider.taskList[id!!.toInt()].title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = if (isChecked.value) TextStyle(
                            fontSize = 28.sp,
                            textDecoration = TextDecoration.LineThrough
                        ) else TextStyle(fontSize = 28.sp, textDecoration = TextDecoration.None)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController() }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(id = R.string.close)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Checkbox(
                    checked = isChecked.value,
                    onCheckedChange = { isChecked.value = it },
                    enabled = true
                )
                val checked = remember { mutableStateOf(false) }
                IconToggleButton(
                    checked = checked.value,
                    onCheckedChange = { checked.value = it }) {
                    Icon(
                        imageVector = if (checked.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Important",
                    )
                }
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(text = DataProvider.taskList[id!!.toInt()].description)
            }
        }
    )
}