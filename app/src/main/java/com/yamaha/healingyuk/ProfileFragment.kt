package com.yamaha.healingyuk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.yamaha.healingyuk.databinding.FragmentProfileBinding
import org.json.JSONObject

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireActivity().getSharedPreferences("user_session", AppCompatActivity.MODE_PRIVATE)
        val email = sharedPref.getString("email", null)

        if (email != null) {
            fetchUserData(email)
        } else {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchUserData(email: String) {
        val url = "https://ubaya.xyz/native/160422022/get_user.php"

        val params = HashMap<String, String>()
        params["email"] = email

        val stringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    val json = JSONObject(response)
                    if (json.getString("status") == "success") {
                        val data = json.getJSONObject("data")
                        val name = data.getString("name")
                        val emailRes = data.getString("email")
                        val dateJoined = data.getString("joined_at")
                        val favorites = data.getInt("favorites")

                        // Update UI
                        binding.txtName.setText(name)
                        binding.txtEmail.setText(emailRes)
                        binding.editTextDate.setText(dateJoined)
                        binding.txtNumber.setText(favorites.toString())

                        // Disable save button awalnya
                        var originalName = name
                        var originalEmail = emailRes
                        binding.btnSaveChanges.isEnabled = false

                        val watcher = object : android.text.TextWatcher {
                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                val nameChanged = binding.txtName.text.toString() != originalName
                                val emailChanged = binding.txtEmail.text.toString() != originalEmail
                                binding.btnSaveChanges.isEnabled = nameChanged || emailChanged
                            }
                            override fun afterTextChanged(s: android.text.Editable?) {}
                        }

                        binding.txtName.addTextChangedListener(watcher)
                        binding.txtEmail.addTextChangedListener(watcher)

                        binding.btnSaveChanges.setOnClickListener {
                            Toast.makeText(requireContext(), "Changes saved (but not yet uploaded)", Toast.LENGTH_SHORT).show()
                            // Implement updateProfileToServer(...) jika perlu
                        }

                    } else {
                        Toast.makeText(requireContext(), json.getString("message"), Toast.LENGTH_SHORT).show()
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
        ) {
            override fun getParams(): Map<String, String> {
                return params
            }
        }

        Volley.newRequestQueue(requireContext()).add(stringRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
