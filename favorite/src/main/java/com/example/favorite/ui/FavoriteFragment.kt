package com.example.favorite.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstone.detail.DetailActivity
import com.example.core.ui.ListDeveloperAdapter
import com.example.favorite.R
import com.example.favorite.databinding.FragmentFavoriteBinding
import com.example.favorite.di.favoriteModule
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules


class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private val favoriteViewModel: FavoriteVM by viewModel()
    private lateinit var favoriteAdapter: ListDeveloperAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = getString(com.example.capstone.R.string.favorite)
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)

        loadKoinModules(favoriteModule)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lottieAnimation.setAnimation("no-data.json")
        binding.lottieAnimation.playAnimation()

        favoriteAdapter = ListDeveloperAdapter(arrayListOf()) { developer ->
            val intent = Intent(activity, DetailActivity::class.java).also {
                it.putExtra(DetailActivity.EXTRA_USERNAME, developer.username)
                it.putExtra(DetailActivity.EXTRA_ID, developer.id)
            }
            startActivity(intent)
        }
        binding.rvFavorite.apply {
            adapter = favoriteAdapter
        }
        obserDetail()
    }

    private fun obserDetail() {
        favoriteViewModel.favoriteDevelopers.observe(viewLifecycleOwner) {
            it.let {
                if (!it.isNullOrEmpty()) {
                    favoriteAdapter.setListUser(it)
                    binding.lottieAnimation.visibility = View.GONE
                } else {
                    binding.rvFavorite.visibility = View.GONE
                    binding.lottieAnimation.visibility = View.VISIBLE
                }
            }
        }
    }
}