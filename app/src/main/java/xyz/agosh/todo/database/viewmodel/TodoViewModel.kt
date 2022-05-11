package xyz.agosh.todo.database.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.agosh.todo.database.TodoDatabase
import xyz.agosh.todo.database.models.TodoData
import xyz.agosh.todo.database.repository.TodoRepository

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val todoDao = TodoDatabase.getDatabase(application).todoDao()
    private val repository: TodoRepository

    private val getAllData: LiveData<List<TodoData>>

    init {
        repository = TodoRepository(todoDao)
        getAllData = repository.getAllData
    }

    fun insertData(todoData: TodoData){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertData(todoData)
        }
    }
}