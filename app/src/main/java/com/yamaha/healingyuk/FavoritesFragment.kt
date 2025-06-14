package com.yamaha.healingyuk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yamaha.healingyuk.adapter.FavoritesAdapter
import com.yamaha.healingyuk.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val favourites = mutableListOf<HealingPlace>() // Bisa diganti dengan data dari database nanti

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Dummy data favorit
        favourites.addAll(listOf(
            HealingPlace("Healing Cafe", "Tempat ngopi santai", "Cafe", "https://lh3.googleusercontent.com/gps-cs-s/AC9h4nrg5lR_szeluo7QamLyQULN4lXa4YgKS2EDtJ1l-jjlGAqBcGVwMJGzBb8jS3zMjfrnXBy0_PVi10d2uoZ-QkFc7bRqMV-4GbbfGt9QVuyOHsI_x2-2Sk1MF9WalFTkn_BtYTtb=w408-h306-k-no"),
            HealingPlace("Arcade Center", "Tempat hiburan klasik", "Arcade", "https://lh3.googleusercontent.com/gps-cs-s/AC9h4npYYjyFE4PN8IZUUXwRi5oY1AFdyss2fPlyXQeTZhCArLiZvBks4_mVVoUqoGgWWWHzGbr-RUVzGo7SpM8p9pv1v0dMp8-ReXgufC3frQ9fNt-wr7av5QolP9eHCOOPeFvG0Vds=w408-h306-k-no")
        ))

        val adapter = FavoritesAdapter(favourites)
        binding.recyclerFavourites.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFavourites.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
