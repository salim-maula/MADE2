package com.example.capstone.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstone.R
import com.example.capstone.databinding.FragmentHomeBinding
import com.example.capstone.detail.DetailActivity
import com.example.core.data.source.Resource
import com.example.core.ui.ListDeveloperAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adaterDeveloper: ListDeveloperAdapter
    private val searchVM: SearchVM by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val customActionBar = (activity as AppCompatActivity).supportActionBar
        customActionBar?.title = getString(R.string.toolbar)
        setHasOptionsMenu(true)
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lottieAnimation.setAnimation("no-data.json")
        binding.lottieAnimation.playAnimation()

        adaterDeveloper = ListDeveloperAdapter(arrayListOf()) { developer ->
            val intent = Intent(activity, DetailActivity::class.java).also {
                it.putExtra(DetailActivity.EXTRA_USERNAME, developer.username)
                it.putExtra(DetailActivity.EXTRA_ID, developer.id)
            }
            startActivity(intent)
        }

        binding.rvListDeveloper.apply {
            layoutManager =LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adaterDeveloper
        }

        binding.searchData.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean {
                    searchVM.setSearch(query)
                    binding.searchData.clearFocus()
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }

            })
        }

        observeHome()
    }

    private fun observeHome() {
        searchVM.developers.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Resource.Success -> {
                        showLoading(false)
                        it.data?.let { data -> adaterDeveloper.setListUser(data) }
                        binding.rvListDeveloper.visibility = View.VISIBLE
                        binding.lottieAnimation.visibility = View.GONE
                        resources
                    }
                    is Resource.Loading -> {
                        showLoading(true)
                        binding.rvListDeveloper.visibility = View.GONE
                        binding.lottieAnimation.visibility = View.GONE
                    }
                    is Resource.Error -> {
                        binding.lottieAnimation.visibility = View.VISIBLE
                        showLoading(false)
                    } // sementara
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        binding.apply {
            if (state) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_toolbar, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                view?.findNavController()?.navigate(R.id.action_homeFragment_to_favoriteFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        binding.rvListDeveloper.adapter = null
        _binding = null
        super.onDestroyView()
    }

}