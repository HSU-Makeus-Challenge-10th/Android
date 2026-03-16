package com.example.yido

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.yido.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.icFace1.setOnClickListener {
            selectEmotion(binding.textFace1, Color.parseColor("#FFBB00"))
        }

        binding.icFace2.setOnClickListener {
            selectEmotion(binding.textFace2, Color.parseColor("#00BFFF"))
        }

        binding.icFace3.setOnClickListener {
            selectEmotion(binding.textFace3, Color.parseColor("#9370DB"))
        }

        binding.icFace4.setOnClickListener {
            selectEmotion(binding.textFace4, Color.parseColor("#32CD32"))
        }

        binding.icFace5.setOnClickListener {
            selectEmotion(binding.textFace5, Color.parseColor("#FF0000"))
        }

        binding.icBack.setOnClickListener {
            finish()
        }
    }

    private fun selectEmotion(selectedText: TextView, color: Int) {
        val textViews = listOf(
            binding.textFace1,
            binding.textFace2,
            binding.textFace3,
            binding.textFace4,
            binding.textFace5
        )

        for (textView in textViews) {
            textView.setTextColor(Color.BLACK)
        }

        selectedText.setTextColor(color)
    }
}