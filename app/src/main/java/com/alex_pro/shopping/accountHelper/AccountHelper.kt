package com.alex_pro.shopping.accountHelper

import android.widget.Toast
import com.alex_pro.shopping.R
import com.alex_pro.shopping.presentation.fragments.AccountFragment
import com.google.firebase.auth.FirebaseUser

class AccountHelper(private val fragment: AccountFragment) {

    fun signUpWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            fragment.mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        sendEmailVerification(task.result?.user!!)
                        fragment.userInfoUpdate(task.result.user)

                    } else {
                        Toast.makeText(fragment.context, fragment.resources
                                .getString(R.string.sing_up_error), Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
    fun signInWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            fragment.mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        fragment.userInfoUpdate(task.result.user)
                       // signInAdmin(email,password)
                    } else {
                        Toast.makeText(fragment.context, fragment.resources
                            .getString(R.string.sing_in_error), Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun sendEmailVerification(user: FirebaseUser) {
        user.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(fragment.context, fragment.resources
                            .getString(R.string.send_verif_done), Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(fragment.context, fragment.resources
                            .getString(R.string.send_verif_email_error), Toast.LENGTH_LONG).show()
                }

            }
    }
//    fun signInAdmin(email: String, password: String){
//        if (email == "admin@admin.my" && password.isNotEmpty()) {
//            fragment.mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        fragment.adminAuth(true)
//                        Toast.makeText(fragment.context, "админ вошел", Toast.LENGTH_LONG).show()
//                    } else {
//                        Toast.makeText(fragment.context, "ошибка", Toast.LENGTH_LONG).show()
//                    }
//                }
//        }
//    }
}