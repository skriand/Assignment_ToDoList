package com.example.assignment_todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.assignment_todolist.data.AppDatabase
import com.example.assignment_todolist.data.DataProvider.taskList
import com.example.assignment_todolist.data.Task
import com.example.assignment_todolist.ui.screens.AddItem
import com.example.assignment_todolist.ui.screens.Home
import com.example.assignment_todolist.ui.screens.ItemDetails
import com.example.assignment_todolist.ui.theme.Assignment_ToDoListTheme
import com.microsoft.device.dualscreen.twopanelayout.Screen
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayoutNav
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneMode
import com.microsoft.device.dualscreen.twopanelayout.twopanelayoutnav.composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            //AppDatabase.getDatabase(applicationContext)?.taskDao()?.insert(*taskList.toTypedArray());
            taskList =
                AppDatabase.getDatabase(applicationContext)?.taskDao()?.allRepos as MutableList<Task>
        }

        setContent {
            Assignment_ToDoListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")
                    //HomeContent()
                    ScreenMain()
                }
            }
        }
    }
}

@Composable
fun ScreenMain() {

    val navController = rememberNavController()

    TwoPaneLayoutNav(
        navController = navController,
        paneMode = TwoPaneMode.HorizontalSingle,
        singlePaneStartDestination = Routes.Home.route,
        pane1StartDestination = Routes.Home.route,
        pane2StartDestination = Routes.ItemDetails.route
    ) {

        // First route : Home
        composable(Routes.Home.route) {

            // Lay down the Home Composable
            // and pass the navController
            Home({ navController.navigateTo(Routes.AddItem.route, Screen.Pane2) }) { id ->
                navController.navigateTo(Routes.ItemDetails.route + "/${id}", Screen.Pane2)
            }
        }

        // Another Route : AddItem
        composable(Routes.AddItem.route) {
            // AddItem Screen
            AddItem { navController.navigateTo(Routes.ItemDetails.route, Screen.Pane2) }
        }

        composable(Routes.ItemDetails.route) {
            if (isSinglePane)
                navController.navigateTo(Routes.Home.route, Screen.Pane1)
            else
                EmptyScreen()
        }

        // Items Route, Notice the "/{id}" in last,
        // its the argument passed down from homeScreen
        composable(Routes.ItemDetails.route + "/{id}") { twoPaneBackStack ->

            // Extracting the argument
            val id = twoPaneBackStack.arguments?.getString("id")

            // Pass the extracted Counter
            ItemDetails(
                { navController.navigateTo(Routes.ItemDetails.route, Screen.Pane2) },
                id = id
            )
        }
    }
}

@Preview
@Composable
fun EmptyScreen() {
    Row(horizontalArrangement = Arrangement.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "",
                modifier = Modifier
                    .height(130.dp)
                    .width(100.dp)
                    .padding(12.dp)
            )
            Text(
                text = stringResource(id = R.string.empty_text),
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Assignment_ToDoListTheme {
        Greeting("Android")
    }
}