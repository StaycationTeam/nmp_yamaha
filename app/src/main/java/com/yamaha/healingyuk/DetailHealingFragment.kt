package com.yamaha.healingyuk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
            // Untuk foto, gunakan Glide atau Picasso
            // Contoh dengan Glide:
            // Glide.with(this).load(it.getString("photoUrl")).into(binding.ivPhoto)

            binding.tvCategory.text = it.getString("category")
            binding.tvShortDesc.text = it.getString("shortDesc")
            binding.tvFullDesc.text = it.getString("fullDesc")

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
