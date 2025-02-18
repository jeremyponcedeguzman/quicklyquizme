package com.example.quicklyquizme

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quicklyquizme.databinding.ActivityDeckviewerBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DeckViewerActivity:AppCompatActivity() {
    private lateinit var binding: ActivityDeckviewerBinding
    private lateinit var deckDatabase:DeckDatabase
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<dataClass>
    private lateinit var cardList:MutableList<Long>
    private lateinit var deckTitle:TextView
    private lateinit var renameButton:ImageButton
    private lateinit var addCard:FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeckviewerBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val deckID=intent.extras?.getLong("deckID")
        deckDatabase = DeckDatabase(this)
        val cardAmount = deckDatabase.returnDeckCardsAmount(deckID!!)
        cardList= mutableListOf()
        deckTitle=binding.deckNameTitle
        deckTitle.text=deckDatabase.returnDeckName(deckID)
        renameButton=binding.renameDeckButton
        renameButton.setOnClickListener{
            nameDeckDialog(deckID)
        }
        addCard=binding.addCardButton
        addCard.setOnClickListener{
            val intent=Intent(this,AddCardActivity::class.java)
            intent.putExtra("deckID",deckID)
            startActivity(intent)
            finish()
        }
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        if (cardAmount > 0){
            cardList=deckDatabase.returnCardIDs(deckID)
            dataList=arrayListOf<dataClass>()
            getData()
        }
    }
    private fun getData()
    {
        for (i in cardList.indices){
            val dataClass = dataClass(cardList[i],deckDatabase)
            dataList.add(dataClass)
        }
        recyclerView.adapter= DeckViewerAdapterClass(dataList)
    }
    private fun nameDeckDialog(deckID:Long){
        val builder= AlertDialog.Builder(this)
        val inflater=this.layoutInflater
        val dialogLayout=inflater.inflate(R.layout.add_deck_layout,null)
        val nameInput=dialogLayout.findViewById<EditText>(R.id.nameInput)
        with (builder){
            setTitle("Rename Deck")
            setPositiveButton("Rename"){dialog, which ->
                val intent= Intent(this@DeckViewerActivity,MainActivity::class.java)
                deckDatabase.renameDeck(deckID,nameInput.text.toString())
                recreate()
            }
            setView(dialogLayout)
            show()
        }
    }
}