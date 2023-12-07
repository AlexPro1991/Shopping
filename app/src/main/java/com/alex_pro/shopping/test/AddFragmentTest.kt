package com.alex_pro.shopping.test

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.alex_pro.shopping.R
import com.alex_pro.shopping.databinding.FragmentAddTestBinding
import com.alex_pro.shopping.test.adapter.Product
import com.alex_pro.shopping.utils.openFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class AddFragmentTest : Fragment() {
    private lateinit var binding: FragmentAddTestBinding
    private lateinit var firebaseRef: DatabaseReference
    private lateinit var storageRef: StorageReference
    private var uri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTestBinding.inflate(inflater, container, false)
        firebaseRef = FirebaseDatabase.getInstance().getReference("products")
        storageRef = FirebaseStorage.getInstance().getReference("Images")

        binding.btSend.setOnClickListener {


            saveData()


        }
        val picImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
            binding.imProduct.setImageURI(it)
            if (it != null) {
                uri = it
            }
        }
        binding.imProduct.setOnClickListener {
            picImage.launch("image/*")
        }
        return binding.root
    }

    private fun saveData() {
        val nameProduct = binding.edName.text.toString()
        val price = binding.edPrice.text.toString() + " грн"



        if (nameProduct.isNotEmpty() && price.isNotEmpty()) {
            val productId = firebaseRef.push().key!!
            var products: Product
            uri?.let {
                storageRef.child(productId).putFile(it)
                    .addOnSuccessListener { task ->
                        task.metadata!!.reference!!.downloadUrl
                            .addOnSuccessListener { url ->
                                Toast.makeText(
                                    context,
                                    "Img stored successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val imgUrl = url.toString()
                                products = Product(productId, nameProduct, price, imgUrl)

                                firebaseRef.child(productId).setValue(products)
                                    .addOnCompleteListener {
                                        Toast.makeText(
                                            context,
                                            "data stored successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        findNavController().navigate(R.id.action_addFragmentTest_to_homeFragmentTest)

                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(
                                            context,
                                            "error ${it.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }
                    }
            }
        }
    }
}