package com.example.quicklyquizme

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class adapterClass(private val dataList: ArrayList<dataClass>): RecyclerView.Adapter<adapterClass.ViewHolderClass>()
{
    class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val rvTitle: TextView=itemView.findViewById(R.id.deckName)
        val rvCard:CardView=itemView.findViewById(R.id.deckItem)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return ViewHolderClass(itemView)
    }
    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem=dataList[position]
        holder.rvTitle.text=currentItem.deckName
        holder.rvTitle.isSelected=true
        holder.rvCard.setOnClickListener{
            val intent=Intent(it.context,FlashCardActivity::class.java)
            it.context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return dataList.size
    }
}


