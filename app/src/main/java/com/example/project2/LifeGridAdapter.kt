package com.example.project2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView

class LifeGridAdapter(private var context: Context, private var gridModel: LifeGridModel) : RecyclerView.Adapter<LifeGridAdapter.LifeViewHolder>() {

    //vars to hold the default cell colors for both states
    var aliveColor = R.color.purple_500
    var deadColor = R.color.black

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LifeViewHolder {
        //"inflate" the layout with grid items
        val itemHolder = LayoutInflater.from(parent.context)
                .inflate(R.layout.life_grid_item, parent, false)
        return LifeViewHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: LifeViewHolder, position: Int) {
        //CALLED EACH TIME GRID UPDATES
        //if this cell is alive
        if (gridModel.getAliveStatus(position)) {
            //set background color
            holder.button.backgroundTintList = AppCompatResources.getColorStateList(context, aliveColor)
            //create animation
            val pulse = AnimationUtils.loadAnimation(context, R.anim.pulse)
            //set animation to repeat forever
            pulse.repeatCount = Animation.INFINITE
            //start the animation
            holder.button.startAnimation(pulse)
        }
        //if this cell is dead
        else {
            //set background color
            holder.button.backgroundTintList = AppCompatResources.getColorStateList(context, deadColor)
        }

        //CALLED EACH TIME BUTTON IS PRESSED
        //code looks similar to above, but is still necessary
        //create event for when button is pressed
        holder.button.setOnClickListener {
            //if this cell is currently alive
            if (gridModel.getAliveStatus(position)) {
                //set background color
                holder.button.backgroundTintList = AppCompatResources.getColorStateList(context, deadColor)
                //set cell dead
                gridModel.setCellDead(position)
                //stop any running animation
                holder.button.clearAnimation()
            }
            //if this cell is currently dead
            else {
                //set background color
                holder.button.backgroundTintList = AppCompatResources.getColorStateList(context, aliveColor)
                //set cell alive
                gridModel.setCellAlive(position)
                //create animation
                val pulse = AnimationUtils.loadAnimation(context, R.anim.pulse)
                //set animation to repeat forever
                pulse.repeatCount = Animation.INFINITE
                //start the animation
                holder.button.startAnimation(pulse)
            }
        }
    }

    override fun getItemCount(): Int {
        //return the number of items in the grid model
        return gridModel.getNumCells()
    }

    class LifeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //assign button var to object using id
        var button: Button = itemView.findViewById(R.id.lifeGridButton)
    }
}