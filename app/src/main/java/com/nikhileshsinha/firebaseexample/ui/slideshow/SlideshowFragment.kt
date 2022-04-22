package com.nikhileshsinha.firebaseexample.ui.slideshow
/*
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nikhileshsinha.firebaseexample.modles.Message
import com.nikhileshsinha.firebaseexample.Auth
import com.nikhileshsinha.firebaseexample.databinding.FragmentSlideshowBinding

class SlideshowFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //1
    private lateinit var db: FirebaseFirestore


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root
        auth = Firebase.auth
        db = Firebase.firestore
        return root
    }
//2
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    db = Firebase.firestore
    auth = Firebase.auth
    binding.btnSubmit.setOnClickListener {
        val msg = binding.txtMessage.text.toString()
        val data = auth.currentUser?.displayName?.let{it -> Message(msg, it) }
        data?.let{it1 ->
            db.collection("messages").add(it1).addOnSuccessListener {
                Snackbar.make(binding.root, "message sent", Snackbar.LENGTH_LONG).show()
                binding.txtMessage.text.clear()
            }.addOnFailureListener{
                Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_LONG).show()
            }
        }
    }
    }

    override fun onStart() {
        super.onStart()
        updateUI(auth.currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser == null){
            val i = Intent(requireContext(), Auth::class.java)
            startActivity(i)
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}*/


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nikhileshsinha.firebaseexample.Auth
import com.nikhileshsinha.firebaseexample.databinding.FragmentSlideshowBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nikhileshsinha.firebaseexample.modles.Message

class SlideshowFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentSlideshowBinding? = null

    private val binding get() = _binding!!
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root
        auth = Firebase.auth
        db = Firebase.firestore
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //db = Firebase.firestore
        //auth = Firebase.auth
        binding.btnSubmit.setOnClickListener {
            val msg = binding.txtMessage.text.toString()
            val data = auth.currentUser?.displayName?.let { it -> Message(msg, it) }
            /*about above lines:
                    variable data  =  find the display name of current user and if you get that then make a object it of class Message(Message is a data class)
            */
            data?.let { it1 ->
                db.collection("message").add(it1).addOnSuccessListener {
                    Snackbar.make(binding.root, "message sent", Snackbar.LENGTH_LONG).show()
                    binding.txtMessage.text.clear()
                    //If every thing was successfully done then  addOnSuccessListener will run here and it will pop-up a snackbar which tells message was addes successfully
                    //and at last it will clear the message area
                }.addOnFailureListener {
                    Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_LONG).show()
                }
                //If for any reasons our message can not sent to firebase, addOnFailureListener will run here and display the error message in snackbar
            }

        }
    }

    override fun onStart() {
        super.onStart()
        updateUI(auth.currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser == null) {
            val i = Intent(requireContext(), Auth::class.java)
            startActivity(i)
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}