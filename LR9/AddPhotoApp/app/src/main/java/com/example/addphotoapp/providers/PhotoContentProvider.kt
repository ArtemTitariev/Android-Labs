package com.example.addphotoapp.providers

import DBHelper
import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.SQLException
import android.net.Uri

class PhotoContentProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.example.addphotoapp.photoprovider"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/photos")

        // Коди для відповідності URI
        private const val PHOTOS = 1
        private const val PHOTO_ID = 2

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, "photos", PHOTOS)
            uriMatcher.addURI(AUTHORITY, "photos/#", PHOTO_ID)
        }
    }

    private lateinit var dbHelper: DBHelper

    override fun onCreate(): Boolean {
        dbHelper = DBHelper(context!!)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val db = dbHelper.readableDatabase
        return when (uriMatcher.match(uri)) {
            PHOTOS -> db.query(
                DBHelper.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
            )

            PHOTO_ID -> {
                val id = uri.lastPathSegment ?: return null
                val querySelection = "${DBHelper.COLUMN_ID} = ?"
                val querySelectionArgs = arrayOf(id)
                db.query(
                    DBHelper.TABLE_NAME,
                    projection,
                    querySelection,
                    querySelectionArgs,
                    null,
                    null,
                    sortOrder
                )
            }

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            PHOTOS -> "vnd.android.cursor.dir/vnd.example.photos"
            PHOTO_ID -> "vnd.android.cursor.item/vnd.example.photos"
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbHelper.writableDatabase
        val rowId = db.insert(DBHelper.TABLE_NAME, null, values)
        if (rowId > 0) {
            val photoUri = Uri.withAppendedPath(CONTENT_URI, rowId.toString())
            context?.contentResolver?.notifyChange(photoUri, null)
            return photoUri
        }
        throw SQLException("Failed to insert row into $uri")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbHelper.writableDatabase
        val count: Int = when (uriMatcher.match(uri)) {
            PHOTOS -> db.delete(DBHelper.TABLE_NAME, selection, selectionArgs)
            PHOTO_ID -> {
                val id = uri.lastPathSegment ?: return 0
                val deleteSelection = "${DBHelper.COLUMN_ID} = ?"
                val deleteSelectionArgs = arrayOf(id)
                db.delete(DBHelper.TABLE_NAME, deleteSelection, deleteSelectionArgs)
            }

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
        context?.contentResolver?.notifyChange(uri, null)
        return count
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val db = dbHelper.writableDatabase
        val count: Int = when (uriMatcher.match(uri)) {
            PHOTOS -> db.update(DBHelper.TABLE_NAME, values, selection, selectionArgs)
            PHOTO_ID -> {
                val id = uri.lastPathSegment ?: return 0
                val updateSelection = "${DBHelper.COLUMN_ID} = ?"
                val updateSelectionArgs = arrayOf(id)
                db.update(DBHelper.TABLE_NAME, values, updateSelection, updateSelectionArgs)
            }

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
        context?.contentResolver?.notifyChange(uri, null)
        return count
    }
}