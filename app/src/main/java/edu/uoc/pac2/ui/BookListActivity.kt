package edu.uoc.pac2.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import edu.uoc.pac2.R
import edu.uoc.pac2.data.Book

/**
 * An activity representing a list of Books.
 */
class BookListActivity : AppCompatActivity()
{

    private val TAG = "BookListActivity"
    val db = FirebaseFirestore.getInstance()
    private lateinit var adapter: BooksListAdapter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        // Init UI
        initToolbar()
        initRecyclerView()

        // Get Books
        getBooks()

    }

    // Init Top Toolbar
    private fun initToolbar()
    {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title
    }

    // Init RecyclerView
    private fun initRecyclerView()
    {
        val recyclerView = findViewById<RecyclerView>(R.id.book_list)
        // Set Layout Manager
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        // Init Adapter
        adapter = BooksListAdapter(emptyList())
        recyclerView.adapter = adapter
    }

    // TODO: Get Books and Update UI
    private fun getBooks()
    {
        val booksCollection = db.collection("books")

        Log.i(TAG, "Dentro de get books")
        booksCollection.addSnapshotListener { querySnapshot, e ->
            if (e != null)
            {
                Log.i(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            val books: List<Book> = querySnapshot!!.documents.mapNotNull { it.toObject(Book::class.java) }
            adapter.setBooks(books)
        }
    }

    // TODO: Load Books from Room
    private fun loadBooksFromLocalDb()
    {
        throw NotImplementedError()
    }

    // TODO: Save Books to Local Storage
    private fun saveBooksToLocalDatabase(books: List<Book>)
    {
        throw NotImplementedError()
    }
}