package com.example.assignment_todolist.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment_todolist.data.DataProvider
import com.example.assignment_todolist.data.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListItem(
    task: Task,
    navTo: (Any) -> Unit,
    checkState: String,
    onCheckChange: (String) -> Unit
) {

    val isChecked = remember { mutableStateOf(false) }
    isChecked.value = task.done
    val checked = remember { mutableStateOf(false) }
    checked.value = task.important

    Card(
        onClick = { navTo(task.id) },
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Checkbox(
                    checked = isChecked.value,
                    onCheckedChange = {
                        isChecked.value = it
                        task.done = it
                        CoroutineScope(Dispatchers.IO).launch {
                            DataProvider.dao.update(task)
                        }
                        onCheckChange(it.toString() + 0)
                    },
                    enabled = true
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                task.title?.let {
                    Text(
                        text = it,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = if (isChecked.value) TextStyle(
                            fontSize = 16.sp,
                            textDecoration = TextDecoration.LineThrough
                        ) else TextStyle(fontSize = 16.sp, textDecoration = TextDecoration.None)
                    )
                }
                task.description?.let {
                    Text(
                        text = it,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Spacer(Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                IconToggleButton(
                    modifier = Modifier.then(Modifier.requiredSize(40.dp)),
                    checked = checked.value,
                    onCheckedChange = {
                        checked.value = it
                        task.important = it
                        CoroutineScope(Dispatchers.IO).launch {
                            DataProvider.dao.update(task)
                        }
                        onCheckChange(it.toString() + 1)
                    }) {
                    Icon(
                        imageVector = if (checked.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Important",
                    )
                }
            }
        }
    }
}