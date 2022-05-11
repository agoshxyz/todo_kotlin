package xyz.agosh.todo.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import xyz.agosh.todo.database.models.TodoData

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo_table ORDER BY  id")
    fun getAllData(): LiveData<List<TodoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(todoData: TodoData)

}