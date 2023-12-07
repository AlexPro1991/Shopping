package com.alex_pro.shopping.test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alex_pro.shopping.databinding.RcContactsItemBinding
import com.squareup.picasso.Picasso

class CategoryAdapter(private val contactList: ArrayList<Product>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {



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
                //tvId.text = currentItem.id
              //  rvContainer.setOnClickListener {
//                    val action = HomeFragmentTestDirections.actionHomeFragmentTestToUpdateFragment(
//                        currentItem.id.toString(),
//                        currentItem.name.toString(),
//                        currentItem.phoneNumber.toString()
//                    )
//
//                    findNavController(holder.itemView).navigate(action)
           //     }
            }
        }
    }
    class ViewHolder( val binding : RcContactsItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}