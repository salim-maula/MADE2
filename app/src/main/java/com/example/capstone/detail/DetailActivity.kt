package com.example.capstone.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.capstone.R
import com.example.capstone.adapter.SectionPagerAdapter
import com.example.capstone.databinding.ActivityDetailBinding
import com.example.capstone.transform.ZoomOutPageTransformer
import com.example.core.data.source.Resource
import com.example.core.domain.model.Developer
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailVM: DetailVM by viewModel()
    private var isFavorite = false
    private lateinit var developer: Developer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        if (username != null){

            detailVM.getDetailDeveloper(username).observe(this) {
                when (it) {
                    is Resource.Success -> {
                        developer = it.data!!
                        binding.apply {
                            Glide.with(this@DetailActivity)
                                .load(developer.avatarUrl)
                                .apply(
                                    RequestOptions.circleCropTransform()
                                        .placeholder(R.drawable.ic_baseline_account_circle)
                                )
                                .into(img)

                            tvName.text = developer.name ?: getString(R.string.name)
                            tvUsername.text = developer.username
                            tvLocation.text = developer.location ?: "-"
                            tvFollwers.text = developer.followers.toString()
                            tvFollwing.text = developer.following.toString()
                            tvLocation.text = developer.company ?: "-"
                            tvRepos.text = developer.publicRepos.toString()
                        }

                        detailVM.checkDeveloper(id)?.observe(this@DetailActivity) { userLocal ->
                            isFavorite = userLocal.isFavorite == true
                            setStatusFavorite(isFavorite)
                        }

                        binding.btnFavorite.show()
                    }
                    is Resource.Error -> binding.btnFavorite.hide()
                    is Resource.Loading -> binding.btnFavorite.hide()
                }
                setStatusFavorite(isFavorite)
                binding.btnFavorite.setOnClickListener {
                    addOrRemoveFavorite()
                    setStatusFavorite(isFavorite)
                }
            }
        }

        val sectionsPagerAdapter = SectionPagerAdapter(this, bundle)
        binding.apply {
            viewPager.adapter = sectionsPagerAdapter
            viewPager.setPageTransformer(ZoomOutPageTransformer())
            TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
                tab.text = resources.getString(TITLE_TABS[position])
            }.attach()
        }
    }

    private fun addOrRemoveFavorite() {
        if (!isFavorite) {
            developer.isFavorite = !isFavorite
            detailVM.addToFavorite(
                developer.username,
                developer.id,
                developer.avatarUrl,
                developer.isFavorite
            )
            Toast.makeText(
                this@DetailActivity,
                getString(R.string.toast_add_favorite),
                Toast.LENGTH_SHORT
            ).show()
            isFavorite = !isFavorite
        } else {
            developer.isFavorite = !isFavorite
            detailVM.removeFromFavorite(developer)
            isFavorite = !isFavorite
            Toast.makeText(
                this@DetailActivity,
                getString(R.string.toast_remove_favorite),
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun setStatusFavorite(isFavorite: Boolean) {
        if (isFavorite) {
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite_white)
        } else {
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        private val TITLE_TABS = intArrayOf(R.string.followers, R.string.following)
    }
}