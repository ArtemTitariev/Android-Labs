package com.example.lr8

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.lr8.models.ArtType
import com.example.lr8.models.Artwork
import com.example.lr8.models.Author

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "artworks_db"
        private const val TABLE_AUTHORS = "authors"
        private const val TABLE_ART_TYPES = "art_types"
        private const val TABLE_ARTWORKS = "artworks"
        private const val COLUMN_ID = "id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_AUTHORS_TABLE = ("CREATE TABLE $TABLE_AUTHORS " +
                "($COLUMN_ID INTEGER PRIMARY KEY, first_name TEXT, last_name TEXT, email TEXT)")
        db.execSQL(CREATE_AUTHORS_TABLE)

        val CREATE_ART_TYPES_TABLE = ("CREATE TABLE $TABLE_ART_TYPES " +
                "($COLUMN_ID INTEGER PRIMARY KEY, type TEXT)")
        db.execSQL(CREATE_ART_TYPES_TABLE)

        val CREATE_ARTWORKS_TABLE = ("CREATE TABLE $TABLE_ARTWORKS " +
                "($COLUMN_ID INTEGER PRIMARY KEY, title TEXT, genre TEXT, year INTEGER, description TEXT, " +
                "author_id INTEGER, type_id INTEGER, " +
                "FOREIGN KEY(author_id) REFERENCES $TABLE_AUTHORS($COLUMN_ID), " +
                "FOREIGN KEY(type_id) REFERENCES $TABLE_ART_TYPES($COLUMN_ID))")
        db.execSQL(CREATE_ARTWORKS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ARTWORKS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_AUTHORS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ART_TYPES")
        onCreate(db)
    }

    fun addAuthor(author: Author): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("first_name", author.firstName)
            put("last_name", author.lastName)
            put("email", author.email)
        }
        val id = db.insert(TABLE_AUTHORS, null, values)
        db.close()
        return id
    }

    fun addArtType(artType: ArtType): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("type", artType.type)
        }
        val id = db.insert(TABLE_ART_TYPES, null, values)
        db.close()
        return id
    }

    fun addArtwork(artwork: Artwork): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("title", artwork.title)
            put("genre", artwork.genre)
            put("year", artwork.year)
            put("description", artwork.description)
            put("author_id", artwork.authorId)
            put("type_id", artwork.typeId)
        }
        val id = db.insert(TABLE_ARTWORKS, null, values)
        db.close()
        return id
    }
}
