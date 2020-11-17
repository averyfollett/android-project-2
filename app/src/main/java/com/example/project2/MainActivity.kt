package com.example.project2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import androidx.appcompat.content.res.AppCompatResources

class MainActivity : AppCompatActivity() {

    private lateinit var lifeFragment: LifeFragment
    private lateinit var nextButton: Button
    private lateinit var startButton: Button
    private lateinit var aliveColorButton: Button
    private lateinit var deadColorButton: Button
    private lateinit var saveButton: Button
    private lateinit var loadButton: Button

    private var started = false
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    private var colorArray = arrayOf(
            R.color.purple_500,
            R.color.teal_200,
            R.color.black,
            R.color.white
    )
    private var aliveColor = 0
    private var deadColor = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifeFragment = supportFragmentManager.findFragmentById(R.id.recyclerViewFragment) as LifeFragment
        nextButton = findViewById(R.id.nextButton)
        startButton = findViewById(R.id.startButton)
        aliveColorButton = findViewById(R.id.aliveColorButton)
        deadColorButton = findViewById(R.id.deadColorButton)
        saveButton = findViewById(R.id.saveButton)
        loadButton = findViewById(R.id.loadButton)

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

        aliveColorButton.setOnClickListener {
            if (aliveColor == colorArray.size - 1) {
                lifeFragment.recyclerViewAdapter.aliveColor = colorArray.first()
                aliveColorButton.backgroundTintList = AppCompatResources.getColorStateList(this, colorArray.first())
                aliveColor = 0
                lifeFragment.recyclerViewAdapter.notifyDataSetChanged()
            }
            else {
                aliveColor++
                lifeFragment.recyclerViewAdapter.aliveColor = colorArray[aliveColor]
                aliveColorButton.backgroundTintList = AppCompatResources.getColorStateList(this, colorArray[aliveColor])
                lifeFragment.recyclerViewAdapter.notifyDataSetChanged()
            }
        }

        deadColorButton.setOnClickListener {
            if (deadColor == colorArray.size - 1) {
                lifeFragment.recyclerViewAdapter.deadColor = colorArray.first()
                deadColorButton.backgroundTintList = AppCompatResources.getColorStateList(this, colorArray.first())
                deadColor = 0
                lifeFragment.recyclerViewAdapter.notifyDataSetChanged()
            }
            else {
                deadColor++
                lifeFragment.recyclerViewAdapter.deadColor = colorArray[deadColor]
                deadColorButton.backgroundTintList = AppCompatResources.getColorStateList(this, colorArray[deadColor])
                lifeFragment.recyclerViewAdapter.notifyDataSetChanged()
            }
        }

        saveButton.setOnClickListener {
            lifeFragment.lifeGridModel.save()
        }

        loadButton.setOnClickListener {
            lifeFragment.lifeGridModel.load()
        }
    }
}