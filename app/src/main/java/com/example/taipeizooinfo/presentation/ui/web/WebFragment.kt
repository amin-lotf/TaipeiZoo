package com.example.taipeizooinfo.presentation.ui.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taipeizooinfo.R
import com.example.taipeizooinfo.databinding.FragmentWebBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
@SuppressLint("SetJavaScriptEnabled")
class WebFragment : Fragment(R.layout.fragment_web) {

    private val args:WebFragmentArgs by navArgs()
    private var _binding:FragmentWebBinding?=null
    private val binding:FragmentWebBinding
    get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentWebBinding.bind(view)
        val link=args.link
        link?.let {
            binding.webView.apply {
                settings.javaScriptEnabled=true
                loadUrl(it)
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

}