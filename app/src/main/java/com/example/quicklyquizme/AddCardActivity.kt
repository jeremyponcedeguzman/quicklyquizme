package com.example.quicklyquizme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quicklyquizme.databinding.ActivityCardAddBinding

class AddCardActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCardAddBinding
    private lateinit var deckDatabase: DeckDatabase
    private lateinit var frontCard:EditText
    private lateinit var backCard:EditText
    private lateinit var insertCard:Button
    private lateinit var backButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardAddBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        deckDatabase=DeckDatabase(this)
        val deckID=intent.extras?.getLong("deckID")
        val intent= Intent(this,DeckViewerActivity::class.java)
        intent.putExtra("deckID",deckID)
        frontCard=binding.editFront
        backCard=binding.editBack
        insertCard=binding.saveBtn
        backButton=binding.backBtn
        backButton.setOnClickListener{
            startActivity(intent)
            finish()

        }
        insertCard.setOnClickListener{
            deckDatabase.insertCard(frontCard.text.toString(),backCard.text.toString(), deckID!!)
            startActivity(intent)
            finish()
        }

    }

}