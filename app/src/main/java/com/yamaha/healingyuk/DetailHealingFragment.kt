package com.yamaha.healingyuk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.yamaha.healingyuk.databinding.FragmentDetailHealingBinding

class DetailHealingFragment : Fragment() {

    private var _binding: FragmentDetailHealingBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(
            name: String,
            photoUrl: String,
            shortDesc: String,
            category: String,
            fullDesc: String
        ): DetailHealingFragment {
            val fragment = DetailHealingFragment()
            val bundle = Bundle()
            bundle.putString("name", name)
            bundle.putString("photoUrl", photoUrl)
            bundle.putString("shortDesc", shortDesc)
            bundle.putString("category", category)
            bundle.putString("fullDesc", fullDesc)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailHealingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            binding.tvName.text = it.getString("name")
            binding.tvCategory.text = it.getString("category")
            binding.tvShortDesc.text = it.getString("shortDesc")
            binding.tvFullDesc.text = it.getString("fullDesc")

            // Gambar dengan Picasso
            val photoUrl = it.getString("photoUrl")
            if (!photoUrl.isNullOrEmpty()) {
                Picasso.get()
                    .load(photoUrl)
                    .placeholder(R.drawable.placeholder_image) // Optional, gambar saat loading
                    .error(R.drawable.error_image) // Optional, gambar jika gagal load
                    .into(binding.ivPhoto)
            }

            binding.btnAddToFavorite.setOnClickListener {
                // Logika tambah ke favorite di sini
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
