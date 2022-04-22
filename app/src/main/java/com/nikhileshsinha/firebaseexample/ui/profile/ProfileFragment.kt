package com.nikhileshsinha.firebaseexample.ui.profile

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.nikhileshsinha.firebaseexample.R
import com.nikhileshsinha.firebaseexample.databinding.FragmentGalleryBinding
import com.nikhileshsinha.firebaseexample.databinding.FragmentHomeBinding
import com.nikhileshsinha.firebaseexample.databinding.FragmentProfileBinding
import com.nikhileshsinha.firebaseexample.modles.Profile

class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    val REQUEST_IMAGE_GET = 200
    private var photouri: Uri? = null
    private var imgUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        db = Firebase.firestore
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        storage = Firebase.storage
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userName = auth.currentUser?.displayName
        val userEmail = auth.currentUser?.email
        binding.txtEmail.setText(userEmail)
        binding.txtUserName.setText(userName)
        binding.btnSave.setOnClickListener{
            updateProfile()
        }
        binding.imgProfile.setOnClickListener{
            selectImage()
        }
    }

    private fun updateProfile() {
        uploadImage()
    }

    private fun uploadImage() {
        val imgName = "dp/"+auth.currentUser?.displayName + ".jpg"
        val imgRef = storage.reference.child(imgName)
        photouri?.let {
            imgRef.putFile(it)
            .addOnSuccessListener {task ->
                imgRef.downloadUrl.addOnSuccessListener {task ->
                    imgUrl = task.toString()
                    //Snackbar.make(binding.root, "Image uploded", Snackbar.LENGTH_SHORT).show()
                    uploadDetails()
                }
            }
            .addOnFailureListener {exception ->
                Snackbar.make(binding.root, exception.message.toString(), Snackbar.LENGTH_INDEFINITE).show()
            }
            .addOnProgressListener {task ->
                val perc = task.bytesTransferred / task.totalByteCount
                Log.d("Progress value : ", "${perc}%")
            }
        }
    }


    private fun uploadDetails(){
        val userNow = auth.currentUser
        val bio = binding.etxtBio.text.toString()
        if(userNow!= null){
            db.collection("Users").document(userNow.uid).set(
                Profile(
                    userNow.email.toString(),
                    userNow.displayName.toString(),
                    userNow.uid,
                    bio,
                    imgUrl!!
                )
            ).addOnSuccessListener {
                Snackbar.make(binding.root, "All details are successfully uploaded ", Snackbar.LENGTH_SHORT).show()
            }
                .addOnFailureListener {
                    Snackbar.make(binding.root,  it.message.toString(), Snackbar.LENGTH_INDEFINITE).show()
                }
        }

        /*userNow?.uid?.let { uid -> db.collection("Users").document(uid).set(
            Profile(
                userNow.email.toString(),
                userNow.displayName.toString(),
                userNow.uid,
                bio,
                imgUrl!!
            )
        ).addOnSuccessListener {
            Snackbar.make(binding.root, "All details are successfully uploaded ", Snackbar.LENGTH_SHORT).show()
        }
            .addOnFailureListener {
                Snackbar.make(binding.root,  it.message.toString(), Snackbar.LENGTH_INDEFINITE).show()
            }
        }*/
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    fun selectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET)
        }
    }

    //onActivityResult() function is called when data from different application is called
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_IMAGE_GET) {
            if(resultCode == Activity.RESULT_OK){
                val thumbnail: Bitmap? = data?.getParcelableExtra("data")
                photouri = data?.data
                Glide.with(requireActivity()).load(photouri).into(binding.imgProfile)
            }
            else{//todo handel cancel objects
             }
        }
    }

    class SelectDialogFragment: DialogFragment(){
       /* override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
            return activity?.let {
                // Use the Builder class for convenient dialog construction
                val builder = AlertDialog.Builder(it)
                builder.setMessage(R.string.dialog_start_game)
                    .setPositiveButton(R.string.start,
                        DialogInterface.OnClickListener { dialog, id ->
                            // START THE GAME!
                        })
                    .setNegativeButton(R.string.cancel,
                        DialogInterface.OnClickListener { dialog, id ->
                            // User cancelled the dialog
                        })
                // Create the AlertDialog object and return it
                builder.create()
            } ?: throw IllegalStateException("Activity cannot be null")
        }*/
    }
}