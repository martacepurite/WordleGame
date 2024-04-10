package com.example.gamethree

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// adapteris RecyclerView objektam, kurš dinamiski attēlo spēlētāju iegūtos punktus 'highscores' tabulā
// modificēts kods no oficiālās dokumentācijas:
//https://developer.android.com/develop/ui/views/layout/recyclerview

class CustomAdapter(private val dataSet: List<User>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val textView2: TextView

        init {

            textView = view.findViewById(R.id.textView)  // spēlētāja lietotājvārds
            textView2 = view.findViewById(R.id.textView2)  // spēlētāja punkti
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.text_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.textView.text = dataSet.get(position).uid.toString()
        viewHolder.textView2.text = dataSet.get(position).points.toString()

    }

    override fun getItemCount() = dataSet.size

}
