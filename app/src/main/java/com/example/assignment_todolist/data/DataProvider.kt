package com.example.assignment_todolist.data

object DataProvider {
    val taskList = listOf(
        Task(
            id = 0,
            title = "Wash the dishes",
            description = "It's a hard task.",
            done = true
        ),
        Task(
            id = 1,
            title = "Clone this repo",
            description = "Not as easy as it might seem.",
            done = false
        ),
        Task(
            id = 2,
            title = "cd to the folder",
            description = "Is it worth mention?",
            done = false
        ),
        Task(
            id = 3,
            title = "npm install",
            description = "It's classical.",
            done = false
        ),
        Task(
            id = 4,
            title = "npm run dev",
            description = "I'm a developer.",
            done = false
        ),
    )
}