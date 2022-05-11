package xyz.agosh.todo.database.repository

import androidx.lifecycle.LiveData
import xyz.agosh.todo.database.TodoDao
import xyz.agosh.todo.database.models.TodoData

class TodoRepository(private val todoDao: TodoDao) {
    val getAllData: LiveData<List<TodoData>> = todoDao.getAllData()


    suspend fun insertData(todoData: TodoData){
        todoDao.insertData(todoData)
    }

}