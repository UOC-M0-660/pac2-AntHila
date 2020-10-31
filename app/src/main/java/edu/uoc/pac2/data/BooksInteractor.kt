package edu.uoc.pac2.data

import android.util.Log

/**
 * This class Interacts with {@param bookDao} to perform operations in the local database.
 *
 * Could be extended also to interact with Firestore, acting as a single entry-point for every
 * book-related operation from all different datasources (Room & Firestore)
 *
 * Created by alex on 03/07/2020.
 */
class BooksInteractor(private val bookDao: BookDao)
{

    // TODO: Get All Books from DAO
    fun getAllBooks(): List<Book>
    {
        return bookDao.getAllBooks()
    }

    // TODO: Save Book
    fun saveBook(book: Book)
    {
        Log.i("BooksInteractor" , "Dentro de saveBook(), guardando el libro: "+book.title)
        bookDao.saveBook(book)
    }

    // TODO: Save List of Books
    fun saveBooks(books: List<Book>)
    {
        Log.i("BooksInteractor" , "Dentro de saveBooks()")
        books.forEach { saveBook(it) }
    }

    // TODO: Get Book by id
    fun getBookById(id: Int): Book?
    {
        return bookDao.getBookById(id)
    }

}