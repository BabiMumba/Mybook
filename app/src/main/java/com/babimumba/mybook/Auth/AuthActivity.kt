package com.babimumba.mybook.Auth

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.babimumba.mybook.Unit.CLASS
import com.babimumba.mybook.Unit.THEME
import com.babimumba.mybook.Unit.VOID
import com.babimumba.mybook.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private var binding: ActivityAuthBinding? = null
    var context: Context = this@AuthActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        THEME.setThemeOfApp(context)
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)

        VOID.Logo(baseContext, binding!!.logo)
       VOID.Intro(baseContext, binding!!.background, binding!!.backWhite, binding!!.backBlack)

        binding!!.loginBtn.setOnClickListener { v: View? -> VOID.Intent1(context, CLASS.LOGIN) }
       binding!!.skipBtn.setOnClickListener { v: View? -> VOID.Intent1(context, CLASS.REGISTER) }
    }
}