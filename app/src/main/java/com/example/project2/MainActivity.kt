package com.example.project2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var lifeFragment: LifeFragment
    private lateinit var nextButton: Button
    private lateinit var startButton: Button

    private var started = false
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifeFragment = supportFragmentManager.findFragmentById(R.id.recyclerViewFragment) as LifeFragment
        nextButton = findViewById(R.id.nextButton)
        startButton = findViewById(R.id.startButton)

        handler = Handler()

        nextButton.setOnClickListener {
            lifeFragment.next()
        }

        startButton.setOnClickListener {
            runnable = Runnable {
                lifeFragment.next()

                handler.postDelayed(runnable, 1000)
            }
            handler.postDelayed(runnable, 1000)
        }
    }
}