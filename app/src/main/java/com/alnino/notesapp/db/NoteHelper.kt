package com.alnino.notesapp.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.alnino.notesapp.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.alnino.notesapp.db.DatabaseContract.NoteColumns.Companion._ID
import java.sql.SQLException

class NoteHelper(context: Context) {
    private val databaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: NoteHelper? = null
        fun getInstance(context: Context): NoteHelper {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: NoteHelper(context)
            }
        }
    }

    // Open and Closing connection to db
    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()

        if (database.isOpen) {
            database.close()
        }
    }

    // get all data (READ)
    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC"
        )
    }

    // get data with id (READ)
    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
        )
    }

    // Insert Data (CREATE)
    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    // Update Data (UPDATE)
    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID =?", arrayOf(id))
    }

    // Delete Data (DELETE)
    fun deleteById(id:String) : Int{
        return database.delete(DATABASE_TABLE,"$_ID = '$id'", null)
    }


}