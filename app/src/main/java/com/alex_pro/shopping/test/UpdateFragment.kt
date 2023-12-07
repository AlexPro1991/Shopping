package com.alex_pro.shopping.test

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alex_pro.shopping.R
import com.alex_pro.shopping.databinding.FragmentUpdateBinding
import com.alex_pro.shopping.test.adapter.Product
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class UpdateFragment : Fragment() {
    private lateinit var binding: FragmentUpdateBinding

    private val args: UpdateFragmentArgs by navArgs()

    private lateinit var firebaseRef: DatabaseReference
    private lateinit var storageRef: StorageReference

    private var uri: Uri? = null
    private var imageUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        firebaseRef = FirebaseDatabase.getInstance().getReference("products")
        storageRef = FirebaseStorage.getInstance().getReference("Images")

        val picImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
            binding.imUpdate.setImageURI(it)
            if (it != null) {
                uri = it
            }
        }
        imageUrl = args.imageUrl

        binding.apply {
            edUpdateName.setText(args.productName)
            edUpdatePhone.setText(args.price)
            Picasso.get().load(args.imageUrl).into(imUpdate)

            btUpdate.setOnClickListener {
                updateData()
                findNavController().navigate(R.id.action_updateFragment_to_homeFragmentTest)
            }
            imUpdate.setOnClickListener {
                MaterialAlertDialogBuilder(activity as AppCompatActivity)
                    .setTitle("Изменить изображение изображение")
                    .setMessage("Вы точно хотите изменить изображение?")
                    .setPositiveButton("Изменить") { _, _ ->
                        picImage.launch("image/*")
                    }
                    .setNegativeButton("Удалить") { _, _ ->
                        imageUrl = null
                        binding.imUpdate.setImageResource(R.drawable.logo_product)
                    }
                    .setNeutralButton("Отмена") { _, _ -> }
                    .show()

            }
        }

        return binding.root
    }

    private fun updateData() {
        val nameProduct = binding.edUpdateName.text.toString()
        val price = binding.edUpdatePhone.text.toString()
        var products: Product


        if (uri != null) {
            storageRef.child(args.id).putFile(uri!!)
                .addOnSuccessListener { task ->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener { url ->
                            imageUrl = url.toString()
                            products = Product(args.id, nameProduct, price, imageUrl)
                            firebaseRef.child(args.id).setValue(products)
                                .addOnCompleteListener {
                                    Toast.makeText(
                                        context,
                                        "data updated successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()

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
        if (uri == null) {
            products = Product(args.id, nameProduct, price, imageUrl)
            firebaseRef.child(args.id).setValue(products)
                .addOnCompleteListener {
                    Toast.makeText(context, "data updated successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "error ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
        if (imageUrl == null) storageRef.child(args.id).delete()
    }
}