package com.yamaha.healingyuk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import com.yamaha.healingyuk.databinding.FragmentDetailHealingBinding
import org.json.JSONObject
import androidx.appcompat.app.AppCompatActivity

class DetailHealingFragment : Fragment() {

    private var _binding: FragmentDetailHealingBinding? = null
    private val binding get() = _binding!!

    private lateinit var currentPlace: HealingPlace

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailHealingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireActivity().getSharedPreferences("user_session", AppCompatActivity.MODE_PRIVATE)
        val userId = sharedPref.getString("id_user", null)

        val place = arguments?.getParcelable<HealingPlace>("healingPlace")
        if (place != null && userId != null) {
            currentPlace = place

            binding.tvName.text = place.name
            binding.tvCategory.text = place.category
            binding.tvShortDesc.text = place.shortDescription
            binding.tvFullDesc.text = place.longDescription

            Picasso.get()
                .load(place.imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(binding.ivPhoto)

            // Cek status favorit dari server
            checkFavoriteStatus(userId, place.id)

            binding.btnAddToFavorite.setOnClickListener {
                toggleFavorite(userId, currentPlace)
            }
        } else {
            Toast.makeText(requireContext(), "Data tidak lengkap", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkFavoriteStatus(userId: String, place_id: String) {
        val url = "https://ubaya.xyz/native/160422022/check_favorite.php"

        val request = object : StringRequest(Method.POST, url,
            Response.Listener { response ->
                try {
                    val json = JSONObject(response)
                    if (json.getString("status") == "success") {
                        val isFavorite = json.getBoolean("is_favorite")
                        currentPlace.isFavorite = isFavorite
                        updateFavoriteButton(isFavorite)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Format response salah", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener {
                Toast.makeText(requireContext(), "Gagal cek status favorit", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                return hashMapOf("userId" to userId, "place_id" to place_id)
            }
        }

        Volley.newRequestQueue(requireContext()).add(request)
    }

    private fun toggleFavorite(userId: String, place: HealingPlace) {
        val isAdding = !place.isFavorite
        val url = if (isAdding)
            "https://ubaya.xyz/native/160422022/add_favorite.php"
        else
            "https://ubaya.xyz/native/160422022/remove_favorite.php"

        val request = object : StringRequest(Method.POST, url,
            Response.Listener { response ->
                try {
                    val json = JSONObject(response)
                    if (json.getString("status") == "success") {
                        place.isFavorite = isAdding
                        updateFavoriteButton(isAdding)
                        Toast.makeText(requireContext(),
                            if (isAdding) "Ditambahkan ke favorit" else "Dihapus dari favorit",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(requireContext(), "Gagal memperbarui favorit", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                Toast.makeText(requireContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                return hashMapOf("userId" to userId, "place_id" to place.id)
            }
        }

        Volley.newRequestQueue(requireContext()).add(request)
    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        binding.btnAddToFavorite.text = if (isFavorite) "Remove from Favorites" else "Add to Favorites"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
