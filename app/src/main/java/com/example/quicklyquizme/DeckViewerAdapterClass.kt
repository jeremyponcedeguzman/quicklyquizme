package com.example.quicklyquizme

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class DeckViewerAdapterClass(private val dataList: ArrayList<dataClass>): RecyclerView.Adapter<DeckViewerAdapterClass.ViewHolderClass>()
{
    class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val rvFront: TextView=itemView.findViewById(R.id.cardFront)
        val rvBack: TextView=itemView.findViewById(R.id.cardBack)
        val rvCard:CardView=itemView.findViewById(R.id.cardItem)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.deck_layout,parent,false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem=dataList[position]
        val deckDatabase=currentItem.deckDatabase
        holder.rvFront.text=deckDatabase.returnFrontCard(currentItem.currentID)
        holder.rvBack.text=deckDatabase.returnBackCard(currentItem.currentID)
        holder.rvCard.setOnClickListener{
            val intent=Intent(it.context,EditCardActivity::class.java)
            intent.putExtra("cardID",currentItem.currentID)
            it.context.startActivity(intent)

        }

        }
    }




