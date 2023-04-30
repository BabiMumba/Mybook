package com.babimumba.mybook.Activity

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.babimumba.mybook.Unit.CLASS
import com.babimumba.mybook.databinding.ActivitySplashBinding
import com.babimumba.mybook.Unit.THEME
import com.babimumba.mybook.Unit.VOID
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private var binding: ActivitySplashBinding? = null
    var context: Context = this@SplashActivity
    var auth: FirebaseAuth? = null
    var time_per_second = 2
    var time_final = time_per_millis * time_per_second

    override fun onCreate(savedInstanceState: Bundle?) {
        THEME.setThemeOfApp(context)
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)

        VOID.Logo(baseContext, binding!!.logo)
        VOID.Intro(baseContext, binding!!.background, binding!!.backWhite, binding!!.backBlack)
        auth = FirebaseAuth.getInstance()
        Handler(Looper.getMainLooper()).postDelayed({ checkUser() }, time_final.toLong())
    }

    private fun checkUser() {
        //get current user, if logged in
        val firebaseUser = auth!!.currentUser
        if (firebaseUser == null) {
            VOID.Intent1(context, CLASS.AUTH)
        } else {
            VOID.Intent1(context, CLASS.MAIN)
        }
        finish()
    }

    companion object {
        const val time_per_millis = 1000
    }
}