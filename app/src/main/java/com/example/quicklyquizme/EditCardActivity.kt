package com.example.quicklyquizme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quicklyquizme.databinding.ActivityCardEditBinding

class EditCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCardEditBinding
    private lateinit var deckDatabase: DeckDatabase
    private lateinit var frontCard: EditText
    private lateinit var backCard: EditText
    private lateinit var editCard: Button
    private lateinit var backButton: Button
    private lateinit var deleteButton:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardEditBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        deckDatabase=DeckDatabase(this)
        val cardID=intent.extras?.getLong("cardID")
        val intent= Intent(this,DeckViewerActivity::class.java)
        frontCard=binding.editFront
        backCard=binding.editBack
        editCard=binding.saveBtn
        backButton=binding.backBtn
        frontCard.setText(deckDatabase.returnFrontCard(cardID!!))
        backCard.setText(deckDatabase.returnBackCard(cardID))
        deleteButton=binding.deleteBtn

        deleteButton.setOnClickListener{
            deckDatabase.deleteCard(cardID)
            finish()
            startActivity(intent)
        }
        backButton.setOnClickListener{
            finish()
        }
        editCard.setOnClickListener{

            deckDatabase.editCard(cardID,frontCard.text.toString(),backCard.text.toString())
            finish()
            startActivity(intent)
        }

    }
}