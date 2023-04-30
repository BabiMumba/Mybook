package com.babimumba.mybook.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.babimumba.mybook.R
import com.babimumba.mybook.Filter.PublisherFilter
import com.babimumba.mybook.Model.User
import com.babimumba.mybook.Unit.CLASS
import com.babimumba.mybook.Unit.DATA
import com.babimumba.mybook.Unit.VOID
import com.babimumba.mybook.databinding.ItemPublisherBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.MessageFormat

class PublisherAdapter(private val context: Context, var list: ArrayList<User?>) :
    RecyclerView.Adapter<PublisherAdapter.ViewHolder>(), Filterable {

    private var binding: ItemPublisherBinding? = null
    var filterList: ArrayList<User?>
    private var filter: PublisherFilter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemPublisherBinding.inflate(
            LayoutInflater.from(
                context
            ), parent, false
        )
        return ViewHolder(binding!!.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        VOID.Glide_(true, context, item!!.profileImage, holder.image)
        if (item.username == DATA.EMPTY) {
            holder.username.visibility = View.GONE
        } else {
            holder.username.visibility = View.VISIBLE
            holder.username.text = item.username
        }
        if (item.id == DATA.FirebaseUserUid) {
            holder.add.visibility = View.GONE
        } else {
            holder.add.visibility = View.VISIBLE
        }
        NrFollowers(holder.numberFollowers, item.id)
        NrBooks(holder.numberBooks, item.id)
        isFollowing(holder.add, item.id)
        holder.add.setOnClickListener {
            if (holder.add.tag == "add") {
                FirebaseDatabase.getInstance().reference.child(DATA.FOLLOW)
                    .child(DATA.FirebaseUserUid)
                    .child(DATA.FOLLOWING).child(item.id!!).setValue(true)
                FirebaseDatabase.getInstance().reference.child(DATA.FOLLOW).child(item.id!!)
                    .child(DATA.FOLLOWERS).child(DATA.FirebaseUserUid).setValue(true)
            } else {
                FirebaseDatabase.getInstance().reference.child(DATA.FOLLOW)
                    .child(DATA.FirebaseUserUid)
                    .child(DATA.FOLLOWING).child(item.id!!).removeValue()
                FirebaseDatabase.getInstance().reference.child(DATA.FOLLOW).child(item.id!!)
                    .child(DATA.FOLLOWERS).child(DATA.FirebaseUserUid).removeValue()
            }
        }
        holder.item.setOnClickListener {
            VOID.IntentExtra(
                context, CLASS.PROFILE, DATA.PROFILE_ID, item.id
            )
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getFilter(): Filter {
        if (filter == null) {
            filter = PublisherFilter(filterList, this)
        }
        return filter!!
    }

    inner class ViewHolder(view: View?) : RecyclerView.ViewHolder(
        view!!
    ) {
        var image: ImageView
        var add: ImageView
        var username: TextView
        var numberBooks: TextView
        var numberFollowers: TextView
        var item: LinearLayout

        init {
            image = binding!!.imageProfile
            username = binding!!.username
            add = binding!!.add
            numberBooks = binding!!.numberBooks
            numberFollowers = binding!!.numberFollowers
            item = binding!!.item
        }
    }

    private fun isFollowing(add: ImageView, userId: String?) {
        val reference = FirebaseDatabase.getInstance().reference
            .child(DATA.FOLLOW).child(DATA.FirebaseUserUid).child(DATA.FOLLOWING)
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.child(userId!!).exists()) {
                    add.setImageResource(R.drawable.ic_heart_selected)
                    add.tag = "added"
                } else {
                    add.setImageResource(R.drawable.ic_heart_unselected)
                    add.tag = "add"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun NrFollowers(numberConnected: TextView, userId: String?) {
        val reference = FirebaseDatabase.getInstance().reference.child(DATA.FOLLOW)
            .child(userId!!).child(DATA.FOLLOWERS)
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                numberConnected.text = MessageFormat.format("{0}", dataSnapshot.childrenCount)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun NrBooks(numberConnected: TextView, userId: String?) {
        val reference = FirebaseDatabase.getInstance().reference.child(DATA.USERS).child(
            userId!!
        )
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val number = DATA.EMPTY + dataSnapshot.child(DATA.BOOKS_COUNT).value
                numberConnected.text = number
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    init {
        filterList = list
    }
}