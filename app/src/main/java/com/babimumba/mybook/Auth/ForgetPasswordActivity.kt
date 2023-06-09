package com.babimumba.mybook.Auth

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.babimumba.mybook.Unit.CLASS
import com.babimumba.mybook.Unit.THEME
import com.babimumba.mybook.Unit.VOID
import com.babimumba.mybook.databinding.ActivityForgetPasswordBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class ForgetPasswordActivity : AppCompatActivity() {

    private var binding: ActivityForgetPasswordBinding? = null
    private val context: Context = this@ForgetPasswordActivity
    private var auth: FirebaseAuth? = null
    private var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        THEME.setThemeOfApp(context)
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(
            layoutInflater
        )
        val view = binding!!.root
        setContentView(view)
        VOID.Logo(baseContext, binding!!.logo)
        VOID.Intro(baseContext, binding!!.background, binding!!.backWhite, binding!!.backBlack)
        auth = FirebaseAuth.getInstance()
        dialog = ProgressDialog(this)
        dialog!!.setTitle("Please wait...")
        dialog!!.setCanceledOnTouchOutside(false)
        binding!!.noAccount.setOnClickListener { v: View? ->
            VOID.Intent1(context, CLASS.REGISTER)
            finish()
        }
        binding!!.login.setOnClickListener { v: View? ->
            VOID.Intent1(context, CLASS.LOGIN)
            finish()
        }
        binding!!.go.setOnClickListener { v: View? -> validateDate() }
    }

    private var email = ""
    private fun validateDate() {
        email = binding!!.emailEt.text.toString().trim { it <= ' ' }
        if (email.isEmpty()) {
            Toast.makeText(context, "Enter email...!", Toast.LENGTH_SHORT).show()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Invalid email format...!", Toast.LENGTH_SHORT).show()
        } else {
            recoverPassword()
        }
    }

    private fun recoverPassword() {
        dialog!!.setMessage("Sending password recovery to instructions to $email")
        dialog!!.show()
        auth!!.sendPasswordResetEmail(email).addOnCompleteListener { task: Task<Void?>? ->
            dialog!!.dismiss()
            Toast.makeText(
                context,
                "Instructions to reset password sent to $email",
                Toast.LENGTH_SHORT
            ).show()
        }.addOnFailureListener { e: Exception ->
            dialog!!.dismiss()
            Toast.makeText(context, "Failed to send to due to " + e.message, Toast.LENGTH_SHORT)
                .show()
        }
    }
}