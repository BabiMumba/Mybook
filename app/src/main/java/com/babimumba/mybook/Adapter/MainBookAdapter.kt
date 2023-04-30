package com.babimumba.mybook.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.babimumba.schoolapp.Filter.PdfMainFilter
import com.babimumba.schoolapp.Model.Book
import com.babimumba.mybook.Unit.CLASS
import com.babimumba.mybook.Unit.DATA
import com.babimumba.mybook.Unit.VOID
import com.babimumba.mybook.databinding.ItemBookMainBinding

class MainBookAdapter(
    private val context: Context?,
    var list: ArrayList<Book?>,
    isDownloads: Boolean,
    isViews: Boolean,
    isLoves: Boolean
) : RecyclerView.Adapter<MainBookAdapter.ViewHolder>(), Filterable {

    private var binding: ItemBookMainBinding? = null
    var filterList: ArrayList<Book?>
    private var filter: PdfMainFilter? = null
    private val isDownloads: Boolean
    private val isViews: Boolean
    private val isLoves: Boolean

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemBookMainBinding.inflate(
            LayoutInflater.from(
                context
            ), parent, false
        )
        return ViewHolder(binding!!.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        val bookId = DATA.EMPTY + item!!.id
        val title = DATA.EMPTY + item.title
        val image = DATA.EMPTY + item.image
        val viewsCount = DATA.EMPTY + item.viewsCount
        val downloadsCount = DATA.EMPTY + item.downloadsCount
        val lovesCount = DATA.EMPTY + item.lovesCount
        if (isDownloads) {
            holder.linearDownloads.visibility = View.VISIBLE
        } else {
            holder.linearDownloads.visibility = View.GONE
        }
        if (isLoves) {
            holder.linearLoves.visibility = View.VISIBLE
        } else {
            holder.linearLoves.visibility = View.GONE
        }
        if (isViews) {
            holder.linearViews.visibility = View.VISIBLE
        } else {
            holder.linearViews.visibility = View.GONE
        }
        if (isViews || isLoves || isDownloads) {
            holder.line.visibility = View.VISIBLE
        } else {
            holder.line.visibility = View.GONE
        }
        VOID.Glide_(false, context, image, holder.image)
        binding!!.views.text = viewsCount
        binding!!.downloads.text = downloadsCount
        binding!!.loves.text = lovesCount
        binding!!.name.text = title
        VOID.isFavorite(holder.favorites, item.id, DATA.FirebaseUserUid)
        holder.favorites.setOnClickListener { view: View? ->
            VOID.checkFavorite(
                holder.favorites,
                bookId
            )
        }
        holder.itemView.setOnClickListener { v: View? ->
            VOID.IntentExtra(
                context, CLASS.BOOK_DETAIL, DATA.BOOK_ID, bookId
            )
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getFilter(): Filter {
        if (filter == null) {
            filter = PdfMainFilter(filterList, this)
        }
        return filter!!
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(
        itemView!!
    ) {
        var linearDownloads: LinearLayout
        var linearLoves: LinearLayout
        var linearViews: LinearLayout
        var linear1: LinearLayout
        var linear2: LinearLayout
        var downloads: TextView
        var loves: TextView
        var views: TextView
        var favorites: ImageView
        var download: ImageView
        var love: ImageView
        var view: ImageView
        var image: ImageView
        var item: LinearLayout
        var line: View

        init {
            image = binding!!.image
            linearDownloads = binding!!.linearDownloads
            linearLoves = binding!!.linearLoves
            linearViews = binding!!.linearViews
            favorites = binding!!.favorites
            downloads = binding!!.downloads
            loves = binding!!.loves
            views = binding!!.views
            download = binding!!.download
            love = binding!!.love
            view = binding!!.view
            item = binding!!.item
            line = binding!!.line
            linear1 = binding!!.linear1
            linear2 = binding!!.linear2
        }
    }

    init {
        filterList = list
        this.isDownloads = isDownloads
        this.isViews = isViews
        this.isLoves = isLoves
    }
}