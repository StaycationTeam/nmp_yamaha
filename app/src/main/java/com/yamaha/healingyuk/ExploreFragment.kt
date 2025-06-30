package com.yamaha.healingyuk

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.yamaha.healingyuk.adapter.HealingAdapter
import com.yamaha.healingyuk.databinding.FragmentExploreBinding
import org.json.JSONObject

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private val healingPlaces = mutableListOf<HealingPlace>()
    private lateinit var adapter: HealingAdapter



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HealingAdapter(healingPlaces) { place: HealingPlace ->
            val detailFragment = DetailHealingFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("healingPlace", place)
                }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.fabAdd.setOnClickListener {
            val intent = Intent(requireContext(), AddLocationActivity::class.java)
            startActivity(intent)
        }

        fetchHealingPlacesFromServer()
    }

    private fun fetchHealingPlacesFromServer() {
        val url = "http://ubaya.xyz/native/160422022/get_all_places.php"
        val stringRequest = object : com.android.volley.toolbox.StringRequest(
            Method.GET, url,
            Response.Listener { response ->
                try {
                    val json = JSONObject(response)
                    if (json.getString("status") == "success") {
                        val dataArray = json.getJSONArray("data")
                        healingPlaces.clear()
                        for (i in 0 until dataArray.length()) {
                            val obj = dataArray.getJSONObject(i)
                            val place = HealingPlace(
                                obj.getString("name"),
                                obj.getString("short_description"),
                                obj.getString("category"),
                                obj.getString("image_url"),
                                obj.getString("long_description")
                            )
                            healingPlaces.add(place)
                        }
                        adapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(requireContext(), "Gagal memuat data", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Response format error", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                Toast.makeText(requireContext(), "Request error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        ) {}

        Volley.newRequestQueue(requireContext()).add(stringRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

