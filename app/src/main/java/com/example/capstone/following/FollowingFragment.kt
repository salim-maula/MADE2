package com.example.capstone.following

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstone.databinding.FragmentFollowingBinding
import com.example.capstone.detail.DetailActivity
import com.example.core.data.source.Resource
import com.example.core.ui.ListDeveloperAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private val followingViewModel: FollowingVM by viewModel()
    private lateinit var adapterFollowing: ListDeveloperAdapter
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = arguments?.getString(DetailActivity.EXTRA_USERNAME).toString()

        adapterFollowing = ListDeveloperAdapter(arrayListOf()) { developer ->
            val intent = Intent(activity, DetailActivity::class.java).also {
                it.putExtra(DetailActivity.EXTRA_USERNAME, developer.username)
                it.putExtra(DetailActivity.EXTRA_ID, developer.id)
            }
            startActivity(intent)
        }

        binding.rvFollowing.apply {
//            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adapterFollowing
        }

        showLoading(true)

        followingViewModel.setFollowing(username)
        observeFollowing()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun observeFollowing() {
        followingViewModel.developerFollowing.observe(viewLifecycleOwner) {
            it.let {
                when (it) {
                    is Resource.Success ->
                        if (!it.data.isNullOrEmpty()) {
                            showLoading(false)
                            adapterFollowing.run { setListUser(it.data) }
                        } else {
                            showLoading(false)
                        }
                    is Resource.Loading -> showLoading(true)
                    is Resource.Error -> showLoading(false)
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