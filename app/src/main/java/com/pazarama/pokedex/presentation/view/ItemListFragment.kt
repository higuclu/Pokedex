package com.pazarama.pokedex.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pazarama.pokedex.R
import com.pazarama.pokedex.databinding.FragmentItemListBinding
import com.pazarama.pokedex.domain.model.item.PokemonItem
import com.pazarama.pokedex.domain.model.search.SearchType
import com.pazarama.pokedex.presentation.viewmodel.ItemFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemListFragment : Fragment(), PokemonItemRecyclerViewAdapter.Listener {
    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ItemFragmentViewModel
    private var pokemonItemAdapter = PokemonItemRecyclerViewAdapter(arrayListOf(), this)

    private var localPokemonItems: List<PokemonItem> = listOf()
    private var searchType: SearchType = SearchType.BY_NAME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[ItemFragmentViewModel::class.java]
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerView.layoutManager = layoutManager
        if (binding.topBarContainer.searchView.query.isNotEmpty()) {
            println(binding.topBarContainer.searchView.query)
        }
        binding.topBarContainer.buttonSearchFilter.setOnClickListener { showSearchFilterMenu() }
        binding.topBarContainer.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (!query.isNullOrBlank()) {
                    // Yapılacak işlemler
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    viewModel.searchInList(newText)
                }
                return true
            }
        })

        viewModel.loadData()
        observeLiveData()
        return view
    }

    private fun observeLiveData() {
        viewModel.pokemonItems.observe(viewLifecycleOwner) { pokemonItems ->
            binding.pokemonErrorText.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            pokemonItemAdapter = PokemonItemRecyclerViewAdapter(pokemonItems.data?.results?.let {
                ArrayList(it)
            } ?: arrayListOf(), this@ItemListFragment)
            binding.recyclerView.adapter = pokemonItemAdapter
            pokemonItems.data?.results?.let { localPokemonItems = it } ?: listOf<PokemonItem>()
        }

        viewModel.pokemonError.observe(viewLifecycleOwner, Observer { error ->
            error.data?.let {
                if (it) {
                    binding.pokemonErrorText.visibility = View.VISIBLE
                    binding.pokemonErrorText.text = error.message
                    binding.recyclerView.visibility = View.GONE
                } else {
                    binding.pokemonErrorText.visibility = View.GONE
                }
            }

        })

        viewModel.pokemonLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading.data?.let {
                if (it) {
                    binding.pokemonProgressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.pokemonErrorText.visibility = View.GONE
                } else {
                    binding.pokemonProgressBar.visibility = View.GONE
                }
            }

        })

        viewModel.searchType.observe(viewLifecycleOwner) {
            searchType = it
        }

        viewModel.pokemonSearchItems.observe(viewLifecycleOwner) { pokemonItems ->
            binding.pokemonErrorText.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            pokemonItemAdapter = PokemonItemRecyclerViewAdapter(pokemonItems.data?.results?.let {
                ArrayList(it)
            } ?: arrayListOf(), this@ItemListFragment)
            binding.recyclerView.adapter = pokemonItemAdapter
            pokemonItems.data?.results?.let { localPokemonItems = it } ?: listOf<PokemonItem>()
            viewModel.processSearchResults()
        }
    }

    override fun onItemClick(pokemonItem: PokemonItem) {
        val action =
            ItemListFragmentDirections.actionItemListFragmentToDetailFragment(pokemonItem.name,
                viewModel.pokemonSearchNames.value.toString()
            )
        findNavController().navigate(action)
    }

    private fun showSearchFilterMenu() {
        val popupMenu = PopupMenu(requireContext(), binding.topBarContainer.buttonSearchFilter)
        popupMenu.menuInflater.inflate(R.menu.menu_search_filter, popupMenu.menu)
        if (searchType != null) {
            popupMenu.menu.findItem(R.id.filterByName)?.isChecked =
                (searchType == SearchType.BY_NAME)
            popupMenu.menu.findItem(R.id.filterById)?.isChecked = (searchType == SearchType.BY_ID)
        }
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.filterByName -> {
                    if (!item.isChecked) {
                        item.isChecked = true
                        viewModel.onSearchByNameSelected()
                    }
                    true
                }

                R.id.filterById -> {
                    if (!item.isChecked) {
                        item.isChecked = true
                        viewModel.onSearchByIdSelected()
                    }
                    true
                }

                else -> false
            }
        }

        popupMenu.show()
    }

}