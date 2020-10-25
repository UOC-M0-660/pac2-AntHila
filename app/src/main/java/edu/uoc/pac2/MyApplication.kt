package edu.uoc.pac2

import android.app.Application
import androidx.room.Room
import edu.uoc.pac2.data.*
import edu.uoc.pac2.data.BookDao as BookDaoklp

/**
 * Entry point for the Application.
 */
class MyApplication : Application()
{

    private lateinit var booksInteractor: BooksInteractor
    lateinit var Rdb:ApplicationDatabase

    override fun onCreate()
    {
        super.onCreate()

        // TODO: Init Room Database
        Rdb = Room.databaseBuilder(applicationContext, ApplicationDatabase::class.java, "toni-database").build()


        // TODO: Init BooksInteractor
        booksInteractor = BooksInteractor(Rdb.bookDao())
    }

    fun getBooksInteractor(): BooksInteractor
    {
        return booksInteractor
    }

    fun hasInternetConnection(): Boolean
    {
        // TODO: Add Internet Check logic.
        return true
    }
}