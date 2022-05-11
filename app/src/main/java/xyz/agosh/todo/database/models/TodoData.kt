package xyz.agosh.todo.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import xyz.agosh.todo.database.models.Type

@Entity(tableName = "todo_table")
data class TodoData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var type: Type,
    var description: String
)