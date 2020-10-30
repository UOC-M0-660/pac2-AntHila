package edu.uoc.pac2.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.uoc.pac2.R

/**
 * An activity representing a single Book detail screen.
 */
class BookDetailActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)
        configureEnterTransition()
        setSupportActionBar(findViewById(R.id.detail_toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener{ view ->
            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                val bookInformation =   "Title: " + BookDetailFragment.bookShare.title +"\n"+
                                        "Url image: " + BookDetailFragment.bookShare.urlImage
                putExtra(Intent.EXTRA_TEXT, bookInformation)
                type = "text/plain"}

            val shareActivity = Intent.createChooser(shareIntent, null)
            startActivity(shareActivity)
        }

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null)
        {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val itemID = intent.getIntExtra(BookDetailFragment.ARG_ITEM_ID, -1)
            val fragment = BookDetailFragment.newInstance(itemID)
            supportFragmentManager.beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit()
        }
    }

    // TODO: Override finish animation for actionbar back arrow
    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        super.onOptionsItemSelected(item)
        NavUtils.navigateUpTo(this, Intent(this, BookListActivity::class.java))
        configureExitTransition()
        return true
    }


    // TODO: Override finish animation for phone back button
    override fun onBackPressed()
    {
        super.onBackPressed()
        configureExitTransition()
    }

    fun configureEnterTransition()
    {
        overridePendingTransition(R.anim.translate_in_top, R.anim.translate_out_bottom)
    }

    fun configureExitTransition()
    {
        overridePendingTransition(R.anim.translate_in_bottom, R.anim.translate_out_top)
    }


}