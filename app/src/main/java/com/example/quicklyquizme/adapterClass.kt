package com.example.quicklyquizme

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat.recreate
import androidx.recyclerview.widget.RecyclerView

class adapterClass(private val dataList: ArrayList<dataClass>): RecyclerView.Adapter<adapterClass.ViewHolderClass>()
{
    class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val rvTitle: TextView=itemView.findViewById(R.id.deckName)
        val rvCard:CardView=itemView.findViewById(R.id.deckItem)
        val rvButton: Button =itemView.findViewById(R.id.deckOptions)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return ViewHolderClass(itemView)
    }
    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem=dataList[position]
        val deckDatabase=DeckDatabase(currentItem.mainActivityContext)
        holder.rvTitle.text=deckDatabase.returnDeckName(currentItem.currentID)
        holder.rvTitle.isSelected=true
        holder.rvCard.setOnClickListener{
            val intent=Intent(it.context,FlashCardActivity::class.java)
            val frontCardsList= mutableListOf<String>()
            val backCardsList= mutableListOf<String>()
            val cardList=deckDatabase.returnCardIDs(currentItem.currentID)
            cardList.shuffle()
            for (card in cardList){
                frontCardsList.add(deckDatabase.returnFrontCard(card))
                backCardsList.add(deckDatabase.returnBackCard(card))
            }
            intent.putExtra("frontCards",frontCardsList as ArrayList)
            intent.putExtra("backCards",backCardsList as ArrayList)
            it.context.startActivity(intent)

        }
        holder.rvButton.setOnClickListener{
            val popup=PopupMenu(it.context,holder.rvButton)
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
                        it.context.startActivity(intent)
                        true
                    }
                    R.id.deleteDeck->{
                        deckDatabase.deleteDeck(currentItem.currentID)
                        Toast.makeText(it.context,"Deck Deleted",Toast.LENGTH_LONG).show()
                        recreate(currentItem.mainActivityContext as Activity)
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




