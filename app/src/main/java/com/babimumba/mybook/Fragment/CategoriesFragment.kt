package com.babimumba.mybook.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.babimumba.mybook.Adapter.CategoryMainAdapter
import com.babimumba.mybook.Model.Category
import com.babimumba.mybook.Unit.DATA
import com.babimumba.mybook.databinding.FragmentCategoriesBinding
import com.google.firebase.database.*

class CategoriesFragment : Fragment() {

    private var binding: FragmentCategoriesBinding? = null
    private var list: ArrayList<Category?>? = null
    private var adapter: CategoryMainAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoriesBinding.inflate(
            LayoutInflater.from(
                context
            ), container, false
        )

        //binding.recyclerCategory.setHasFixedSize(true);
        list = ArrayList()
        adapter = CategoryMainAdapter(context, list!!)
        binding!!.recyclerView.adapter = adapter
        return binding!!.root
    }

    private fun loadItems() {
        val ref: Query = FirebaseDatabase.getInstance().getReference(DATA.CATEGORIES)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list!!.clear()
                for (snapshot in dataSnapshot.children) {
                    val item = snapshot.getValue(Category::class.java)!!
                    list!!.add(item)
                }
                binding!!.bar.visibility = View.GONE
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

    override fun onResume() {
        loadItems()
        super.onResume()
    }
}