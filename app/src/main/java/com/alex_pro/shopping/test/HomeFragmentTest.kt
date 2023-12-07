package com.alex_pro.shopping.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.alex_pro.shopping.R
import com.alex_pro.shopping.databinding.FragmentHomeTestBinding
import com.alex_pro.shopping.test.adapter.ProductAdapter
import com.alex_pro.shopping.test.adapter.Product
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragmentTest : Fragment() {
   private lateinit var binding: FragmentHomeTestBinding
   private lateinit var productsList: ArrayList<Product>
   private lateinit var firebaseRef: DatabaseReference



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeTestBinding.inflate(inflater,container,false)

        binding.btAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragmentTest_to_addFragmentTest)
        }
        firebaseRef = FirebaseDatabase.getInstance().getReference("products")

        binding.rcAdapter.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this.context,2)
        }
        fetchData()


        return binding.root
    }

     private  fun fetchData() {
         productsList = arrayListOf()
        firebaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                productsList.clear()
                if (snapshot.exists()) {
                for (contactsSnap in snapshot.children){
                    val contacts = contactsSnap.getValue(Product::class.java)
                    productsList.add(contacts!!)
                }
                }
                val rvAdapter = ProductAdapter(productsList)
                binding.rcAdapter.adapter = rvAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "error: $error", Toast.LENGTH_SHORT).show()
            }
        })
    }


}