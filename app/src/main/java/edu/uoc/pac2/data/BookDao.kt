package edu.uoc.pac2.data

import androidx.room.*

/**
 * Book Dao (Data Access Object) for accessing Book Table functions.
 */

@Dao
interface BookDao
{
    @Query("SELECT * FROM book")
    fun getAllBooks(): List<Book>

    @Query("SELECT * FROM book WHERE uid IN (:id)")
    fun getBookById(id: Int): Book?

    @Query("SELECT * FROM book WHERE titleR IN (:titleBook)")
    fun getBookByTitle(titleBook: String): Book?

    //@Insert(onConflict = OnConflictStrategy.ABORT)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveBook(book: Book): Long

    /*@Insert
    fun saveBook(book: Book):Long*/

    @Delete
    fun delete(book: Book)

    @Update
    fun update(book: Book)
}