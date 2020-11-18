package com.example.project2

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources

class MainActivity : AppCompatActivity() {
    //vars for all buttons
    private lateinit var lifeFragment: LifeFragment
    private lateinit var nextButton: Button
    private lateinit var startButton: Button
    private lateinit var aliveColorButton: Button
    private lateinit var deadColorButton: Button
    private lateinit var resetButton: Button
    private lateinit var saveButton: Button
    private lateinit var loadButton: Button
    private lateinit var cloneButton: Button
    private lateinit var stopButton: Button

    //vars to handle start/stop functionality
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    //vars to handle changing alive/dead colors
    private var colorArray = arrayOf(
            R.color.purple_500,
            R.color.teal_200,
            R.color.black,
            R.color.white
    )
    private var aliveColor = 0
    private var deadColor = 2

    //required to use handler.hasCallbacks()
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize our recycler view in a fragment
        lifeFragment = supportFragmentManager.findFragmentById(R.id.recyclerViewFragment) as LifeFragment
        if (intent.getBooleanArrayExtra("ALIVE_LIST") != null) {
            lifeFragment.lifeGridModel.aliveList = intent.getBooleanArrayExtra("ALIVE_LIST")!!.toMutableList()
        }

        //initialize/link all button vars to ids
        nextButton = findViewById(R.id.nextButton)
        startButton = findViewById(R.id.startButton)
        stopButton = findViewById(R.id.stopButton)
        aliveColorButton = findViewById(R.id.aliveColorButton)
        deadColorButton = findViewById(R.id.deadColorButton)
        resetButton = findViewById(R.id.resetButton)
        saveButton = findViewById(R.id.saveButton)
        loadButton = findViewById(R.id.loadButton)
        cloneButton = findViewById(R.id.cloneButton)

        //initialize vars to handle looping functionality
        handler = Handler(Looper.getMainLooper())
        runnable = Runnable { }

        //set on click event for next button
        nextButton.setOnClickListener {
            //run next function on fragment
            lifeFragment.next()
        }

        //set on click event for start button
        startButton.setOnClickListener {
            //define runnable
            runnable = Runnable {
                //run next function on fragment
                lifeFragment.next()
                //wait for 1 second
                handler.postDelayed(runnable, 1000)
            }
            handler.postDelayed(runnable, 1000)
        }

        //set on click event for stop button
        stopButton.setOnClickListener {
            //if the handler has active callbacks in queue
            if (handler.hasCallbacks(runnable)) {
                //remove all callbacks from queue
                handler.removeCallbacks(runnable)
            }
        }

        //set on click event for alive color button
        aliveColorButton.setOnClickListener {
            //if the current color is the last in the array
            if (aliveColor == colorArray.size - 1) {
                //set the color to the first in the array
                lifeFragment.recyclerViewAdapter.aliveColor = colorArray.first()
                //set the background of the alive color button
                aliveColorButton.backgroundTintList = AppCompatResources.getColorStateList(this, colorArray.first())
                //reset alive color index to 0
                aliveColor = 0
                //tell the recycler view that data has changed so it will refresh
                lifeFragment.update()
            }
            //else if the current color is NOT the last in the array
            else {
                //increment the alive color index
                aliveColor++
                //set the color to the alive color index in the color array
                lifeFragment.recyclerViewAdapter.aliveColor = colorArray[aliveColor]
                //set the background of the alive color button
                aliveColorButton.backgroundTintList = AppCompatResources.getColorStateList(this, colorArray[aliveColor])
                //tell the recycler view that data has changed so it will refresh
                lifeFragment.update()
            }
        }

        //set on click event for dead color button
        deadColorButton.setOnClickListener {
            //if the current color is the last in the array
            if (deadColor == colorArray.size - 1) {
                //set the color to the first in the array
                lifeFragment.recyclerViewAdapter.deadColor = colorArray.first()
                //set the background of the dead color button
                deadColorButton.backgroundTintList = AppCompatResources.getColorStateList(this, colorArray.first())
                //reset dead color index to 0
                deadColor = 0
                //tell the recycler view that data has changed so it will refresh
                lifeFragment.update()
            }
            //else if the current color is NOT the last in the array
            else {
                //increment the dead color index
                deadColor++
                //set the color to the dead color index in the color array
                lifeFragment.recyclerViewAdapter.deadColor = colorArray[deadColor]
                //set the background of the dead color button
                deadColorButton.backgroundTintList = AppCompatResources.getColorStateList(this, colorArray[deadColor])
                //tell the recycler view that data has changed so it will refresh
                lifeFragment.update()
            }
        }

        //set on click event for reset button
        resetButton.setOnClickListener {
            //run reset function on the grid model
            lifeFragment.lifeGridModel.reset()
            //tell the recycler view that data has changed so it will refresh
            lifeFragment.update()
        }

        //set on click event for save button
        saveButton.setOnClickListener {
            //tell the grid model to save its data to disk
            lifeFragment.lifeGridModel.save()
        }

        //set on click event for load button
        loadButton.setOnClickListener {
            //tell the grid model to load its data from disk
            lifeFragment.lifeGridModel.load()
            //tell the recycler view that data has changed so it will refresh
            lifeFragment.update()
        }

        //set on click event for clone button
        cloneButton.setOnClickListener {
            //create the intent for the activity to load
            val intent = Intent(this, MainActivity::class.java)
            //give the intent the list of currently alive cells for it to clone
            intent.putExtra("ALIVE_LIST", lifeFragment.lifeGridModel.aliveList.toBooleanArray())
            //start the new activity
            startActivity(intent)
        }
    }
}