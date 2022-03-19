package com.example.capstone.follower

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstone.databinding.FragmentFolloweBinding
import com.example.capstone.detail.DetailActivity
import com.example.core.data.source.Resource
import com.example.core.ui.ListDeveloperAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class FollowerFragment : Fragment() {

    private var _binding: FragmentFolloweBinding? = null
    private val binding get() = _binding!!
    private val followerViewModel: FollowerVM by viewModel()
    private lateinit var adapterFollower: ListDeveloperAdapter
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFolloweBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = arguments?.getString(DetailActivity.EXTRA_USERNAME).toString()
        adapterFollower = ListDeveloperAdapter(arrayListOf()) { developer ->
            val intent = Intent(activity, DetailActivity::class.java).also {
                it.putExtra(DetailActivity.EXTRA_USERNAME, developer.username)
                it.putExtra(DetailActivity.EXTRA_ID, developer.id)
            }
            startActivity(intent)
        }
        binding.rvFollower.apply {
//            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adapterFollower
        }
        showLoading(true)
        followerViewModel.setFollower(username)
        observerFollow()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun observerFollow() {
        followerViewModel.developerFollowers.observe(viewLifecycleOwner) {
            it.let {
                when (it) {
                    is Resource.Success ->
                        if (!it.data.isNullOrEmpty()) {
                            showLoading(false)
                            adapterFollower.run { setListUser(it.data) }
                        } else {
                            showLoading(false)
                        }
                    is Resource.Loading -> showLoading(true)
                    is Resource.Error -> showLoading((false))
                }
            }
        }
    }

    private fun showLoading(b: Boolean) {
        binding.apply {
            if (b) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }
}