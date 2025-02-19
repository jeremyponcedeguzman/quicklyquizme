package com.example.quicklyquizme


import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quicklyquizme.databinding.ActivitySessionEndBinding

class SessionEndActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySessionEndBinding
    private lateinit var returnBtn:Button
    private lateinit var scoreTextView:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySessionEndBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        returnBtn=binding.returnBtn
        scoreTextView=binding.score
        val score=intent.extras?.getString("score")
        scoreTextView.text=score
        returnBtn.setOnClickListener{
            finish()
        }
    }
}