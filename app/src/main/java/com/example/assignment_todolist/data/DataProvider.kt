package com.example.assignment_todolist.data

object DataProvider {
    var taskList = mutableListOf(
        Task(
            title = "Wash the dishes",
            description = "It's a hard task.",
        ),
        Task(
            title = "Clone this repo",
            description = "Not as easy as it might seem.",
        ),
        Task(
            title = "cd to the folder",
            description = "Is it worth mention?",
        ),
        Task(
            title = "npm install",
            description = "It's classical.",
        ),
        Task(
            title = "npm run dev",
            description = "I'm a developer.",
        ),
    )
}