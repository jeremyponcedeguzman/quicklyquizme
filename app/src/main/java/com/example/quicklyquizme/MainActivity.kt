package com.example.quicklyquizme

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quicklyquizme.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var deckDatabase:DeckDatabase
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<dataClass>
    private lateinit var addDeck:FloatingActionButton
    private lateinit var deckList:MutableList<Long>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        addDeck = binding.floatingActionButton
        deckDatabase = DeckDatabase(this)
        val deckAmount = deckDatabase.returnDeckAmount()
        deckList= mutableListOf()
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        if (deckAmount > 0){
            for (deck in 0..<deckAmount) {
                deckList.add(deckDatabase.returnDeckID(deck))
            }
            dataList=arrayListOf<dataClass>()
            getData()
        }
        addDeck.setOnClickListener{
            nameDeckDialog()
        }

    }
    private fun getData()
    {
        for (i in deckList.indices){
            val dataClass = dataClass(deckList[i],deckDatabase)
            dataList.add(dataClass)
        }
        recyclerView.adapter= adapterClass(dataList)
    }
    private fun nameDeckDialog(){
        val builder=AlertDialog.Builder(this)
        val inflater=this.layoutInflater
        val dialogLayout=inflater.inflate(R.layout.add_deck_layout,null)
        val nameInput=dialogLayout.findViewById<EditText>(R.id.nameInput)
        with (builder){
            setTitle("Add Deck")
            setPositiveButton("Add"){dialog, which ->
                deckDatabase.insertDeck(nameInput.text.toString())
                recreate()
            }
            setView(dialogLayout)
            show()
        }
    }


}