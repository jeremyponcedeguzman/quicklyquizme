package com.example.quicklyquizme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adapterClass(private val dataList: ArrayList<dataClass>): RecyclerView.Adapter<adapterClass.ViewHolderClass>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return ViewHolderClass(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem=dataList[position]
        holder.rvTitle.text=currentItem.dataTitle
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val rvTitle: TextView=itemView.findViewById(R.id.deckName)
    }

}