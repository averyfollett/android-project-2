package com.example.project2

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView

class LifeGridAdapter(var context: Context): RecyclerView.Adapter<LifeGridAdapter.LifeViewHolder>() {

    private lateinit var lifeViewHolder: LifeViewHolder
    private var aliveList = mutableListOf<Boolean>()
    private var recentIndex = 0
    private var width = 20
    private var height = 20

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LifeViewHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.life_grid_item, parent, false)
        return LifeViewHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: LifeViewHolder, position: Int) {
        lifeViewHolder = holder
        holder.button.tag = Integer.valueOf(position)
        aliveList.add(false)
        holder.button.setOnClickListener {
            holder.button.backgroundTintList = AppCompatResources.getColorStateList(context, R.color.black)
            aliveList[position] = true
            recentIndex = position
        }
    }

    fun nextGeneration() {
        var i = 0
        for (item in aliveList) {
            val neighborArray = getNeighbors(i)
            var neighborsAlive = 0
            for (neighbor in neighborArray) {
                if (aliveList[neighbor])
                    neighborsAlive++
            }

            if (item) { //if item is alive
                if ((neighborsAlive == 2 || neighborsAlive == 3)) { //and if item has 2 or 3 alive neighbors
                    setAlive()
                } else { //and item does not have 2 or 3 alive neighbors
                    setDead()
                }
            } else { //if item is dead
                if (neighborsAlive == 3) {
                    setAlive()
                } else {
                    setDead()
                }
            }
            i++
        }
    }

    private fun setAlive() {
        lifeViewHolder.button.backgroundTintList = AppCompatResources.getColorStateList(context, R.color.black)
        Log.i("set", "alive")
    }

    private fun setDead() {
        lifeViewHolder.button.backgroundTintList = AppCompatResources.getColorStateList(context, R.color.black)
        Log.i("set", "dead")
    }

    private fun getNeighbors(id: Int): Array<Int> {
        val coordX = getXCoordinateFromID(id)
        val coordY = getYCoordinateFromID(id)

        var neighborArray = Array<Int>(8) { 0 }

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

    fun getMostRecentIndex(): Int {
        return recentIndex
    }

    override fun getItemCount(): Int {
        return 400
    }

    class LifeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var button: Button = itemView.findViewById(R.id.lifeGridButton)
    }
}