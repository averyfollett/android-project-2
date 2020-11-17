package com.example.project2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView

class LifeGridAdapter(var context: Context, var gridModel: LifeGridModel): RecyclerView.Adapter<LifeGridAdapter.LifeViewHolder>() {

    var aliveColor = R.color.purple_500
    var deadColor = R.color.black

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LifeViewHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.life_grid_item, parent, false)
        return LifeViewHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: LifeViewHolder, position: Int) {
        if (gridModel.getAliveStatus(position)) {
            holder.button.backgroundTintList = AppCompatResources.getColorStateList(context, aliveColor)
        } else {
            holder.button.backgroundTintList = AppCompatResources.getColorStateList(context, deadColor)
        }

        holder.button.setOnClickListener {
            if (gridModel.getAliveStatus(position)) { //if cell is already alive
                holder.button.backgroundTintList = AppCompatResources.getColorStateList(context, deadColor)
                gridModel.setCellDead(position)
            } else
            {
                holder.button.backgroundTintList = AppCompatResources.getColorStateList(context, aliveColor)
                gridModel.setCellAlive(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return gridModel.getNumCells()
    }

    class LifeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var button: Button = itemView.findViewById(R.id.lifeGridButton)
    }
}