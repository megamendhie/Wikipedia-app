package com.sqube.wikipedia.repositories

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import org.jetbrains.anko.db.*

class ArticleDatabaseOpenhelper (context : Context): ManagedSQLiteOpenHelper(context, "ArticleDatabase.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        //define the tables in the database

        db?.createTable("Favourites", true,
            "id" to INTEGER + PRIMARY_KEY,
            "title" to TEXT,
            "url" to TEXT,
            "thumbnailJson" to TEXT)

        db?.createTable("History", true,
            "id" to INTEGER + PRIMARY_KEY,
            "title" to TEXT,
            "url" to TEXT,
            "thumbnailJson" to TEXT)

        Log.i("HelperClass", "successful")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //used to update the schema of the table if needed
    }

}