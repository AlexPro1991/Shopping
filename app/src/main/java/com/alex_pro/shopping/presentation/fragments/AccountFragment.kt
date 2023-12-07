package com.alex_pro.shopping.presentation.fragments

import android.content.ClipData.Item
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.alex_pro.shopping.R
import com.alex_pro.shopping.databinding.FragmentAccountBinding
import com.alex_pro.shopping.dialogHelper.Dialog
import com.alex_pro.shopping.dialogHelper.DialogHelper
import com.alex_pro.shopping.presentation.EditActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding
    private lateinit var tvAccount:TextView
    private val dialogHelper = DialogHelper(this)
    val mAuth = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
onBottomNavClicks()
    }

    override fun onStart() {
        super.onStart()
        userInfoUpdate(mAuth.currentUser)
    }
    
    private fun onBottomNavClicks()= with(binding){
        navMenuAccount.setNavigationItemSelectedListener {
            when (it.itemId){
                R.id.registration -> { dialogHelper.createSignDialog(Dialog.SIGN_UP_STATE)
                }
                R.id.signIn -> { dialogHelper.createSignDialog(Dialog.SIGN_IN_STATE)

                }
                R.id.signOut ->{
                    userInfoUpdate(null)
                    mAuth.signOut()
                    //adminAuth(false)
                }
                R.id.signInAdminItem ->{
                    val intent = Intent (activity, EditActivity::class.java)
                    activity?.startActivity(intent)
                }
            }
            true
        }
    }
     fun userInfoUpdate(user:FirebaseUser?){
        tvAccount = binding.navMenuAccount.getHeaderView(0).findViewById(R.id.tvAccountEmail)
         tvAccount.text = if (user == null){
             resources.getString(R.string.not_reg)
         }else{
             user.email
         }
    }
//    fun adminAuth(auth: Boolean){
//        binding.navMenuAccount.menu.findItem(R.id.signInAdminItem).isVisible = auth
  //  }
    companion object {

        @JvmStatic
        fun newInstance() = AccountFragment()
    }
}