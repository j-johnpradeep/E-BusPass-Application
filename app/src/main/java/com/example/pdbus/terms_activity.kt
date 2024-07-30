package com.example.pdbus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.github.barteksc.pdfviewer.PDFView

class terms_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_pdf)
        findViewById<PDFView>(R.id.pdf).fromAsset("rules.pdf").load()
    }
}