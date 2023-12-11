package com.example.a02task02nuis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.ActivityNotFoundException
import android.content.Intent
import android.speech.RecognizerIntent
import android.widget.Button
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {

    private val REQ_CODE_SPEECH_INPUT = 100
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSpeak: Button = findViewById(R.id.btnSpeak)
        tvResult = findViewById(R.id.textResult)

        btnSpeak.setOnClickListener {
            promptSpeechInput()
        }
    }

    private fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla algo...")

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (e: ActivityNotFoundException) {
            tvResult.text = "Error: Reconocimiento de voz no disponible"
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                val result: ArrayList<String>? = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                val spokenText = result?.get(0) ?: ""
                tvResult.text = "Texto hablado: $spokenText"
            }
        }
    }
}