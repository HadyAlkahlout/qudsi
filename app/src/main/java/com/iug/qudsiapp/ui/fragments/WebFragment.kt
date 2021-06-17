package com.iug.qudsiapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import com.iug.qudsiapp.R
import com.iug.qudsiapp.databinding.FragmentWebBinding

class WebFragment : Fragment() {

    private lateinit var binding: FragmentWebBinding

    private var mWebViewClient: WebViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView, url: String) {
            view.loadUrl("javascript:window.android.onUrlChange(window.location.href);")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var url = ""
        if (arguments != null){
            val args = WebFragmentArgs.fromBundle(requireArguments())
            url = args.url
        }

        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }

        val webSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true
        binding.webView.webViewClient = mWebViewClient
        binding.webView.loadUrl(url)

    }

}