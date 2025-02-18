package com.example.quicklyquizme

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class adapterClass(private val dataList: ArrayList<dataClass>): RecyclerView.Adapter<adapterClass.ViewHolderClass>()
{
    class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val rvTitle: TextView=itemView.findViewById(R.id.deckName)
        val rvCard:CardView=itemView.findViewById(R.id.deckItem)
        val rvImageButton:ImageButton=itemView.findViewById(R.id.imageButton)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return ViewHolderClass(itemView)
    }
    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem=dataList[position]
        val deckDatabase=currentItem.deckDatabase
        holder.rvTitle.text=deckDatabase.returnDeckName(currentItem.currentID)
        holder.rvTitle.isSelected=true
        holder.rvCard.setOnClickListener{
            val intent=Intent(it.context,FlashCardActivity::class.java)
            it.context.startActivity(intent)
        }
        holder.rvImageButton.setOnClickListener{
            val popup=PopupMenu(it.context,holder.rvImageButton)
            popup.inflate(R.menu.popup_menu)
            popup.setOnMenuItemClickListener{ menuItem ->
                when(menuItem.itemId){
                    R.id.choiceMode->{
                        Toast.makeText(it.context,"Choice",Toast.LENGTH_LONG).show()
                        true
                    }
                    R.id.viewDeck->{
                        val intent=Intent(it.context,DeckViewerActivity::class.java)
                        intent.putExtra("deckID",currentItem.currentID)
                        Toast.makeText(it.context,"view",Toast.LENGTH_LONG).show()
                        it.context.startActivity(intent)
                        true
                    }
                    R.id.deleteDeck->{
                        deckDatabase.deleteDeck(currentItem.currentID)
                        Toast.makeText(it.context,"Deck Deleted",Toast.LENGTH_LONG).show()
                        val intent=Intent(it.context,MainActivity::class.java)
                        it.context.startActivity(intent)
                        true
                    }
                    else -> {false}
                }
            }
            popup.show()
        }
    }
    override fun getItemCount(): Int {
        return dataList.size
    }

}




