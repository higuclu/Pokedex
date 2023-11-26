package com.pazarama.pokedex.presentation.view

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.pazarama.pokedex.R
import com.pazarama.pokedex.databinding.FragmentDetailBinding
import com.pazarama.pokedex.domain.model.detail.Move
import com.pazarama.pokedex.domain.model.detail.Stat
import com.pazarama.pokedex.domain.model.detail.Type
import com.pazarama.pokedex.presentation.viewmodel.DetailFragmentViewModel
import com.pazarama.pokedex.util.StatFormatter.formatStat
import com.pazarama.pokedex.util.UrlIdExtractor.formatId
import com.pazarama.pokedex.util.setPokemonImage
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailFragmentViewModel
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this)[DetailFragmentViewModel::class.java]

        //Back button activity
        binding.toolbarContainer.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.linearLayoutPrevious.setOnClickListener {
            //TODO previous pokemon
            Toast.makeText(requireContext(),"Back Pressed",Toast.LENGTH_SHORT).show()
        }
        binding.linearLayoutNext.setOnClickListener {
            //viewModel.loadData(args.searchList[1])
        }
        viewModel.loadData(args.name)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailFragmentViewModel::class.java]
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.pokemonDetail.observe(viewLifecycleOwner) { pokemonDetail ->
            binding.imageViewPokemon.setPokemonImage(pokemonDetail.data?.sprite?.sprite)
            binding.toolbarContainer.titleTextView.text =
                pokemonDetail.data?.name?.capitalize(Locale.getDefault())
            binding.toolbarContainer.subtitleTextView.text =
                pokemonDetail.data?.id?.let { formatId(it) } // format like #001
            pokemonDetail.data?.types?.let {
                appendTypesToTextviewContainer(it)
                setBackgorundColor(it[0])//color set to the first type name
            }
            binding.cardviewContainer.textViewWeightData.text = getString(R.string.weight_format, pokemonDetail.data?.weight?.toDouble()?.div(10).toString())
            binding.cardviewContainer.textViewHeightData.text = getString(R.string.height_format, pokemonDetail.data?.height?.toDouble()?.div(10).toString())
            pokemonDetail.data?.moves?.let { appendMovesToTextviewContainer(it) }
            fillStats(pokemonDetail.data?.stats)
        }

        viewModel.pokemonLoading.observe(viewLifecycleOwner){ loading ->
            loading.data?.let {
                if(it) {
                    binding.progressBarDetail.visibility = View.VISIBLE
                } else {
                    binding.progressBarDetail.visibility = View.GONE
                }
            }

        }
    }

    private fun fillStats(stats: List<Stat>?) {
        stats?.forEach { stat ->
            fillStatData(stat)
        }
    }

    private fun fillStatData(stat: Stat) {
        when (stat.stat.name) {
            "hp" -> {
                binding.cardviewContainer.textViewHpData.text = formatStat(stat.baseStat)
                binding.cardviewContainer.progressBarHp.progress = stat.baseStat
            }

            "attack" -> {
                binding.cardviewContainer.textViewAtkData.text = formatStat(stat.baseStat)
                binding.cardviewContainer.progressBarAtk.progress = stat.baseStat
            }
            "defense" -> {
                binding.cardviewContainer.textViewDefData.text = formatStat(stat.baseStat)
                binding.cardviewContainer.progressBarDef.progress = stat.baseStat
            }
            "special-attack" -> {
                binding.cardviewContainer.textViewSatkData.text = formatStat(stat.baseStat)
                binding.cardviewContainer.progressBarSatk.progress = stat.baseStat
            }

            "special-defense" -> {
                binding.cardviewContainer.textViewSdefData.text = formatStat(stat.baseStat)
                binding.cardviewContainer.progressBarSdef.progress = stat.baseStat
            }

            "speed" -> {
                binding.cardviewContainer.textViewSpdData.text = formatStat(stat.baseStat)
                binding.cardviewContainer.progressBarSpd.progress = stat.baseStat
            }
        }
    }

    private fun setBackgorundColor(type: Type) {
        val selectedColor =
            ContextCompat.getColor(requireContext(), viewModel.getColorForType(type))
        binding.root.setBackgroundColor(selectedColor)

        (binding.toolbarContainer.root as? ViewGroup)?.setBackgroundColor(selectedColor)
        binding.toolbarContainer.titleTextView.setBackgroundColor(selectedColor)
        binding.toolbarContainer.subtitleTextView.setBackgroundColor(selectedColor)

        binding.cardviewContainer.textViewAbout.setTextColor(selectedColor)
        binding.cardviewContainer.textviewBaseStatLabel.setTextColor(selectedColor)

        binding.cardviewContainer.textViewHpLabel.setTextColor(selectedColor)
        binding.cardviewContainer.textViewAtkLabel.setTextColor(selectedColor)
        binding.cardviewContainer.textViewDefLabel.setTextColor(selectedColor)
        binding.cardviewContainer.textViewSatkLabel.setTextColor(selectedColor)
        binding.cardviewContainer.textViewSdefLabel.setTextColor(selectedColor)
        binding.cardviewContainer.textViewSpdLabel.setTextColor(selectedColor)

        binding.cardviewContainer.progressBarHp.progressTintList = ColorStateList.valueOf(selectedColor)
        binding.cardviewContainer.progressBarAtk.progressTintList = ColorStateList.valueOf(selectedColor)
        binding.cardviewContainer.progressBarDef.progressTintList = ColorStateList.valueOf(selectedColor)
        binding.cardviewContainer.progressBarSatk.progressTintList = ColorStateList.valueOf(selectedColor)
        binding.cardviewContainer.progressBarSdef.progressTintList = ColorStateList.valueOf(selectedColor)
        binding.cardviewContainer.progressBarSpd.progressTintList = ColorStateList.valueOf(selectedColor)
    }

    private fun appendTypesToTextviewContainer(typeList: List<Type>) {

        typeList.forEach() { type ->
            val textView = TextView(requireContext())
            textView.text = type.type.name.capitalize(Locale.getDefault())
            textView.background = resources.getDrawable(R.drawable.background_type_attribute)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0F
            )
            params.marginEnd = resources.getDimension(R.dimen.text_margin).toInt()
            textView.layoutParams = params
            textView.gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
            textView.background = updateShapeBackgroundColor(type)
            textView.setTextColor(resources.getColor(R.color.white))
            binding.cardviewContainer.typesContainer.root.gravity = Gravity.CENTER_HORIZONTAL
            binding.cardviewContainer.typesContainer.root.addView(textView)
        }
    }
    private fun appendMovesToTextviewContainer(moveList: List<Move>) {

        moveList.take(2).forEach() { move ->
            val textView = TextView(requireContext())
            textView.text = move.move.name.capitalize(Locale.getDefault())
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0F
            )
            textView.layoutParams = params
            textView.gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
            binding.cardviewContainer.movesContainer.root.gravity = Gravity.CENTER_HORIZONTAL
            binding.cardviewContainer.movesContainer.root.addView(textView)
        }
    }

    private fun updateShapeBackgroundColor(type: Type): Drawable {
        val colorResId = viewModel.getColorForType(type)
        val backgroundColor = ContextCompat.getColor(requireContext(), colorResId)

        val shapeDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(backgroundColor)
            cornerRadius = resources.getDimension(R.dimen.type_textview_radius)
        }
        return shapeDrawable
    }
}
