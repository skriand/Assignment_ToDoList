package com.example.assignment_todolist.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.example.assignment_todolist.data.DataProvider
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.runtime.setValue
import com.example.assignment_todolist.R
import com.example.assignment_todolist.ui.TaskListItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navToAddItem: () -> Unit,
    checkState: String,
    onCheckChange: (String) -> Unit,
    navToItemDetails: (Any) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        rememberTopAppBarState()
    )

    var selectedItem by remember { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    when (selectedItem) {
                        1 -> Text(
                            text = stringResource(R.string.important_title),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        else -> Text(
                            text = stringResource(R.string.home),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = { navToAddItem() }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = stringResource(id = R.string.add_task)
                        )
                    }
                }
            )
        },
        bottomBar = {
            val items =
                listOf(stringResource(id = R.string.all), stringResource(id = R.string.important))
            val icons = listOf(Icons.Filled.List, Icons.Filled.Favorite)
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = item) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        enabled = when (index) {
                            1 -> {
                                DataProvider.taskList.any { it.important }
                            }

                            else -> true
                        }
                    )
                }
            }
        },
        content = { innerPadding ->
            val tasks =
                when (selectedItem) {
                    1 -> {
                        DataProvider.taskList.filter { it.important }
                    }

                    else -> {
                        DataProvider.taskList
                    }
                }

            LazyColumn(
                modifier = Modifier.padding(vertical = 10.dp),
                contentPadding = innerPadding
            ) {
                items(
                    items = tasks,
                    itemContent = {
                        TaskListItem(task = it, navToItemDetails, checkState, onCheckChange)
                    })
            }
        }
    )
}
