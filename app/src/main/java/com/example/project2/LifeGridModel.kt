package com.example.project2

import android.content.Context
import android.util.Log
import java.io.*

class LifeGridModel(private var context: Context) {

    //vars to create our grid and save its status
    var aliveList = mutableListOf<Boolean>()
    private var width = 20
    private var height = 20

    //helper function to convert bool to int (true=1, false=0)
    private fun Boolean.toInt() = if (this) 1 else 0

    //helper function to convert int to bool (1=true, 0=false)
    private fun Int.toBoolean() = this == 1

    fun reset() {
        //clear all data in the array
        aliveList.clear()
        //re-init the whole array
        init()
    }

    fun save() {
        //create output stream writer to write to data.txt
        val outputStreamWriter = OutputStreamWriter(context.openFileOutput("data.txt", Context.MODE_PRIVATE))

        //for each item in the array
        for (item in aliveList) {
            //write its alive status to disk as int
            outputStreamWriter.write(item.toInt())
        }

        //finish writing data
        outputStreamWriter.close()
    }

    fun load() {
        //open data.txt file
        val inputStream = context.openFileInput("data.txt")

        //if file exists
        if (inputStream != null) {
            //create other helper objects
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var i = 0

            //while not at end of array
            while (i < aliveList.size) {
                //set position in array equal to int read from file
                aliveList[i] = bufferedReader.read().toBoolean()
                //increment index to keep reading file
                i++
            }
        }
        //if file does not exist, alert us!
        else {
            Log.e("error", "inputStream is null")
        }

        //finish reading from file
        inputStream.close()
    }

    fun init() {
        //helper index var
        var i = 0
        //for each cell
        while (i < getNumCells()) {
            //set that cell to be dead
            addDeadCell()
            //increment index var
            i++
        }
    }

    //returns true/false alive status at any index on the grid
    fun getAliveStatus(position: Int): Boolean {
        return aliveList[position]
    }

    //adds 1 dead cell to the array
    private fun addDeadCell() {
        aliveList.add(false)
    }

    //returns the total number of cells in the grid
    fun getNumCells(): Int {
        return width * height
    }

    //advance 1 generation in the simulation
    fun nextGeneration() {
        //for each grid cell
        for ((i, item) in aliveList.withIndex()) {
            //find its neighbors
            val neighborArray = getNeighbors(i)
            //assume no neighbors are alive
            var neighborsAlive = 0
            //for each neighbor
            for (neighbor in neighborArray) {
                //if neighbor is alive
                if (aliveList[neighbor]) {
                    //increment # of alive neighbors
                    neighborsAlive++
                }
            }

            //if selected cell is alive
            if (item) {
                //and if cell has 2 or 3 alive neighbors
                if ((neighborsAlive == 2 || neighborsAlive == 3)) {
                    //set the cell to be alive
                    setCellAlive(i)
                }
                //if cell does not have 2 or 3 alive neighbors
                else {
                    //set the cell to be dead
                    setCellDead(i)
                }
            }
            //if selected cell is dead
            else {
                //and if cell has exactly 3 alive neighbors
                if (neighborsAlive == 3) {
                    //set the cell to be alive
                    setCellAlive(i)
                }
                //if cell does not have exactly 3 alive neighbors
                else {
                    //set the cell to be dead
                    setCellDead(i)
                }
            }
        }
    }

    //sets a cell to be alive given its position in the grid
    fun setCellAlive(position: Int) {
        aliveList[position] = true
    }

    //sets a cell to be dead given its position in the grid
    fun setCellDead(position: Int) {
        aliveList[position] = false
    }

    //returns the neighbors of a given cell
    private fun getNeighbors(id: Int): Array<Int> {
        //the the x and y coordinates
        val coordX = getXCoordinateFromID(id)
        val coordY = getYCoordinateFromID(id)

        //make empty array size of 8
        val neighborArray = Array(8) { 0 }

        //set each neighbor using formulas to calculate it's position in the grid
        //this also takes into account under/overflow using if statements
        neighborArray[0] = getIDFromCoordinates(if (coordX - 1 < 0) coordX + width - 1 else coordX - 1, if (coordY - 1 < 0) coordY + height - 1 else coordY - 1)
        neighborArray[1] = getIDFromCoordinates(coordX, if (coordY - 1 < 0) coordY + height - 1 else coordY - 1)
        neighborArray[2] = getIDFromCoordinates(if (coordX + 1 > width - 1) coordX - width + 1 else coordX + 1, if (coordY - 1 < 0) coordY + height - 1 else coordY - 1)
        neighborArray[3] = getIDFromCoordinates(if (coordX + 1 > width - 1) coordX - width + 1 else coordX + 1, coordY)
        neighborArray[4] = getIDFromCoordinates(if (coordX + 1 > width - 1) coordX - width + 1 else coordX + 1, if (coordY + 1 > height - 1) coordY - height + 1 else coordY + 1)
        neighborArray[5] = getIDFromCoordinates(coordX, if (coordY + 1 > height - 1) coordY - height + 1 else coordY + 1)
        neighborArray[6] = getIDFromCoordinates(if (coordX - 1 < 0) coordX + width - 1 else coordX - 1, if (coordY + 1 > height - 1) coordY - height + 1 else coordY + 1)
        neighborArray[7] = getIDFromCoordinates(if (coordX - 1 < 0) coordX + width - 1 else coordX - 1, coordY)

        //return the completed array
        return neighborArray
    }

    //calculates a cells id given its x and y coordinates
    private fun getIDFromCoordinates(x: Int, y: Int): Int {
        return (y * width) + x
    }

    //calculates a cells x coordinate given its id
    private fun getXCoordinateFromID(id: Int): Int {
        return id % width
    }

    //calculates a cells y coordinate given its id
    private fun getYCoordinateFromID(id: Int): Int {
        return id / width
    }
}