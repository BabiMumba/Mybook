package com.babimumba.mybook.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.babimumba.mybook.Model.Category
import com.babimumba.mybook.Unit.CLASS
import com.babimumba.mybook.databinding.ItemCategoryBinding
import com.babimumba.mybook.Unit.DATA
import com.babimumba.mybook.Unit.VOID

class CategoryAdapter(private val context: Context?, var list: ArrayList<Category?>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemCategoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding!!.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        val id = item!!.id
        val name = item.category
        val image = item.image
        VOID.Glide_(false, context, image, binding!!.image)
        holder.itemView.setOnClickListener { v: View? ->
            VOID.IntentExtra2(
                context, CLASS.CATEGORY_BOOKS, DATA.CATEGORY_ID, id, DATA.CATEGORY_NAME, name
            )
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var image: ImageView

        init {
            image = binding!!.image
        }
    }

    companion object {
        private var binding: ItemCategoryBinding? = null
    }
}