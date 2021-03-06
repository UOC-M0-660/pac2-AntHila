package edu.uoc.pac2

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.Toast

class AdMobFragment : Fragment()
{

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_ad_mob, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        // Load an ad into the AdMob banner view.
        val adView: AdView = view.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().setRequestAgent("android_studio:ad_template").build()
        adView.loadAd(adRequest)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
        val appContext = activity?.applicationContext ?: return
        Toast.makeText(appContext, TOAST_TEXT, Toast.LENGTH_LONG).show()
    }

    companion object
    {
        // Remove the below line after defining your own ad unit ID.
        private const val TOAST_TEXT =
                "Test ads are being shown. " + "To show live ads, replace the ad unit ID in res/values/strings.xml with your own ad unit ID."
    }
}