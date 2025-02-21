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
import com.example.quicklyquizme.databinding.ActivityMultipleChoiceBinding

class MultipleChoiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMultipleChoiceBinding
    private lateinit var continueBtn:Button
    private lateinit var deckDatabase: DeckDatabase
    private lateinit var backBtn: Button
    private lateinit var flashCardText:TextView
    private lateinit var deckTitle: TextView
    private lateinit var choice1:TextView
    private lateinit var choice2:TextView
    private lateinit var choice3:TextView
    private lateinit var choice4:TextView
    private lateinit var choiceArray:Array<TextView>
    private lateinit var cardIDs:MutableList<Long>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMultipleChoiceBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        deckDatabase=DeckDatabase(this)
        flashCardText=binding.flashcard
        val deckID= intent.extras!!.getLong("deckID")
        var currentCard=0
        var correctAnswers=0
        val cardsAmount=deckDatabase.returnDeckCardsAmount(deckID)
        choice1=binding.choice1
        choice2=binding.choice2
        choice3=binding.choice3
        choice4=binding.choice4
        choiceArray= arrayOf(choice1,choice2,choice3,choice4)
        continueBtn=binding.correctBtn
        cardIDs=deckDatabase.returnCardIDs(deckID)
        cardIDs.shuffle()
        deckTitle=binding.title
        deckTitle.text=deckDatabase.returnDeckName(deckID)
        for (answer in 0..3){
            choiceArray[answer].setOnClickListener{
                val correctText=deckDatabase.returnBackCard(cardIDs[currentCard])
                if (0 == choiceArray[answer].text.toString().compareTo(correctText)) {
                    correctAnswers++
                }
                removeWrongAnswers(correctText)
                continueBtn.isVisible=true
            }
        }
        continueBtn.setOnClickListener{
            currentCard++
            if (currentCard.toLong() == (cardsAmount)){
                val intent= Intent(this,SessionEndActivity::class.java)
                intent.putExtra("score", "$correctAnswers/$cardsAmount")
                startActivity(intent)
                finish()
            }
            else{
                for(choice in choiceArray){
                    choice.isVisible=true
                    choice.isClickable=true
                }
                nextQuestion(currentCard)
                continueBtn.isGone=true
            }
        }
        nextQuestion(currentCard)
        backBtn=binding.returnBtn
        backBtn.setOnClickListener{
            finish()}
    }
    private fun nextQuestion(currentCard: Int){
        flashCardText.text = deckDatabase.returnFrontCard(cardIDs[currentCard])
        val currentChoices = mutableListOf<String>()
        val correctAnswer=deckDatabase.returnBackCard(cardIDs[currentCard])
        currentChoices.addLast(correctAnswer)
        var cardsToAdd=3
        while(cardsToAdd!=0) {
            val cardToAdd=deckDatabase.returnBackCard((cardIDs).random())
            if (0!=cardToAdd.compareTo(currentChoices[0])) {
                currentChoices.addLast(cardToAdd)
                cardsToAdd--
            }
        }
        currentChoices.shuffle()
        for (choice in 0..3) {
            choiceArray[choice].text = currentChoices[choice]
        }
    }

    private fun removeWrongAnswers(correctAnswer:String){
        for (choice in choiceArray){
            if (0 == choice.text.toString().compareTo(correctAnswer)){
                choice.isClickable=false
            }
            else{
                choice.isGone=true
            }
        }
    }
}