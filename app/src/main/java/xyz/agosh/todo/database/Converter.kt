package xyz.agosh.todo.database

import androidx.room.TypeConverter

class Converter {

    @TypeConverter
    fun fromType(type: Type): String{
        return type.name
    }

    @TypeConverter
    fun toType(type: String): Type {
        return Type.valueOf(type)
    }


}