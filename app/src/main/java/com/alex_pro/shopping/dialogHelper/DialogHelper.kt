package com.alex_pro.shopping.dialogHelper

import android.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.alex_pro.shopping.R
import com.alex_pro.shopping.accountHelper.AccountHelper
import com.alex_pro.shopping.databinding.SignDialogBinding
import com.alex_pro.shopping.presentation.fragments.AccountFragment

class DialogHelper(private  val fragment: AccountFragment) {
    private val accHelper = AccountHelper(fragment)

    fun createSignDialog(index: Int){
        val builder = AlertDialog.Builder(fragment.context)
        val binding = SignDialogBinding.inflate(fragment.layoutInflater)
        val view = binding.root
        builder.setView(view)
        setDialogState(index, binding)

        val dialog =builder.create()
        binding.btSignUp.setOnClickListener {
            setOnClickSign(index,binding,dialog)
        }
        binding.tvForgetPass.setOnClickListener {
            setOnClickResetPass(binding,dialog)
        }
        dialog.show()
    }

    private fun setOnClickResetPass(binding: SignDialogBinding, dialog: AlertDialog?) {
     binding.edSignPass.visibility = View.INVISIBLE
     binding.btSignUp.visibility = View.INVISIBLE
        binding.tvForgetPass.text = "Натисніть щоб відновити пароль"
if (binding.edSignEmail.text.isNotEmpty()){
    fragment.mAuth.sendPasswordResetEmail(binding.edSignEmail.text.toString())
        .addOnCompleteListener {task->
            if (task.isSuccessful){
                Toast.makeText(fragment.context, R.string.pass_reset, Toast.LENGTH_LONG).show()
            }
        }
    dialog?.dismiss()
}else{
    binding.tvDialogMassage.visibility = View.VISIBLE
}
    }

    private fun setOnClickSign(index: Int, binding: SignDialogBinding, dialog: AlertDialog?) {
        dialog?.dismiss()

        if (index == Dialog.SIGN_UP_STATE){
            accHelper.signUpWithEmail(binding.edSignEmail.text.toString(),
                binding.edSignPass.text.toString())
        }else{
            accHelper.signInWithEmail(binding.edSignEmail.text.toString(),
                binding.edSignPass.text.toString())
        }
    }

    private fun setDialogState(index: Int, binding: SignDialogBinding)= with(binding) {
        if(index == Dialog.SIGN_UP_STATE){
            tvSignTitle.text = fragment.resources
                .getString(R.string.registration_title)
            btSignUp.text = fragment.resources
                .getString(R.string.registration)
        }else {
            tvSignTitle.text = fragment.resources
                .getString(R.string.sign_in_title)
            btSignUp.text = fragment.resources
                .getString(R.string.sign_in_bt)
            tvForgetPass.visibility = View.VISIBLE
        }
    }
}