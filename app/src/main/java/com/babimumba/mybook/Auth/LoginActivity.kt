package com.babimumba.mybook.Auth

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.babimumba.mybook.Unit.CLASS
import com.babimumba.mybook.databinding.ActivityLoginBinding
import com.babimumba.mybook.Unit.DATA
import com.babimumba.mybook.Unit.THEME
import com.babimumba.mybook.Unit.VOID
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private var binding: ActivityLoginBinding? = null
    var context: Context = this@LoginActivity
    private var auth: FirebaseAuth? = null
    private var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        THEME.setThemeOfApp(context)
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)
        VOID.Logo(baseContext, binding!!.logo)
        VOID.Intro(baseContext, binding!!.background, binding!!.backWhite, binding!!.backBlack)
        auth = FirebaseAuth.getInstance()
        dialog = ProgressDialog(this)
        dialog!!.setTitle("Please wait...")
        dialog!!.setCanceledOnTouchOutside(false)
        binding!!.forget.setOnClickListener { v: View? ->
            VOID.Intent1(
                context,
                CLASS.FORGET_PASSWORD
            )
        }

        binding!!.noAccount.setOnClickListener { v: View? ->
            VOID.Intent1(
                context,
                CLASS.REGISTER
            )
        }
        binding!!.loginBtn.setOnClickListener { v: View? -> validateDate() }
    }

    private var email = ""
    private var password = ""
    private fun validateDate() {

        //get data
        email = binding!!.emailEt.text.toString().trim { it <= ' ' }
        password = binding!!.passwordEt.text.toString().trim { it <= ' ' }

        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Invalid email pattern...!", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "Enter password...!", Toast.LENGTH_SHORT).show()
        } else {
            loginUser()
        }
    }

    private fun loginUser() {
        dialog!!.setMessage("Logging In...")
        dialog!!.show()
        try {
            auth!!.signInWithEmailAndPassword(email, password).addOnCanceledListener {
                dialog!!.dismiss()
                Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show()
            }.addOnSuccessListener { authResult: AuthResult? ->
                VOID.IntentClear(
                    context,
                    CLASS.MAIN
                )
            }
                .addOnFailureListener { e: Exception ->
                    dialog!!.dismiss()
                    Toast.makeText(context, DATA.EMPTY + e.message, Toast.LENGTH_SHORT).show()
                }.addOnCompleteListener { task: Task<AuthResult?>? -> dialog!!.show() }
        } catch (e: Exception) {
            dialog!!.dismiss()
            Toast.makeText(context, DATA.EMPTY + e.message, Toast.LENGTH_SHORT).show()
        }
    }
}