package com.example.project2

import android.util.Log

class LifeGridModel {

    private var aliveList = mutableListOf<Boolean>()
    private var width = 20
    private var height = 20

    fun init() {
        var i = 0
        while (i < getNumCells()) {
            addDeadCell()
            i++
        }
    }

    fun getAliveStatus(position: Int): Boolean {
        return aliveList[position]
    }

    fun addDeadCell() {
        aliveList.add(false)
    }

    fun getNumCells(): Int {
        return width * height
    }

    fun nextGeneration() {
        for ((i, item) in aliveList.withIndex()) {
            val neighborArray = getNeighbors(i)
            var neighborsAlive = 0
            for (neighbor in neighborArray) {
                if (aliveList[neighbor])
                    neighborsAlive++
            }

            if (item) { //if item is alive
                if ((neighborsAlive == 2 || neighborsAlive == 3)) { //and if item has 2 or 3 alive neighbors
                    setCellAlive(i)
                } else { //and item does not have 2 or 3 alive neighbors
                    setCellDead(i)
                }
            } else { //if item is dead
                if (neighborsAlive == 3) {
                    setCellAlive(i)
                } else {
                    setCellDead(i)
                }
            }
        }
    }


    fun setCellAlive(position: Int) {
        aliveList[position] = true
        //Log.i("set", "alive")
    }

    fun setCellDead(position: Int) {
        aliveList[position] = false
        //Log.i("set", "dead")
    }

    private fun getNeighbors(id: Int): Array<Int> {
        val coordX = getXCoordinateFromID(id)
        val coordY = getYCoordinateFromID(id)

        val neighborArray = Array(8) { 0 }

        neighborArray[0] = getIDFromCoordinates(if (coordX - 1 < 0) coordX + width - 1 else coordX - 1, if (coordY - 1 < 0) coordY + height - 1 else coordY - 1)
        neighborArray[1] = getIDFromCoordinates(coordX, if (coordY - 1 < 0) coordY + height - 1 else coordY - 1)
        neighborArray[2] = getIDFromCoordinates(if (coordX + 1 > width - 1) coordX - width + 1 else coordX + 1, if (coordY - 1 < 0) coordY + height - 1 else coordY - 1)
        neighborArray[3] = getIDFromCoordinates(if (coordX + 1 > width - 1) coordX - width + 1 else coordX + 1, coordY)
        neighborArray[4] = getIDFromCoordinates(if (coordX + 1 > width - 1) coordX - width + 1 else coordX + 1, if (coordY + 1 > height - 1) coordY - height + 1 else coordY + 1)
        neighborArray[5] = getIDFromCoordinates(coordX, if (coordY + 1 > height - 1) coordY - height + 1 else coordY + 1)
        neighborArray[6] = getIDFromCoordinates(if (coordX - 1 < 0) coordX + width - 1 else coordX - 1, if (coordY + 1 > height - 1) coordY - height + 1 else coordY + 1)
        neighborArray[7] = getIDFromCoordinates(if (coordX - 1 < 0) coordX + width - 1 else coordX - 1, coordY)

        return neighborArray
    }

    private fun getIDFromCoordinates(x: Int, y: Int): Int {
        return (y * width) + x
    }

    private fun getXCoordinateFromID(id: Int): Int{
        return id % width
    }

    private fun getYCoordinateFromID(id: Int): Int{
        return id / width
    }

    fun getAliveCoordinates() {
        for ((i, item) in aliveList.withIndex()) {
            if (item) {
                Log.i("alive coordinate", (i % width).toString() + (i / width).toString())
            }
        }
    }

    fun getNumberAlive(): Int {
        var i = 0
        for (item in aliveList) {
            if (item) {
                i += 1
            }
        }
        return i
    }
}