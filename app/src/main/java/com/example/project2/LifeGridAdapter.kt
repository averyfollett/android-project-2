package com.example.project2

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView

class LifeGridAdapter(var context: Context, var gridModel: LifeGridModel): RecyclerView.Adapter<LifeGridAdapter.LifeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LifeViewHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.life_grid_item, parent, false)
        return LifeViewHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: LifeViewHolder, position: Int) {
        if (gridModel.getAliveStatus(position)) {
            holder.button.backgroundTintList = AppCompatResources.getColorStateList(context, R.color.purple_500)
        } else {
            holder.button.backgroundTintList = AppCompatResources.getColorStateList(context, R.color.black)
        }

        holder.button.setOnClickListener {
            if (gridModel.getAliveStatus(position)) { //if cell is already alive
                holder.button.backgroundTintList = AppCompatResources.getColorStateList(context, R.color.black)
                gridModel.setCellDead(position)
            } else
            {
                holder.button.backgroundTintList = AppCompatResources.getColorStateList(context, R.color.purple_500)
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