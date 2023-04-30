package com.babimumba.mybook.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.babimumba.mybook.Adapter.LinearBookAdapter
import com.babimumba.mybook.Model.Book
import com.babimumba.mybook.Unit.DATA
import com.babimumba.mybook.Unit.VOID
import com.babimumba.mybook.databinding.FragmentFollowersBinding
import com.google.firebase.database.*

class FollowersFragment : Fragment() {

    private var binding: FragmentFollowersBinding? = null
    var check: MutableList<String?>? = null
    var list: ArrayList<Book?>? = null
    var adapter: LinearBookAdapter? = null
    private var type: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowersBinding.inflate(
            LayoutInflater.from(
                context
            ), container, false
        )
        type = DATA.TIMESTAMP
        VOID.BannerAd(context, binding!!.adView, DATA.BANNER_SMART_FOLLOWERS_BOOKS)
        binding!!.recyclerView.setHasFixedSize(true)
        list = ArrayList()
        adapter = LinearBookAdapter(context, list!!, false)
        binding!!.recyclerView.adapter = adapter
        binding!!.switchBar.all.setOnClickListener { v: View? ->
            type = DATA.TIMESTAMP
            getData(type)
        }
        binding!!.switchBar.mostViews.setOnClickListener { v: View? ->
            type = DATA.VIEWS_COUNT
            getData(type)
        }
        binding!!.switchBar.mostLoves.setOnClickListener { v: View? ->
            type = DATA.LOVES_COUNT
            getData(type)
        }
        binding!!.switchBar.mostDownloads.setOnClickListener { v: View? ->
            type = DATA.DOWNLOADS_COUNT
            getData(type)
        }
        return binding!!.root
    }

    private fun getData(orderBy: String?) {
        check = ArrayList()
        val reference = FirebaseDatabase.getInstance().getReference(DATA.FOLLOW)
            .child(DATA.FirebaseUserUid).child(DATA.FOLLOWING)
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                (check as ArrayList<String?>).clear()
                for (snapshot in dataSnapshot.children) {
                    (check as ArrayList<String?>).add(snapshot.key)
                }
                getBooks(orderBy)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun getBooks(orderBy: String?) {
        val ref: Query = FirebaseDatabase.getInstance().getReference(DATA.BOOKS)
        ref.orderByChild(orderBy!!).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list!!.clear()
                for (data in dataSnapshot.children) {
                    val item = data.getValue(
                        Book::class.java
                    )
                    for (id in check!!) {
                        assert(item != null)
                        if (item!!.publisher == id) list!!.add(item)
                    }
                }
                adapter!!.notifyDataSetChanged()
                binding!!.progress.visibility = View.GONE
                if (list!!.isNotEmpty()) {
                    binding!!.recyclerView.visibility = View.VISIBLE
                    binding!!.emptyText.visibility = View.GONE
                } else {
                    binding!!.recyclerView.visibility = View.GONE
                    binding!!.emptyText.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onResume() {
        getData(DATA.TIMESTAMP)
        super.onResume()
    }
}