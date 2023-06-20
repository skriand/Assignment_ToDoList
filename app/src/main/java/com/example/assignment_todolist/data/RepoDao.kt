package com.example.assignment_todolist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface RepoDao {
    @get:Query("SELECT * FROM tasks")
    val allRepos: List<Task?>?

    @Insert
    fun insert(vararg tasks: Task?)

    @Update
    fun update(vararg tasks: Task?)

    @Delete
    fun delete(vararg tasks: Task?)
}