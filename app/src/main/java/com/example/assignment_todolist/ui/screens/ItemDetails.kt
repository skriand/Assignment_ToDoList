package com.example.assignment_todolist.ui.screens

import androidx.activity.compose.BackHandler
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

    val task = DataProvider.taskList.find { it.id == id!!.toInt() }
    val index = DataProvider.taskList.indexOfFirst{
            it.id == id!!.toInt()
    }
    val isChecked = remember {
        if (task != null) mutableStateOf(task.done)
        else mutableStateOf(false)
    }
    val checked = remember {
        if (task != null) mutableStateOf(task.important)
        else mutableStateOf(false)
    }
    BackHandler(enabled = true, onBack = {navController()})
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    task?.title?.let {
                        Text(
                            it,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = if (isChecked.value) TextStyle(
                                fontSize = 28.sp,
                                textDecoration = TextDecoration.LineThrough
                            ) else TextStyle(fontSize = 28.sp, textDecoration = TextDecoration.None)
                        )
                    }
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
                    IconButton(onClick = {
                        DataProvider.taskList.removeAt(index)
                        navController()
                    }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Checkbox(
                    checked = isChecked.value,
                    onCheckedChange = {
                        isChecked.value = it
                        task?.done = it
                                      },
                    enabled = true
                )
                IconToggleButton(
                    checked = checked.value,
                    onCheckedChange = {
                        checked.value = it
                        task?.important = it
                    }) {
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
                task?.description?.let { Text(text = it) }
            }
        }
    )
}