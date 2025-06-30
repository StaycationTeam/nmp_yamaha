package com.yamaha.healingyuk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yamaha.healingyuk.adapter.FavoritesAdapter
import com.yamaha.healingyuk.databinding.FragmentFavoritesBinding
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import android.widget.Toast
import com.android.volley.Response
import androidx.appcompat.app.AppCompatActivity


class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val favourites = mutableListOf<HealingPlace>() // Bisa diganti dengan data dari database nanti
    private lateinit var adapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPref = requireActivity().getSharedPreferences("user_session", AppCompatActivity.MODE_PRIVATE)
        val email = sharedPref.getString("email", null)

        adapter = FavoritesAdapter(favourites)
        binding.recyclerFavourites.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFavourites.adapter = adapter

        if (email != null) {
            fetchFavoritesFromServer(email)
        } else {
            Toast.makeText(requireContext(), "User belum login", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchFavoritesFromServer(email: String) {
        val url = "http://ubaya.xyz/native/160422022/get_favorites.php"

        val request = object : StringRequest(Method.POST, url,
            Response.Listener { response ->
                try {
                    val json = JSONObject(response)
                    if (json.getString("status") == "success") {
                        val dataArray = json.getJSONArray("data")
                        favourites.clear()
                        for (i in 0 until dataArray.length()) {
                            val obj = dataArray.getJSONObject(i)
                            val place = HealingPlace(
                                obj.getString("name"),
                                obj.getString("short_description"),
                                obj.getString("category"),
                                obj.getString("image_url"),
                                obj.getString("long_description")
                            )
                            favourites.add(place)
                        }
                        adapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(requireContext(), "Gagal memuat data favorit", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Format response error", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                Toast.makeText(requireContext(), "Request error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                return hashMapOf("email" to email)
            }
        }

        Volley.newRequestQueue(requireContext()).add(request)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
