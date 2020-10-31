package edu.uoc.pac2.ui

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.firestore.FirebaseFirestore
import edu.uoc.pac2.MyApplication
import edu.uoc.pac2.R
import edu.uoc.pac2.data.Book
import edu.uoc.pac2.data.BooksInteractor

/**
 * An activity representing a list of Books.
 */
class BookListActivity : AppCompatActivity()
{

    private val TAG = "BookListActivity"
    val Fdb = FirebaseFirestore.getInstance()
    private lateinit var adapter: BooksListAdapter
    lateinit var AdView : AdView
    var books: List<Book> = emptyList()
    lateinit var booksInteractor : BooksInteractor

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        //Ads
        initAds()

        // Init UI
        initToolbar()
        initRecyclerView()

        booksInteractor= (application as MyApplication).getBooksInteractor()

        //Info from Room
        loadBooksFromLocalDatabase()

        //If internet connection is available the app contact with Firestore
        if((application as MyApplication).hasInternetConnection())
        {
            loadBooksFromFirestore()
        }

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

    private fun initAds()
    {
        MobileAds.initialize(this) {}
        AdView= findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().setRequestAgent("android_studio:ad_template").build()
        AdView.loadAd(adRequest)
    }

    private fun loadBooksFromFirestore()
    {
        Log.i(TAG, "Dentro de loadBooksFromFirestore")

        val booksCollection = Fdb.collection("books")
        booksCollection.addSnapshotListener { querySnapshot, e ->
            if (e != null)
            {
                Log.i(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (querySnapshot != null)
            {
                books = querySnapshot.documents.mapNotNull {it.toObject(Book::class.java) }
                refreshBooksList()
                saveBooksToLocalDatabase(books)
            }
        }
    }

    private fun loadBooksFromLocalDatabase()
    {
        Log.i(TAG, "Dentro de loadBooksFromLocalDatabase")
        Toast.makeText(this,"Dentro de loadBooksFromLocalDatabase",Toast.LENGTH_SHORT)

        AsyncTask.execute{
            books = booksInteractor.getAllBooks()
            val selectedBooks: List<Book> = books.filter { book -> book.uid == 2}
            Log.i(TAG, "Selected books: "+selectedBooks.toString())
            books=selectedBooks
            refreshBooksList()
        }
    }

    private fun saveBooksToLocalDatabase(books: List<Book>)
    {
        Log.i(TAG, "Dentro de saveBooksToLocalDatabase")

        AsyncTask.execute{
            booksInteractor.saveBooks(books)
        }
    }

    private fun refreshBooksList()
    {
        adapter.setBooks(books)
    }

}