package com.babimumba.mybook.Filter

import android.widget.Filter
import com.babimumba.mybook.Adapter.MainBookAdapter
import com.babimumba.schoolapp.Model.Book
import java.util.*

class PdfMainFilter(var list: ArrayList<Book?>, var adapter: MainBookAdapter) : Filter() {
    override fun performFiltering(constraint: CharSequence): FilterResults {
        var constraint: CharSequence? = constraint
        val results = FilterResults()
        if (constraint != null && constraint.length > 0) {
            constraint = constraint.toString().uppercase(Locale.getDefault())
            val filter = ArrayList<Book?>()
            for (i in list.indices) {
                if (list[i]!!.title!!.uppercase(Locale.getDefault()).contains(constraint)) {
                    filter.add(list[i])
                }
            }
            results.count = filter.size
            results.values = filter
        } else {
            results.count = list.size
            results.values = list
        }
        return results
    }

    override fun publishResults(constraint: CharSequence, results: FilterResults) {
        adapter.list = (results.values as ArrayList<Book?>)
        adapter.notifyDataSetChanged()
    }
}