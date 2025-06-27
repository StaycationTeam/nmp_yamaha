package com.yamaha.healingyuk

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yamaha.healingyuk.adapter.HealingAdapter
import com.yamaha.healingyuk.databinding.FragmentExploreBinding

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private val healingPlaces = listOf(
        HealingPlace("Cafe Healing", "Tempat santai dengan kopi enak.", "Cafe", "https://example.com/image1.jpg"),
        HealingPlace("Resto Sehat", "Makanan sehat dan pemandangan indah.", "Resto", "https://example.com/image2.jpg"),
        HealingPlace("Warkop Nostalgia", "Ngopi sambil bernostalgia.", "Warkop", "https://example.com/image3.jpg")
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = HealingAdapter(healingPlaces) { place ->
            val detailFragment = DetailHealingFragment().apply {
                arguments = Bundle().apply {
                    putString("name", place.name)
                    putString("shortDescription", place.shortDescription)
                    putString("category", place.category)
                    putString("imageUrl", place.imageUrl)
                }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit()
        }
        binding.fabAdd.setOnClickListener {
            val intent = Intent(requireContext(), AddLocationActivity::class.java)
            startActivity(intent)
        }


        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
