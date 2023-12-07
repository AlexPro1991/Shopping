package com.alex_pro.shopping.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.alex_pro.shopping.databinding.FragmentCategoriesBinding
import com.alex_pro.shopping.test.adapter.CategoryAdapter
import com.alex_pro.shopping.test.adapter.Product
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CategoriesFragment : Fragment() {
private lateinit var binding: FragmentCategoriesBinding
    private lateinit var firebaseRef: DatabaseReference
    private lateinit var contactsList: ArrayList<Product>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater,container, false)
        firebaseRef = FirebaseDatabase.getInstance().getReference("products")


        binding.rcCategory.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this.context,2)

        }
        fetchData()

        return binding.root
    }
    private fun fetchData() {
        contactsList = arrayListOf()
        firebaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                contactsList.clear()
                if (snapshot.exists()) {
                    for (contactsSnap in snapshot.children){
                        val contacts = contactsSnap.getValue(Product::class.java)
                        contactsList.add(contacts!!)
                    }
                }
                val rvAdapter = CategoryAdapter(contactsList)
                binding.rcCategory.adapter = rvAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "error: $error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {

        @JvmStatic
        fun newInstance() = CategoriesFragment()
    }
}