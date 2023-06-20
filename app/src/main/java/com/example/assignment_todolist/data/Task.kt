package com.example.assignment_todolist.data

/*data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val done: Boolean,
)*/
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

// tableName is your table name
@Entity(tableName = "tasks")
class Task constructor() {
    @field:PrimaryKey(autoGenerate = true)
    var id: Int = 0

    /*@field:ColumnInfo(name = "task_id")
    var taskId: Int = 0*/

    @field:ColumnInfo(name = "title")
    var title: String? = null

    @field:ColumnInfo(name = "description")
    var description: String? = null

    @field:ColumnInfo(name = "done")
    var done: Boolean = false

    @field:ColumnInfo(name = "important")
    var important: Boolean = false

    @Ignore
    constructor(title: String, description: String) : this() {
        this.title = title
        this.description = description
    }

}

