package edu.uoc.pac2.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.firebase.firestore.FirebaseFirestore
import edu.uoc.pac2.R
import edu.uoc.pac2.data.Book

/**
 * A fragment representing a single Book detail screen.
 * This fragment is contained in a [BookDetailActivity].
 */
class BookDetailFragment : Fragment()
{
    var bookUid:Int = 0
    lateinit var book :Book
    private val TAG = "BookDetailFragment"
    val Fdb = FirebaseFirestore.getInstance()
    var viewCentral : View? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //Recover the book uid from the BookListActivity
        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID))
            {
                bookUid = it.getInt(ARG_ITEM_ID)
                Log.i("BookDetailFragment","Book uid:"+bookUid.toString())
            }
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val rootView = inflater.inflate(R.layout.fragment_book_detail, container, false)
        viewCentral = rootView

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        // Get Book for this detail screen
        //loadBookRoom()
        loadBookFirestore(bookUid)
        //initUI(book)
    }


    // TODO: Get Book for the given {@param ARG_ITEM_ID} Book id
    private fun loadBookRoom()
    {
        //Com no em funciona la base de dades Room, per avançar en la practica torno a agafar la llista de llibres de Firestore.
    }

    private fun loadBookFirestore(uid:Int)
    {
        val booksCollection = Fdb.collection("books")

        booksCollection.addSnapshotListener { querySnapshot, e ->
            if (e != null)
            {
                Log.i(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            var books: List<Book> = emptyList()
            if (querySnapshot != null)
            {
                books = querySnapshot.documents.mapNotNull { it.toObject(Book::class.java) }
            }

            val selectedBook: List<Book> = books.filter { book -> book.uid == uid}
            book = selectedBook[0]
            Log.i(TAG, "The selected book is:" + book.title)
            initUI(book)
        }
    }

    // TODO: Init UI with book details
    private fun initUI(book: Book)
    {
        Log.i(TAG,"Dentro de initUI")

        //Toolbar
        activity?.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)?.title = book?.title

        //Author
        val author : TextView = viewCentral!!.findViewById<TextView>(R.id.author)
        author.text = book.author

        //Date
        val date : TextView = viewCentral!!.findViewById<TextView>(R.id.publication_date)
        date.text = book.publicationDate

        //Description
        val description : TextView = viewCentral!!.findViewById<TextView>(R.id.description)
        description.text = book.description

        //Cover
        val cover : ImageView = viewCentral!!.findViewById<ImageView>(R.id.book_cover)
        Glide.with(this)
                .load(book.urlImage)
                .fitCenter()
                .into(cover);

    }

    // TODO: Share Book Title and Image URL
    private fun shareContent(book: Book)
    {
        throw NotImplementedError()
    }

    companion object
    {
        /**
         * The fragment argument representing the item title that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "itemIdKey"

        fun newInstance(itemId: Int): BookDetailFragment
        {
            val fragment = BookDetailFragment()
            val arguments = Bundle()
            arguments.putInt(ARG_ITEM_ID, itemId)
            fragment.arguments = arguments
            return fragment
        }
    }
}