package com.example.quicklyquizme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.quicklyquizme.databinding.ActivityFlashcardBinding

class FlashCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFlashcardBinding
    private lateinit var correctBtn: Button
    private lateinit var wrongBtn: Button
    private lateinit var flipBtn: Button
    private lateinit var backBtn: Button
    private lateinit var flashCardText:TextView
    private lateinit var deckTitle: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashcardBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val frontCards= intent.extras!!.getStringArrayList("frontCards")
        val backCards=intent.extras!!.getStringArrayList("backCards")
        var currentCard=0
        val cardsAmount= frontCards?.size
        var correctAnswers=0
        deckTitle=binding.title
        deckTitle.text=intent.extras!!.getString("deckName")
        flashCardText=binding.flashcard
        flashCardText.text= frontCards!!.get(currentCard)
        correctBtn=binding.correctBtn
        wrongBtn=binding.wrongBtn
        flipBtn=binding.flipBtn
        flipBtn.setOnClickListener{
            if (flashCardText.text!= backCards!![currentCard]){
                flashCardText.text= backCards[currentCard]
            }else{
                flashCardText.text= frontCards[currentCard]
                }
            correctBtn.isVisible=true
            wrongBtn.isVisible=true
        }
        val intent= Intent(this,SessionEndActivity::class.java)
        correctBtn.setOnClickListener{
            currentCard++
            correctAnswers++
            if (currentCard== (cardsAmount)!!){
                intent.putExtra("score", "$correctAnswers/$cardsAmount")
                startActivity(intent)
                finish()
            }
            else{
                flashCardText.text= frontCards[currentCard]
                correctBtn.isGone=true
                wrongBtn.isGone=false
            }
        }
        wrongBtn.setOnClickListener{
            currentCard++
            if (currentCard== (cardsAmount)!!){
                intent.putExtra("score", "$correctAnswers/$cardsAmount")
                startActivity(intent)
                finish()
            }
            else{
                flashCardText.text= frontCards[currentCard]
                correctBtn.isGone=false
                wrongBtn.isGone=false
            }
        }

        backBtn=binding.returnBtn
        backBtn.setOnClickListener{
            finish()}
    }
}