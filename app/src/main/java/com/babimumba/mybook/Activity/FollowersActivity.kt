package com.babimumba.mybook.Activity

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.babimumba.mybook.databinding.ActivityPageStaggeredBinding
import com.babimumba.mybook.Adapter.PublisherAdapter
import com.babimumba.mybook.Model.User
import com.babimumba.mybook.Unit.DATA
import com.babimumba.mybook.R
import com.babimumba.mybook.Unit.THEME
import com.babimumba.mybook.Unit.VOID
import com.google.firebase.database.*
import java.text.MessageFormat

class FollowersActivity : AppCompatActivity() {

    private var binding: ActivityPageStaggeredBinding? = null
    private val context: Context = this@FollowersActivity
    var check: MutableList<String?>? = null
    var list: ArrayList<User?>? = null
    var adapter: PublisherAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        THEME.setThemeOfApp(context)
        super.onCreate(savedInstanceState)
        binding = ActivityPageStaggeredBinding.inflate(
            layoutInflater
        )
        val view = binding!!.root
        setContentView(view)
        binding!!.toolbar.nameSpace.setText(R.string.followers)
        VOID.BannerAd(context, binding!!.adView, DATA.BANNER_SMART_FOLLOWERS)
        binding!!.toolbar.search.setOnClickListener {
            binding!!.toolbar.toolbar.visibility = View.GONE
            binding!!.toolbar.toolbarSearch.visibility = View.VISIBLE
            DATA.searchStatus = true
        }
        binding!!.toolbar.close.setOnClickListener { onBackPressed() }
        binding!!.toolbar.textSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                try {
                    adapter!!.filter.filter(s)
                } catch (e: Exception) {
                    //None
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })

        //binding.recyclerView.setHasFixedSize(true);
        list = ArrayList()
        adapter = PublisherAdapter(context, list!!)
        binding!!.recyclerView.adapter = adapter
        binding!!.toolbar.back.setOnClickListener { onBackPressed() }
    }

    private val data: Unit
        private get() {
            check = ArrayList()
            val reference = FirebaseDatabase.getInstance().getReference(DATA.FOLLOW)
                .child(DATA.FirebaseUserUid).child(DATA.FOLLOWERS)
            reference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    (check as ArrayList<String?>).clear()
                    for (snapshot in dataSnapshot.children) {
                        (check as ArrayList<String?>).add(snapshot.key)
                    }
                    users
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }
    private val users: Unit
        get() {
            val reference: Query = FirebaseDatabase.getInstance().getReference(DATA.USERS)
            reference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    list!!.clear()
                    var i = 0
                    for (snapshot in dataSnapshot.children) {
                        val user = snapshot.getValue(
                            User::class.java
                        )
                        for (id in check!!) {
                            assert(user != null)
                            if (user!!.username != null) if (user.id == id) {
                                list!!.add(user)
                                i++
                            }
                        }
                    }
                    binding!!.toolbar.number.text = MessageFormat.format("( {0} )", i)
                    binding!!.progress.visibility = View.GONE
                    if (list!!.isNotEmpty()) {
                        binding!!.recyclerView.visibility = View.VISIBLE
                        binding!!.emptyText.visibility = View.GONE
                    } else {
                        binding!!.recyclerView.visibility = View.GONE
                        binding!!.emptyText.visibility = View.VISIBLE
                    }
                    adapter!!.notifyDataSetChanged()
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }

    override fun onBackPressed() {
        if (DATA.searchStatus) {
            binding!!.toolbar.toolbar.visibility = View.VISIBLE
            binding!!.toolbar.toolbarSearch.visibility = View.GONE
            DATA.searchStatus = false
            binding!!.toolbar.textSearch.setText(DATA.EMPTY)
        } else super.onBackPressed()
    }

    override fun onResume() {
        data
        super.onResume()
    }

    override fun onRestart() {
        data
        super.onRestart()
    }
}