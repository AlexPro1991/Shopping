package com.alex_pro.shopping.test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.alex_pro.shopping.databinding.RcContactsItemBinding
import com.alex_pro.shopping.test.HomeFragmentTestDirections
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class ProductAdapter(private val contactList: ArrayList<Product>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
     return ViewHolder(RcContactsItemBinding.inflate(LayoutInflater.from(parent.context),
         parent, false))
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = contactList[position]
        holder.apply {
            binding.apply {
                tvTitle.text = currentItem.nameProduct
                tvPrice.text = currentItem.price
                Picasso.get().load(currentItem.imageUri).into(imProductItem)

                //tv.text = currentItem.id
                rvContainer.setOnClickListener {
                    val action = HomeFragmentTestDirections.actionHomeFragmentTestToUpdateFragment(
                        currentItem.id.toString(),
                        currentItem.nameProduct.toString(),
                        currentItem.price.toString(),
                        currentItem.imageUri.toString()
                    )

                    findNavController(holder.itemView).navigate(action)
                }
                rvContainer.setOnLongClickListener() {
                    MaterialAlertDialogBuilder(holder.itemView.context)
                        .setTitle("Удалить")
                        .setMessage("Вы точно хотите удалить ?")
                        .setPositiveButton("Удалить"){_,_->
                         val firebaseRef = FirebaseDatabase.getInstance().getReference("products")
                         val storageRef = FirebaseStorage.getInstance().getReference("Images")
                         //storage
                            storageRef.child(currentItem.id.toString()).delete()
                           //realtime database
                         firebaseRef.child(currentItem.id.toString()).removeValue()
                             .addOnSuccessListener {
                                 Toast.makeText(holder.itemView.context,"deleted", Toast.LENGTH_SHORT).show()
                             }
                             .addOnFailureListener {
                                 Toast.makeText(holder.itemView.context,"error ${it.message}", Toast.LENGTH_SHORT).show()
                             }
                        }
                        .setNegativeButton("Отмена"){_,_->

                        }
                        .show()
                    return@setOnLongClickListener true
                }
            }
        }
    }
    class ViewHolder( val binding : RcContactsItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}