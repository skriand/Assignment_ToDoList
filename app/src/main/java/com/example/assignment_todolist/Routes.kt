package com.example.assignment_todolist

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object ItemDetails : Routes("item_details")
    object AddItem : Routes("add_item")
}