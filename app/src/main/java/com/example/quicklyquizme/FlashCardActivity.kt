package com.example.quicklyquizme

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.quicklyquizme.databinding.ActivityFlashCardBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FlashCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFlashCardBinding
    private lateinit var deckDatabase: DeckDatabase
    private lateinit var dataList: ArrayList<dataClass>
    private lateinit var addDeck: FloatingActionButton
    private lateinit var deckList: MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashCardBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}