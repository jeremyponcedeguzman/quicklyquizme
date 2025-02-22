package com.example.quicklyquizme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.addCallback
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
    private lateinit var renameButton:Button
    private lateinit var addCard:FloatingActionButton
    private lateinit var returnBtn:Button
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
        returnBtn=binding.returnBtn
        returnBtn.setOnClickListener{
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
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
            dataList=arrayListOf()
            getData()
        }
        val intent=Intent(this,MainActivity::class.java)
        onBackPressedDispatcher.addCallback {
            startActivity(intent)
            finish()
        }
    }
    private fun getData()
    {
        for (i in cardList.indices){
            val dataClass = dataClass(cardList[i],this)
            dataList.add(dataClass)
        }
        recyclerView.adapter= DeckViewerAdapterClass(dataList)
    }
    private fun nameDeckDialog(deckID:Long){
        val builder= AlertDialog.Builder(this)
        val inflater=this.layoutInflater
        val dialogLayout=inflater.inflate(R.layout.add_deck_layout,null)
        val nameInput=dialogLayout.findViewById<EditText>(R.id.nameInput)
        nameInput.setText(deckDatabase.returnDeckName(deckID))
        with (builder){
            setTitle("Rename Deck")
            setPositiveButton("Rename"){_, _ ->
                deckDatabase.renameDeck(deckID,nameInput.text.toString())
                recreate()
            }
            setView(dialogLayout)
            show()
        }
    }
}