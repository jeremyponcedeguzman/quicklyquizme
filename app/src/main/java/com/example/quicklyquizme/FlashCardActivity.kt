package com.example.quicklyquizme

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quicklyquizme.databinding.ActivityFlashcardBinding

class FlashCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFlashcardBinding
				private lateinit var nextBtn: Button
				private lateinit var flipBtn: Button
				private lateinit var backBtn: Button
				private lateinit var flashCardText:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashcardBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }val frontCards:Array<String>=intent.getExtras?("frontCards")
val backCards:Array<String>=intent.getExtras?("backCards")
var frontFacing=true
var currentCard=0
val cardsAmount=.intent.getExtras?("cardsAmount")
flashCardText=binding.flashCard
flashCardText.text=frontCards.getString(currentCard)
nextBtn=binding.nextBtn
flipBtn=binding.flipBtn
flipBtn.setOnClickListener{
if (frontFacing){
frontFacing=false
flashCardText.text=backCards.getString(currentCard)
}else{
flashCardText.text=backCards.getString(currentCard)
frontFacing=true}
}
nextBtn.setOnClickListener{
currentCard++
if (currentCard==cardsAmount){
finish()
}
flashCardText.text=frontCards.getString(currentCard)}
backBtn=binding.backBtn
backBtn.setOnClickListener{
finish()}
    }
}