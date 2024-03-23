package com.example.lr8.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.lr8.models.ArtGenre
import com.example.lr8.models.Artwork
import com.example.lr8.models.Author

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "artworks_db.db"
        private const val TABLE_AUTHORS = "authors"
        private const val TABLE_ART_GENRES = "art_genres"
        private const val TABLE_ARTWORKS = "artworks"
        private const val COLUMN_ID = "id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_AUTHORS_TABLE = ("CREATE TABLE $TABLE_AUTHORS " +
                "($COLUMN_ID INTEGER PRIMARY KEY, first_name TEXT, last_name TEXT, email TEXT)")
        db.execSQL(CREATE_AUTHORS_TABLE)

        val CREATE_ART_GENRES_TABLE = ("CREATE TABLE $TABLE_ART_GENRES " +
                "($COLUMN_ID INTEGER PRIMARY KEY, genre TEXT)")
        db.execSQL(CREATE_ART_GENRES_TABLE)

        val CREATE_ARTWORKS_TABLE = ("CREATE TABLE $TABLE_ARTWORKS " +
                "($COLUMN_ID INTEGER PRIMARY KEY, title TEXT, year INTEGER, description TEXT, " +
                "author_id INTEGER, genre_id INTEGER, " +
                "FOREIGN KEY(author_id) REFERENCES $TABLE_AUTHORS($COLUMN_ID), " +
                "FOREIGN KEY(genre_id) REFERENCES $TABLE_ART_GENRES($COLUMN_ID))")
        db.execSQL(CREATE_ARTWORKS_TABLE)

        // Вставка тестових даних
        addTestArtists(db)
        addTestGenres(db)
        addTestArtworks(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ARTWORKS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_AUTHORS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ART_GENRES")
        onCreate(db)
    }

    fun updateArtwork(id: Int, title: String, description: String): Boolean {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.apply {
            put("title", title)
            put("description", description)
        }
        val updated = db.update(TABLE_ARTWORKS, contentValues, "$COLUMN_ID = ?", arrayOf(id.toString()))
        return updated > 0
    }

    fun deleteArtwork(id: Int): Boolean {
        val db = this.writableDatabase

        val deleted = db.delete(TABLE_ARTWORKS, "$COLUMN_ID = ?", arrayOf(id.toString()))
        return deleted > 0
    }

    @SuppressLint("Range")
    fun getAuthorById(authorId: Int): Author? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_AUTHORS WHERE $COLUMN_ID = ?"
        val selectionArgs = arrayOf(authorId.toString())

        val cursor = db.rawQuery(query, selectionArgs)
        cursor.use {
            if (it.moveToFirst()) {
                val id = it.getInt(it.getColumnIndex("id"))
                val firstName = it.getString(it.getColumnIndex("first_name"))
                val lastName = it.getString(it.getColumnIndex("last_name"))
                val email = it.getString(it.getColumnIndex("email"))
                return Author(id, firstName, lastName, email)
            }
        }
        return null
    }

    @SuppressLint("Range")
    fun getArtGenreById(genreId: Int): ArtGenre? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_ART_GENRES WHERE $COLUMN_ID = ?"
        val selectionArgs = arrayOf(genreId.toString())

        val cursor = db.rawQuery(query, selectionArgs)
        cursor.use {
            if (it.moveToFirst()) {
                val id = it.getInt(it.getColumnIndex("id"))
                val genre = it.getString(it.getColumnIndex("genre"))
                return ArtGenre(id, genre)
            }
        }
        return null
    }


    @SuppressLint("Range")
    fun getArtworkList(): List<Artwork> {
        val artworkList = mutableListOf<Artwork>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_ARTWORKS"

        val cursor = db.rawQuery(query, null)
        cursor.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndex(COLUMN_ID))
                val title = it.getString(it.getColumnIndex("title"))
                val year = it.getInt(it.getColumnIndex("year"))
                val description = it.getString(it.getColumnIndex("description"))

                // Отримання об'єкта Author за його id
                val author = getAuthorById(it.getInt(it.getColumnIndex("author_id")))

                // Отримання об'єкта ArtGenre за його id
                val genre = getArtGenreById(it.getInt(it.getColumnIndex("genre_id")))

                checkAuthorAndGenre(author, genre)

                val artwork = Artwork(id, title, year, description, author!!, genre!!)
                artworkList.add(artwork)
            }
        }
        return artworkList
    }

    @SuppressLint("Range")
    fun getArtwork(title: String, description: String): Artwork? {


        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_ARTWORKS " +
                "WHERE title = ? AND description = ? " +
                "LIMIT 1"
        val selectionArgs = arrayOf(title, description)

        val cursor = db.rawQuery(query, selectionArgs)
        cursor.use {
            while (it.moveToFirst()) {
                val id = it.getInt(it.getColumnIndex(COLUMN_ID))
                val title = it.getString(it.getColumnIndex("title"))
                val year = it.getInt(it.getColumnIndex("year"))
                val description = it.getString(it.getColumnIndex("description"))

                // Отримання об'єкта Author за його id
                val author = getAuthorById(it.getInt(it.getColumnIndex("author_id")))

                // Отримання об'єкта ArtGenre за його id
                val genre = getArtGenreById(it.getInt(it.getColumnIndex("genre_id")))

                checkAuthorAndGenre(author, genre)

                return Artwork(id, title, year, description, author!!, genre!!)
            }
        }
        return null
    }

    private fun checkAuthorAndGenre(
        author: Author?,
        genre: ArtGenre?
    ) {
        if (author == null || genre == null) {
            throw NullPointerException("Author or genre not found.")
        }
    }

    @SuppressLint("Range")
    fun getFilteredArtworkList(genreFilter: String, yearFilter: Int?): List<Artwork> {
        val filteredList = mutableListOf<Artwork>()
        val db = readableDatabase

        val selection = if (genreFilter.isNotEmpty() && yearFilter != null) {
            "genre LIKE ? AND year = ?"
        } else if (genreFilter.isNotEmpty()) {
            "genre LIKE ?"
        } else if (yearFilter != null) {
            "year = ?"
        } else {
            null
        }

        val selectionArgs = when {
            genreFilter.isNotEmpty() && yearFilter != null -> arrayOf("%$genreFilter%", yearFilter.toString())
            genreFilter.isNotEmpty() -> arrayOf("%$genreFilter%")
            yearFilter != null -> arrayOf(yearFilter.toString())
            else -> null
        }

        val query = "SELECT * FROM $TABLE_ARTWORKS " +
                "JOIN $TABLE_ART_GENRES ON $TABLE_ARTWORKS.genre_id = $TABLE_ART_GENRES.$COLUMN_ID " +
                "WHERE $selection"

        val cursor = db.rawQuery(query, selectionArgs)

        cursor.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndex(COLUMN_ID))
                val title = it.getString(it.getColumnIndex("title"))
                val year = it.getInt(it.getColumnIndex("year"))
                val description = it.getString(it.getColumnIndex("description"))
                val authorId = it.getInt(it.getColumnIndex("author_id"))
                val genreId = it.getInt(it.getColumnIndex("genre_id"))

                val author = getAuthorById(authorId)
                val genre = getArtGenreById(genreId)

                checkAuthorAndGenre(author, genre)

                val artwork = Artwork(id, title, year, description, author!!, genre!!)
                filteredList.add(artwork)
            }
        }

        return filteredList
    }




    // Тестові дані
    private fun addTestArtists(db: SQLiteDatabase?) {
        val artists = listOf(
            arrayOf("Vincent", "van Gogh", "vincent@example.com"),
            arrayOf("Pablo", "Picasso", "pablo@example.com"),
            arrayOf("Leonardo", "da Vinci", "leonardo@example.com")
        )

        val insertArtistStatement = db?.compileStatement(
            "INSERT INTO $TABLE_AUTHORS (first_name, last_name, email) VALUES (?, ?, ?)"
        )

        db?.beginTransaction()

        try {
            for (artist in artists) {
                insertArtistStatement?.bindString(1, artist[0])
                insertArtistStatement?.bindString(2, artist[1])
                insertArtistStatement?.bindString(3, artist[2])
                insertArtistStatement?.execute()
            }
            db?.setTransactionSuccessful()
        } catch (e: Exception) {
            Log.e("DB_Error", "Error inserting test artists: ${e.message}")
        } finally {
            db?.endTransaction()
            insertArtistStatement?.close()
        }
    }

    private fun addTestGenres(db: SQLiteDatabase?) {
        val genres = listOf(
            "Impressionism",
            "Cubism",
            "Renaissance"
        )

        val insertGenreStatement = db?.compileStatement(
            "INSERT INTO $TABLE_ART_GENRES (genre) VALUES (?)"
        )

        db?.beginTransaction()

        try {
            for (genre in genres) {
                insertGenreStatement?.bindString(1, genre)
                insertGenreStatement?.execute()
            }
            db?.setTransactionSuccessful()
        } catch (e: Exception) {
            Log.e("DB_Error", "Error inserting test genres: ${e.message}")
        } finally {
            db?.endTransaction()
            insertGenreStatement?.close()
        }
    }

    private fun addTestArtworks(db: SQLiteDatabase?) {
        val insertArtworkStatement = db?.compileStatement(
            "INSERT INTO $TABLE_ARTWORKS (title, year, description, author_id, genre_id) VALUES (?, ?, ?, ?, ?)"
        )

        db?.beginTransaction()

        try {
            // Artwork 1
            insertArtworkStatement?.bindString(1, "Starry Night")
            insertArtworkStatement?.bindString(2, "1889")
            insertArtworkStatement?.bindString(3, "A famous painting by Vincent van Gogh")
            insertArtworkStatement?.bindString(4, "1") // Author ID 1
            insertArtworkStatement?.bindString(5, "1") // Genre ID 1
            insertArtworkStatement?.execute()

            // Artwork 2
            insertArtworkStatement?.bindString(1, "Guernica")
            insertArtworkStatement?.bindString(2, "1937")
            insertArtworkStatement?.bindString(3, "A famous painting by Pablo Picasso")
            insertArtworkStatement?.bindString(4, "2") // Author ID 2
            insertArtworkStatement?.bindString(5, "2") // Genre ID 2
            insertArtworkStatement?.execute()

            // Artwork 3
            insertArtworkStatement?.bindString(1, "Mona Lisa")
            insertArtworkStatement?.bindString(2, "1503")
            insertArtworkStatement?.bindString(3, "A famous painting by Leonardo da Vinci")
            insertArtworkStatement?.bindString(4, "3") // Author ID 3
            insertArtworkStatement?.bindString(5, "3") // Genre ID 3
            insertArtworkStatement?.execute()

            // Artwork 4
            insertArtworkStatement?.bindString(1, "Sunflowers")
            insertArtworkStatement?.bindString(2, "1888")
            insertArtworkStatement?.bindString(3, "A famous painting by Vincent van Gogh")
            insertArtworkStatement?.bindString(4, "1") // Author ID 1
            insertArtworkStatement?.bindString(5, "1") // Genre ID 1
            insertArtworkStatement?.execute()

            // Artwork 5
            insertArtworkStatement?.bindString(1, "The Persistence of Memory")
            insertArtworkStatement?.bindString(2, "1931")
            insertArtworkStatement?.bindString(3, "A famous painting by Salvador Dalí")
            insertArtworkStatement?.bindString(4, "3") // Author ID 3
            insertArtworkStatement?.bindString(5, "1") // Genre ID 1
            insertArtworkStatement?.execute()

            // Artwork 6
            insertArtworkStatement?.bindString(1, "The Last Supper")
            insertArtworkStatement?.bindString(2, "1495")
            insertArtworkStatement?.bindString(3, "A famous painting by Leonardo da Vinci")
            insertArtworkStatement?.bindString(4, "3") // Author ID 3
            insertArtworkStatement?.bindString(5, "3") // Genre ID 3
            insertArtworkStatement?.execute()

            // Artwork 7
            insertArtworkStatement?.bindString(1, "Les Demoiselles d'Avignon")
            insertArtworkStatement?.bindString(2, "1907")
            insertArtworkStatement?.bindString(3, "A famous painting by Pablo Picasso")
            insertArtworkStatement?.bindString(4, "2") // Author ID 2
            insertArtworkStatement?.bindString(5, "2") // Genre ID 2
            insertArtworkStatement?.execute()

            // Artwork 8
            insertArtworkStatement?.bindString(1, "The Starry Night")
            insertArtworkStatement?.bindString(2, "1889")
            insertArtworkStatement?.bindString(3, "A famous painting by Vincent van Gogh")
            insertArtworkStatement?.bindString(4, "1") // Author ID 1
            insertArtworkStatement?.bindString(5, "1") // Genre ID 1
            insertArtworkStatement?.execute()

            // Artwork 9
            insertArtworkStatement?.bindString(1, "The Creation of Adam")
            insertArtworkStatement?.bindString(2, "1512")
            insertArtworkStatement?.bindString(3, "A famous painting by Michelangelo")
            insertArtworkStatement?.bindString(4, "1") // Author ID 1
            insertArtworkStatement?.bindString(5, "3") // Genre ID 3
            insertArtworkStatement?.execute()

            // Artwork 10
            insertArtworkStatement?.bindString(1, "The Scream")
            insertArtworkStatement?.bindString(2, "1893")
            insertArtworkStatement?.bindString(3, "A famous painting by Edvard Munch")
            insertArtworkStatement?.bindString(4, "2") // Author ID 2
            insertArtworkStatement?.bindString(5, "1") // Genre ID 1
            insertArtworkStatement?.execute()


            db?.setTransactionSuccessful()
        } catch (e: Exception) {
            Log.e("DB_Error", "Error inserting test artworks: ${e.message}")
        } finally {
            db?.endTransaction()
            insertArtworkStatement?.close()
        }
    }
}
