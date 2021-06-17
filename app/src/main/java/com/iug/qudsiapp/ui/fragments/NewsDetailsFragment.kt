package com.iug.qudsiapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.iug.qudsiapp.R
import com.iug.qudsiapp.databinding.FragmentNewsDetailsBinding
import com.iug.qudsiapp.model.api.Article
import com.iug.qudsiapp.model.local_storage.News
import com.iug.qudsiapp.ui.view_models.LocalNewsViewModel
import java.util.*
import kotlin.collections.ArrayList

class NewsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailsBinding
    private lateinit var article: Article
    private val viewModel by lazy {
        ViewModelProvider(this)[LocalNewsViewModel::class.java]
    }
    private var isFav = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null){
            val args = NewsDetailsFragmentArgs.fromBundle(requireArguments())
            binding.news = args.news
            article = args.news
        }

        viewModel.dataNews.observe(viewLifecycleOwner,
            { response ->
                if (response.isNotEmpty()){
                    for (i in response){
                        if (i.title == article.title){
                            isFav = true
                            binding.imgFav.setImageResource(R.drawable.ic_star_fill)
                        }
                    }
                }
            })

        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.tvClickHere.setOnClickListener {
            val action = NewsDetailsFragmentDirections.actionNewsDetailsFragmentToWebFragment(article.url)
            findNavController().navigate(action)
        }

        binding.imgFav.setOnClickListener {
            if (isFav){
                Snackbar.make(
                    requireView(),
                    "الخبر موجود بالفعل في مفضلتك!!",
                    Snackbar.LENGTH_SHORT
                ).show()
            }else {
                val uuid = UUID.randomUUID()
                val news = News(
                    uuid.toString(),
                    article.urlToImage,
                    article.title,
                    article.description
                )
                viewModel.addNews(news)
                binding.imgFav.setImageResource(R.drawable.ic_star_fill)
                Snackbar.make(
                    requireView(),
                    "تم إضافة الخبر الى المفضلة بنجاح",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

}