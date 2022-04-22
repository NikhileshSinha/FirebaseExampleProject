package com.nikhileshsinha.firebaseexample.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.nikhileshsinha.firebaseexample.R
import com.nikhileshsinha.firebaseexample.adapter.MessageAdapter
import com.nikhileshsinha.firebaseexample.databinding.FragmentHomeBinding
import com.nikhileshsinha.firebaseexample.modles.Message

class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var msgList : ArrayList<Message>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        auth = Firebase.auth
        db = Firebase.firestore
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        msgList = arrayListOf()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter : MessageAdapter = MessageAdapter(R.layout.message_card, msgList)
        binding.recyclerView.adapter = adapter
        load_mesage()
    }

    fun load_mesage(){
        db.collection("message").get()
            .addOnSuccessListener {
                it.forEach{ data->
                    msgList.add(data.toObject<Message>())
                }
                binding.recyclerView.adapter?.notifyDataSetChanged()
                //Snackbar.make(binding.root, "message sent", Snackbar.LENGTH_LONG).show()
            }

            .addOnFailureListener {
            Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_INDEFINITE).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}