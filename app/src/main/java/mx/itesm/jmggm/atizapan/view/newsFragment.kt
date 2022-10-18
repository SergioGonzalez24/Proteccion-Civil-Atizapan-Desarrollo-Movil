package mx.itesm.jmggm.atizapan.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import mx.itesm.jmggm.atizapan.R


class newsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_news,container,false)
        val  mWebView = view.findViewById(R.id.noticiasview) as WebView
        mWebView.loadUrl("https://www.milenio.com/temas/atizapan-de-zaragoza")

        val webSettings = mWebView.getSettings()
        webSettings.setJavaScriptEnabled(true)
        mWebView.setWebViewClient(WebViewClient())
        return view
    }

}