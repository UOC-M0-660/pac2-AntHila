package edu.uoc.pac2

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
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

        //Init Room Database
        Rdb = Room.databaseBuilder(applicationContext, ApplicationDatabase::class.java, "toni-database").build()


        //Init BooksInteractor
        booksInteractor = BooksInteractor(Rdb.bookDao())
    }

    fun getBooksInteractor(): BooksInteractor
    {
        return booksInteractor
    }

    fun hasInternetConnection(): Boolean
    {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        return isConnected
    }
}