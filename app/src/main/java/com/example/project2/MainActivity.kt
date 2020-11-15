package com.example.project2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var lifeFragment: LifeFragment
    private lateinit var nextButton: Button
    private lateinit var startButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifeFragment = supportFragmentManager.findFragmentById(R.id.recyclerViewFragment) as LifeFragment
        nextButton = findViewById(R.id.nextButton)
        startButton = findViewById(R.id.startButton)

        nextButton.setOnClickListener {
            //Toast.makeText(this, "dsfsdfsd", Toast.LENGTH_SHORT).show()
            lifeFragment.next()
        }

        startButton.setOnClickListener {

        }
    }
}