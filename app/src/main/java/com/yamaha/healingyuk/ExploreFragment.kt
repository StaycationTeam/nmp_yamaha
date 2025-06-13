package com.yamaha.healingyuk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.yamaha.healingyuk.adapter.HealingAdapter
import com.yamaha.healingyuk.databinding.FragmentExploreBinding
import android.widget.Toast

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private val healingPlaces = listOf(
        HealingPlace(
            "Cafe Healing",
            "Tempat santai dengan kopi enak.",
            "Cafe",
            "https://example.com/image1.jpg"
        ),
        HealingPlace(
            "Resto Sehat",
            "Makanan sehat dan pemandangan indah.",
            "Resto",
            "https://example.com/image2.jpg"
        ),
        HealingPlace(
            "Warkop Nostalgia",
            "Ngopi sambil bernostalgia.",
            "Warkop",
            "https://example.com/image3.jpg"
        )
        // Tambah data sesuai kebutuhan
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = HealingAdapter(healingPlaces) { place ->
            // Handle tombol Read More diklik
            Toast.makeText(requireContext(), "Clicked: ${place.name}", Toast.LENGTH_SHORT).show()

            // Bisa implementasi pindah ke detail fragment atau activity
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
